package com.recipemanager.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.recipemanager.R;
import com.recipemanager.database.FavoriteRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying favorite recipes from database
 * Used in FavoritesFragment
 * Shows additional info like rating and notes
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    // List of favorite recipes
    private List<FavoriteRecipe> favorites = new ArrayList<>();

    // Click listener interface
    private OnFavoriteClickListener clickListener;

    /**
     * Interface for handling favorite clicks
     */
    public interface OnFavoriteClickListener {
        void onFavoriteClick(FavoriteRecipe favorite);
        void onFavoriteDelete(FavoriteRecipe favorite);
    }

    /**
     * Set click listener
     */
    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.clickListener = listener;
    }

    /**
     * Update the favorites list
     */
    public void setFavorites(List<FavoriteRecipe> newFavorites) {
        if (newFavorites != null) {
            this.favorites = newFavorites;
            notifyDataSetChanged();
        }
    }

    /**
     * Get favorite at specific position
     */
    public FavoriteRecipe getFavoriteAt(int position) {
        if (position >= 0 && position < favorites.size()) {
            return favorites.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the favorite item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_card, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        // Get the favorite at this position
        FavoriteRecipe favorite = favorites.get(position);

        // Bind data to views
        holder.bind(favorite);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onFavoriteClick(favorite);
            }
        });

        // Long press for delete (optional - could also use swipe to delete)
        holder.itemView.setOnLongClickListener(v -> {
            if (clickListener != null) {
                clickListener.onFavoriteDelete(favorite);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    /**
     * ViewHolder for favorite items
     */
    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recipeImage;
        private final TextView recipeName;
        private final TextView recipeCategory;
        private final RatingBar ratingBar;
        private final TextView userNotes;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find views
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeCategory = itemView.findViewById(R.id.recipe_category);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            userNotes = itemView.findViewById(R.id.user_notes);
        }

        /**
         * Bind favorite recipe data to views
         */
        public void bind(FavoriteRecipe favorite) {
            // Set recipe name
            recipeName.setText(favorite.getName());

            // Set category and area
            String categoryText = favorite.getCategory();
            if (favorite.getArea() != null && !favorite.getArea().isEmpty()) {
                categoryText += " • " + favorite.getArea();
            }
            recipeCategory.setText(categoryText);

            // Set rating (show only if rated)
            if (favorite.getRating() > 0) {
                ratingBar.setRating(favorite.getRating());
                ratingBar.setVisibility(View.VISIBLE);
            } else {
                ratingBar.setVisibility(View.GONE);
            }

            // Set user notes (show only if not empty)
            if (favorite.getUserNotes() != null && !favorite.getUserNotes().trim().isEmpty()) {
                userNotes.setText("📝 " + favorite.getUserNotes());
                userNotes.setVisibility(View.VISIBLE);
            } else {
                userNotes.setVisibility(View.GONE);
            }

            // Load image using Glide
            Glide.with(itemView.getContext())
                    .load(favorite.getImageUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.color.background_gray)
                    .error(R.color.error)
                    .centerCrop()
                    .into(recipeImage);
        }
    }
}
