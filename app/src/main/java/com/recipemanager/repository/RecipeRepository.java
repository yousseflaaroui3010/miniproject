package com.recipemanager.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.recipemanager.api.MealApiService;
import com.recipemanager.api.RetrofitClient;
import com.recipemanager.database.FavoriteRecipe;
import com.recipemanager.database.RecipeDao;
import com.recipemanager.database.RecipeDatabase;
import com.recipemanager.model.MealResponse;
import com.recipemanager.model.Recipe;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class - Single source of truth for all data operations
 * This class decides whether to fetch data from API or local database
 * Separates business logic from UI (keeps fragments clean)
 *
 * Think of this as a traffic controller:
 * - Fragment asks for data → Repository decides where to get it
 * - Could be from network (API) or local storage (Room)
 */
public class RecipeRepository {

    private static final String TAG = "RecipeRepository";

    // Database DAO for local operations
    private final RecipeDao recipeDao;

    // API service for network operations
    private final MealApiService apiService;

    // Executor for running database operations in background
    // Room requires database operations to run off main thread
    private final ExecutorService executorService;

    /**
     * Constructor - initialize database and API clients
     */
    public RecipeRepository(Application application) {
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDao = database.recipeDao();
        apiService = RetrofitClient.getApiService();
        executorService = Executors.newFixedThreadPool(2);  // 2 background threads
    }

    // ==================== API OPERATIONS (Network) ====================

    /**
     * Search recipes from API by name
     * Returns LiveData that UI can observe for changes
     */
    public LiveData<List<Recipe>> searchRecipes(String query) {
        MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

        // Make API call in background
        apiService.searchRecipes(query).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                // Check if request was successful and has data
                if (response.isSuccessful() && response.body() != null) {
                    MealResponse mealResponse = response.body();
                    if (mealResponse.hasResults()) {
                        // Update LiveData with results
                        recipesLiveData.postValue(mealResponse.getMeals());
                        Log.d(TAG, "Search successful: " + mealResponse.getCount() + " recipes found");
                    } else {
                        // No results found
                        recipesLiveData.postValue(null);
                        Log.d(TAG, "No recipes found for query: " + query);
                    }
                } else {
                    // API call failed
                    recipesLiveData.postValue(null);
                    Log.e(TAG, "API call failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                // Network error
                recipesLiveData.postValue(null);
                Log.e(TAG, "Network error: " + t.getMessage());
            }
        });

        return recipesLiveData;
    }

    /**
     * Filter recipes by category from API
     */
    public LiveData<List<Recipe>> filterByCategory(String category) {
        MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

        apiService.filterByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().hasResults()) {
                    recipesLiveData.postValue(response.body().getMeals());
                } else {
                    recipesLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                recipesLiveData.postValue(null);
                Log.e(TAG, "Category filter error: " + t.getMessage());
            }
        });

        return recipesLiveData;
    }

    /**
     * Get full recipe details by ID from API
     */
    public LiveData<Recipe> getRecipeDetails(String recipeId) {
        MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

        apiService.getRecipeById(recipeId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().hasResults()) {
                    // Get first meal from response (lookup returns single result)
                    recipeLiveData.postValue(response.body().getFirstMeal());
                } else {
                    recipeLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                recipeLiveData.postValue(null);
                Log.e(TAG, "Recipe details error: " + t.getMessage());
            }
        });

        return recipeLiveData;
    }

    /**
     * Get a random recipe from API
     */
    public LiveData<Recipe> getRandomRecipe() {
        MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

        apiService.getRandomRecipe().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().hasResults()) {
                    recipeLiveData.postValue(response.body().getFirstMeal());
                } else {
                    recipeLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                recipeLiveData.postValue(null);
                Log.e(TAG, "Random recipe error: " + t.getMessage());
            }
        });

        return recipeLiveData;
    }

    // ==================== DATABASE OPERATIONS (Local) ====================

    /**
     * CREATE - Insert a recipe to favorites
     * Runs in background thread (Room requirement)
     */
    public void insertFavorite(FavoriteRecipe recipe) {
        executorService.execute(() -> {
            try {
                recipeDao.insertRecipe(recipe);
                Log.d(TAG, "Recipe saved to favorites: " + recipe.getName());
            } catch (Exception e) {
                Log.e(TAG, "Error saving recipe: " + e.getMessage());
            }
        });
    }

    /**
     * UPDATE - Update an existing favorite recipe
     */
    public void updateFavorite(FavoriteRecipe recipe) {
        executorService.execute(() -> {
            try {
                recipeDao.updateRecipe(recipe);
                Log.d(TAG, "Recipe updated: " + recipe.getName());
            } catch (Exception e) {
                Log.e(TAG, "Error updating recipe: " + e.getMessage());
            }
        });
    }

    /**
     * UPDATE - Quick update for notes and rating only
     */
    public void updateNotesAndRating(String recipeId, String notes, float rating) {
        executorService.execute(() -> {
            try {
                recipeDao.updateNotesAndRating(recipeId, notes, rating);
                Log.d(TAG, "Notes and rating updated for recipe: " + recipeId);
            } catch (Exception e) {
                Log.e(TAG, "Error updating notes/rating: " + e.getMessage());
            }
        });
    }

    /**
     * DELETE - Remove a recipe from favorites
     */
    public void deleteFavorite(FavoriteRecipe recipe) {
        executorService.execute(() -> {
            try {
                recipeDao.deleteRecipe(recipe);
                Log.d(TAG, "Recipe deleted from favorites: " + recipe.getName());
            } catch (Exception e) {
                Log.e(TAG, "Error deleting recipe: " + e.getMessage());
            }
        });
    }

    /**
     * READ - Get all favorite recipes
     * Returns LiveData that automatically updates UI
     */
    public LiveData<List<FavoriteRecipe>> getAllFavorites() {
        return recipeDao.getAllFavorites();
    }

    /**
     * READ - Get a specific favorite by ID
     */
    public LiveData<FavoriteRecipe> getFavoriteById(String recipeId) {
        return recipeDao.getRecipeById(recipeId);
    }

    /**
     * READ - Search within favorites
     */
    public LiveData<List<FavoriteRecipe>> searchFavorites(String query) {
        return recipeDao.searchFavorites(query);
    }

    /**
     * READ - Filter favorites by category
     */
    public LiveData<List<FavoriteRecipe>> getFavoritesByCategory(String category) {
        return recipeDao.getFavoritesByCategory(category);
    }

    /**
     * READ - Get top rated recipes
     */
    public LiveData<List<FavoriteRecipe>> getTopRatedRecipes(float minRating) {
        return recipeDao.getTopRatedRecipes(minRating);
    }

    /**
     * READ - Get favorites count
     */
    public LiveData<Integer> getFavoritesCount() {
        return recipeDao.getFavoritesCount();
    }

    /**
     * Check if a recipe is favorited (synchronous - use in background)
     */
    public void isRecipeFavorited(String recipeId, FavoriteCheckCallback callback) {
        executorService.execute(() -> {
            boolean isFavorited = recipeDao.isRecipeFavorited(recipeId);
            callback.onResult(isFavorited);
        });
    }

    /**
     * DELETE - Clear all favorites
     */
    public void deleteAllFavorites() {
        executorService.execute(() -> {
            try {
                recipeDao.deleteAllRecipes();
                Log.d(TAG, "All favorites cleared");
            } catch (Exception e) {
                Log.e(TAG, "Error clearing favorites: " + e.getMessage());
            }
        });
    }

    /**
     * Callback interface for async favorite check
     */
    public interface FavoriteCheckCallback {
        void onResult(boolean isFavorited);
    }
}
