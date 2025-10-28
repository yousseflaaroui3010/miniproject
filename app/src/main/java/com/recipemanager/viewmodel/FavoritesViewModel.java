package com.recipemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.recipemanager.database.FavoriteRecipe;
import com.recipemanager.repository.RecipeRepository;

import java.util.List;

/**
 * ViewModel for FavoritesFragment
 * Manages all favorite recipes stored locally
 * Handles CRUD operations on favorites
 */
public class FavoritesViewModel extends AndroidViewModel {

    // Repository for data operations
    private final RecipeRepository repository;

    // LiveData for all favorites
    private final LiveData<List<FavoriteRecipe>> allFavorites;

    // LiveData for favorites count
    private final LiveData<Integer> favoritesCount;

    /**
     * Constructor - initialize repository and load favorites
     */
    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
        allFavorites = repository.getAllFavorites();
        favoritesCount = repository.getFavoritesCount();
    }

    /**
     * Get all favorite recipes
     */
    public LiveData<List<FavoriteRecipe>> getAllFavorites() {
        return allFavorites;
    }

    /**
     * Get a specific favorite by ID
     */
    public LiveData<FavoriteRecipe> getFavoriteById(String recipeId) {
        return repository.getFavoriteById(recipeId);
    }

    /**
     * Search within favorites
     */
    public LiveData<List<FavoriteRecipe>> searchFavorites(String query) {
        return repository.searchFavorites(query);
    }

    /**
     * Filter favorites by category
     */
    public LiveData<List<FavoriteRecipe>> filterByCategory(String category) {
        return repository.getFavoritesByCategory(category);
    }

    /**
     * Get top rated recipes
     */
    public LiveData<List<FavoriteRecipe>> getTopRated(float minRating) {
        return repository.getTopRatedRecipes(minRating);
    }

    /**
     * Get favorites count
     */
    public LiveData<Integer> getFavoritesCount() {
        return favoritesCount;
    }

    /**
     * DELETE - Remove a recipe from favorites
     */
    public void deleteFavorite(FavoriteRecipe recipe) {
        repository.deleteFavorite(recipe);
    }

    /**
     * UPDATE - Update a favorite recipe
     */
    public void updateFavorite(FavoriteRecipe recipe) {
        repository.updateFavorite(recipe);
    }

    /**
     * UPDATE - Quick update for notes and rating
     */
    public void updateNotesAndRating(String recipeId, String notes, float rating) {
        repository.updateNotesAndRating(recipeId, notes, rating);
    }

    /**
     * DELETE - Clear all favorites
     */
    public void clearAllFavorites() {
        repository.deleteAllFavorites();
    }
}
