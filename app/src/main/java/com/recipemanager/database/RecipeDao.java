package com.recipemanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DAO (Data Access Object) for recipe database operations
 * This interface defines all the ways we can interact with the database
 * Room automatically implements these methods - we just declare them
 */
@Dao
public interface RecipeDao {

    /**
     * INSERT OPERATION - Save a new recipe to favorites
     * OnConflictStrategy.REPLACE: If recipe already exists (same ID), replace it
     * This prevents duplicate favorites
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(FavoriteRecipe recipe);

    /**
     * UPDATE OPERATION - Modify an existing recipe
     * Used when user edits notes or rating
     */
    @Update
    void updateRecipe(FavoriteRecipe recipe);

    /**
     * DELETE OPERATION - Remove a recipe from favorites
     * Used when user swipes to delete or clicks delete button
     */
    @Delete
    void deleteRecipe(FavoriteRecipe recipe);

    /**
     * READ OPERATION - Get all favorite recipes
     * LiveData automatically notifies UI when data changes (reactive programming)
     * Ordered by most recently added first
     */
    @Query("SELECT * FROM favorite_recipes ORDER BY date_added DESC")
    LiveData<List<FavoriteRecipe>> getAllFavorites();

    /**
     * READ OPERATION - Get a specific recipe by ID
     * Used to check if a recipe is already favorited
     * Returns LiveData so UI can react to changes
     */
    @Query("SELECT * FROM favorite_recipes WHERE id = :recipeId LIMIT 1")
    LiveData<FavoriteRecipe> getRecipeById(String recipeId);

    /**
     * READ OPERATION - Check if a recipe exists (synchronous)
     * Returns true/false for quick "is favorited?" checks
     * Use this in background thread only (Room doesn't allow main thread queries)
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_recipes WHERE id = :recipeId)")
    boolean isRecipeFavorited(String recipeId);

    /**
     * READ OPERATION - Search favorites by name
     * Used for search functionality within favorites
     * % is SQL wildcard (matches any characters)
     */
    @Query("SELECT * FROM favorite_recipes WHERE name LIKE '%' || :searchQuery || '%' ORDER BY date_added DESC")
    LiveData<List<FavoriteRecipe>> searchFavorites(String searchQuery);

    /**
     * READ OPERATION - Filter favorites by category
     * Used for category filtering in favorites tab
     */
    @Query("SELECT * FROM favorite_recipes WHERE category = :category ORDER BY date_added DESC")
    LiveData<List<FavoriteRecipe>> getFavoritesByCategory(String category);

    /**
     * READ OPERATION - Get top-rated recipes
     * Returns recipes with rating >= minimum rating, sorted by rating
     */
    @Query("SELECT * FROM favorite_recipes WHERE rating >= :minRating ORDER BY rating DESC, date_added DESC")
    LiveData<List<FavoriteRecipe>> getTopRatedRecipes(float minRating);

    /**
     * DELETE OPERATION - Remove all favorites
     * Used for "clear all" functionality
     */
    @Query("DELETE FROM favorite_recipes")
    void deleteAllRecipes();

    /**
     * READ OPERATION - Get total count of favorites
     * Useful for showing "You have X saved recipes"
     */
    @Query("SELECT COUNT(*) FROM favorite_recipes")
    LiveData<Integer> getFavoritesCount();

    /**
     * UPDATE OPERATION - Update only notes and rating (quick update)
     * More efficient than updating entire recipe
     */
    @Query("UPDATE favorite_recipes SET user_notes = :notes, rating = :rating WHERE id = :recipeId")
    void updateNotesAndRating(String recipeId, String notes, float rating);
}
