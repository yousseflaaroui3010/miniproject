package com.recipemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.recipemanager.database.FavoriteRecipe;
import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.utils.RecipeConverter;

/**
 * ViewModel for RecipeDetailFragment
 * Handles displaying recipe details and managing favorite status
 * Manages the "Add to Favorites" functionality
 */
public class RecipeDetailViewModel extends AndroidViewModel {

    // Repository for data operations
    private final RecipeRepository repository;

    // Current recipe being displayed
    private final MutableLiveData<Recipe> currentRecipe = new MutableLiveData<>();

    // Favorite status of current recipe
    private final MutableLiveData<Boolean> isFavorited = new MutableLiveData<>(false);

    // Loading state
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    /**
     * Constructor
     */
    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    /**
     * Load recipe details from API
     */
    public void loadRecipeDetails(String recipeId) {
        isLoading.setValue(true);

        LiveData<Recipe> recipeLiveData = repository.getRecipeDetails(recipeId);
        recipeLiveData.observeForever(recipe -> {
            isLoading.setValue(false);
            currentRecipe.setValue(recipe);

            // Check if this recipe is favorited
            if (recipe != null) {
                checkFavoriteStatus(recipe.getId());
            }
        });
    }

    /**
     * Set current recipe (when coming from search results)
     */
    public void setCurrentRecipe(Recipe recipe) {
        currentRecipe.setValue(recipe);
        if (recipe != null) {
            checkFavoriteStatus(recipe.getId());
        }
    }

    /**
     * Set current recipe from favorite
     */
    public void setCurrentFavorite(FavoriteRecipe favorite) {
        Recipe recipe = RecipeConverter.favoriteToRecipe(favorite);
        currentRecipe.setValue(recipe);
        isFavorited.setValue(true);  // Already favorited
    }

    /**
     * Toggle favorite status
     */
    public void toggleFavorite() {
        Recipe recipe = currentRecipe.getValue();
        if (recipe == null) {
            return;
        }

        Boolean currentStatus = isFavorited.getValue();
        if (currentStatus != null && currentStatus) {
            // Remove from favorites
            removeFavorite(recipe);
        } else {
            // Add to favorites
            addFavorite(recipe);
        }
    }

    /**
     * Add recipe to favorites
     */
    private void addFavorite(Recipe recipe) {
        FavoriteRecipe favorite = RecipeConverter.recipeToFavorite(recipe);
        repository.insertFavorite(favorite);
        isFavorited.setValue(true);
    }

    /**
     * Remove recipe from favorites
     */
    private void removeFavorite(Recipe recipe) {
        FavoriteRecipe favorite = RecipeConverter.recipeToFavorite(recipe);
        repository.deleteFavorite(favorite);
        isFavorited.setValue(false);
    }

    /**
     * Update notes and rating for current recipe
     */
    public void updateNotesAndRating(String notes, float rating) {
        Recipe recipe = currentRecipe.getValue();
        if (recipe != null) {
            recipe.setUserNotes(notes);
            recipe.setRating(rating);
            repository.updateNotesAndRating(recipe.getId(), notes, rating);
        }
    }

    /**
     * Check if recipe is in favorites
     */
    private void checkFavoriteStatus(String recipeId) {
        repository.isRecipeFavorited(recipeId, isFavorited::postValue);
    }

    /**
     * Get current recipe
     */
    public LiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }

    /**
     * Get favorite status
     */
    public LiveData<Boolean> getFavoriteStatus() {
        return isFavorited;
    }

    /**
     * Get loading state
     */
    public LiveData<Boolean> getLoadingState() {
        return isLoading;
    }
}
