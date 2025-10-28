package com.recipemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;

import java.util.List;

/**
 * ViewModel for SearchFragment
 * Handles business logic for searching and browsing recipes from API
 * Survives configuration changes (screen rotation)
 */
public class SearchViewModel extends AndroidViewModel {

    // Repository for data operations
    private final RecipeRepository repository;

    // LiveData for search results
    private MutableLiveData<List<Recipe>> searchResults;

    // LiveData for loading state
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    // LiveData for error messages
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Current search query (saves state during rotation)
    private String currentQuery = "";

    /**
     * Constructor - requires Application context
     */
    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    /**
     * Search recipes by name
     */
    public LiveData<List<Recipe>> searchRecipes(String query) {
        if (query == null || query.trim().isEmpty()) {
            errorMessage.setValue("Please enter a search term");
            return new MutableLiveData<>();
        }

        currentQuery = query;
        isLoading.setValue(true);

        searchResults = (MutableLiveData<List<Recipe>>) repository.searchRecipes(query);

        // Update loading state when results arrive
        searchResults.observeForever(recipes -> {
            isLoading.setValue(false);
            if (recipes == null || recipes.isEmpty()) {
                errorMessage.setValue("No recipes found for: " + query);
            }
        });

        return searchResults;
    }

    /**
     * Filter recipes by category
     */
    public LiveData<List<Recipe>> filterByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return new MutableLiveData<>();
        }

        isLoading.setValue(true);

        searchResults = (MutableLiveData<List<Recipe>>) repository.filterByCategory(category);

        searchResults.observeForever(recipes -> {
            isLoading.setValue(false);
            if (recipes == null || recipes.isEmpty()) {
                errorMessage.setValue("No recipes found in category: " + category);
            }
        });

        return searchResults;
    }

    /**
     * Get a random recipe
     */
    public LiveData<Recipe> getRandomRecipe() {
        isLoading.setValue(true);

        LiveData<Recipe> randomRecipe = repository.getRandomRecipe();

        randomRecipe.observeForever(recipe -> {
            isLoading.setValue(false);
            if (recipe == null) {
                errorMessage.setValue("Failed to load random recipe");
            }
        });

        return randomRecipe;
    }

    /**
     * Get current search results (preserves data during rotation)
     */
    public LiveData<List<Recipe>> getSearchResults() {
        if (searchResults == null) {
            searchResults = new MutableLiveData<>();
        }
        return searchResults;
    }

    /**
     * Get loading state
     */
    public LiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    /**
     * Get error messages
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Get current query
     */
    public String getCurrentQuery() {
        return currentQuery;
    }

    /**
     * Clear error message
     */
    public void clearError() {
        errorMessage.setValue(null);
    }
}
