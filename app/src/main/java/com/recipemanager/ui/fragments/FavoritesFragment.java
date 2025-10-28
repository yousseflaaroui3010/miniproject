package com.recipemanager.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.recipemanager.R;
import com.recipemanager.database.FavoriteRecipe;
import com.recipemanager.ui.adapters.FavoriteAdapter;
import com.recipemanager.viewmodel.FavoritesViewModel;

/**
 * FavoritesFragment - Display saved favorite recipes
 * Features:
 * - List all favorites from local database
 * - Swipe to delete
 * - Click to view details
 * - Works offline
 */
public class FavoritesFragment extends Fragment {

    // ViewModel
    private FavoritesViewModel viewModel;

    // UI Components
    private RecyclerView favoritesRecyclerView;
    private LinearLayout emptyState;
    private TextView favoritesTitle;

    // Adapter
    private FavoriteAdapter favoriteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        // Find views
        initViews(view);

        // Setup RecyclerView
        setupRecyclerView();

        // Setup swipe to delete
        setupSwipeToDelete();

        // Observe ViewModel
        observeViewModel();
    }

    /**
     * Initialize all view references
     */
    private void initViews(View view) {
        favoritesRecyclerView = view.findViewById(R.id.favorites_recycler_view);
        emptyState = view.findViewById(R.id.empty_state);
        favoritesTitle = view.findViewById(R.id.favorites_title);
    }

    /**
     * Setup RecyclerView with LinearLayoutManager
     */
    private void setupRecyclerView() {
        // Create adapter
        favoriteAdapter = new FavoriteAdapter();

        // Set click listeners
        favoriteAdapter.setOnFavoriteClickListener(new FavoriteAdapter.OnFavoriteClickListener() {
            @Override
            public void onFavoriteClick(FavoriteRecipe favorite) {
                // Navigate to detail fragment
                openFavoriteDetail(favorite);
            }

            @Override
            public void onFavoriteDelete(FavoriteRecipe favorite) {
                // Show delete confirmation dialog
                showDeleteDialog(favorite);
            }
        });

        // Setup RecyclerView with linear layout
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoritesRecyclerView.setAdapter(favoriteAdapter);
        favoritesRecyclerView.setHasFixedSize(true);
    }

    /**
     * Setup swipe to delete gesture
     */
    private void setupSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                // Not used for swipe to delete
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Get the position of swiped item
                int position = viewHolder.getAdapterPosition();
                FavoriteRecipe favorite = favoriteAdapter.getFavoriteAt(position);

                if (favorite != null) {
                    // Show confirmation dialog
                    showDeleteDialog(favorite);
                }
            }
        };

        // Attach to RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(favoritesRecyclerView);
    }

    /**
     * Observe ViewModel LiveData
     */
    private void observeViewModel() {
        // Observe all favorites
        viewModel.getAllFavorites().observe(getViewLifecycleOwner(), favorites -> {
            if (favorites != null && !favorites.isEmpty()) {
                // Update adapter
                favoriteAdapter.setFavorites(favorites);
                showFavorites();

                // Update title with count
                String title = "My Favorites (" + favorites.size() + ")";
                favoritesTitle.setText(title);
            } else {
                // Show empty state
                showEmptyState();
            }
        });

        // Observe favorites count
        viewModel.getFavoritesCount().observe(getViewLifecycleOwner(), count -> {
            if (count != null && count > 0) {
                String title = "My Favorites (" + count + ")";
                favoritesTitle.setText(title);
            } else {
                favoritesTitle.setText(R.string.nav_favorites);
            }
        });
    }

    /**
     * Navigate to recipe detail fragment
     */
    private void openFavoriteDetail(FavoriteRecipe favorite) {
        RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstanceFromFavorite(favorite);

        // Navigate to detail fragment
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Show delete confirmation dialog
     */
    private void showDeleteDialog(FavoriteRecipe favorite) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete \"" + favorite.getName() + "\" from your favorites?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete the favorite
                    deleteFavorite(favorite);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Refresh adapter to restore swiped item
                    favoriteAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    /**
     * Delete a favorite recipe
     */
    private void deleteFavorite(FavoriteRecipe favorite) {
        viewModel.deleteFavorite(favorite);
        Toast.makeText(getContext(), "Recipe deleted from favorites", Toast.LENGTH_SHORT).show();
    }

    /**
     * Show favorites RecyclerView
     */
    private void showFavorites() {
        favoritesRecyclerView.setVisibility(View.VISIBLE);
        emptyState.setVisibility(View.GONE);
    }

    /**
     * Show empty state
     */
    private void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        favoritesRecyclerView.setVisibility(View.GONE);
    }
}
