package com.recipemanager.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.recipemanager.R;
import com.recipemanager.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying recipes from API in a grid
 * Used in SearchFragment
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    // List of recipes to display
    private List<Recipe> recipes = new ArrayList<>();

    // Click listener interface
    private OnRecipeClickListener clickListener;

    /**
     * Interface for handling recipe clicks
     */
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    /**
     * Set click listener
     */
    public void setOnRecipeClickListener(OnRecipeClickListener listener) {
        this.clickListener = listener;
    }

    /**
     * Update the recipe list and refresh the display
     */
    public void setRecipes(List<Recipe> newRecipes) {
        if (newRecipes != null) {
            this.recipes = newRecipes;
            notifyDataSetChanged();  // Notify adapter that data changed
        }
    }

    /**
     * Get recipe at specific position
     */
    public Recipe getRecipeAt(int position) {
        if (position >= 0 && position < recipes.size()) {
            return recipes.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // Get the recipe at this position
        Recipe recipe = recipes.get(position);

        // Bind data to views
        holder.bind(recipe);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onRecipeClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    /**
     * ViewHolder class - holds references to views for each item
     */
    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recipeImage;
        private final TextView recipeName;
        private final TextView recipeCategory;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find views by ID (cache them for performance)
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeCategory = itemView.findViewById(R.id.recipe_category);
        }

        /**
         * Bind recipe data to views
         */
        public void bind(Recipe recipe) {
            // Set recipe name
            recipeName.setText(recipe.getName());

            // Set category (or "Unknown" if null)
            String category = recipe.getCategory() != null ? recipe.getCategory() : "Unknown";
            recipeCategory.setText(category);

            // Load image using Glide
            // Glide handles caching, placeholders, and errors automatically
            Glide.with(itemView.getContext())
                    .load(recipe.getImageUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())  // Smooth fade-in
                    .placeholder(R.color.background_gray)  // Show while loading
                    .error(R.color.error)  // Show if image fails to load
                    .centerCrop()  // Crop to fill the view
                    .into(recipeImage);
        }
    }
}
