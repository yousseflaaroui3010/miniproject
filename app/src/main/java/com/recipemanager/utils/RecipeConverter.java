package com.recipemanager.utils;

import com.recipemanager.database.FavoriteRecipe;
import com.recipemanager.model.Recipe;

/**
 * Utility class to convert between different recipe data types
 * Converts API Recipe objects to Database FavoriteRecipe entities and vice versa
 */
public class RecipeConverter {

    /**
     * Convert API Recipe to Database FavoriteRecipe
     * Used when saving a recipe from search results to favorites
     */
    public static FavoriteRecipe recipeToFavorite(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        FavoriteRecipe favorite = new FavoriteRecipe();
        favorite.setId(recipe.getId());
        favorite.setName(recipe.getName());
        favorite.setImageUrl(recipe.getImageUrl());
        favorite.setCategory(recipe.getCategory());
        favorite.setArea(recipe.getArea());
        favorite.setInstructions(recipe.getInstructions());
        favorite.setIngredients(recipe.getFormattedIngredients());  // Convert to formatted string
        favorite.setVideoUrl(recipe.getVideoUrl());

        // Set default values for user fields
        favorite.setUserNotes("");
        favorite.setRating(0.0f);
        favorite.setDateAdded(System.currentTimeMillis());  // Current timestamp

        return favorite;
    }

    /**
     * Convert Database FavoriteRecipe to API Recipe format
     * Used when displaying favorites in the same UI as search results
     */
    public static Recipe favoriteToRecipe(FavoriteRecipe favorite) {
        if (favorite == null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(favorite.getId());
        recipe.setName(favorite.getName());
        recipe.setImageUrl(favorite.getImageUrl());
        recipe.setCategory(favorite.getCategory());
        recipe.setArea(favorite.getArea());
        recipe.setInstructions(favorite.getInstructions());
        recipe.setVideoUrl(favorite.getVideoUrl());

        // Copy user fields
        recipe.setUserNotes(favorite.getUserNotes());
        recipe.setRating(favorite.getRating());
        recipe.setDateAdded(favorite.getDateAdded());

        return recipe;
    }
}
