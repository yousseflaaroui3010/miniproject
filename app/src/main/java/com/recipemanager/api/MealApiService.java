package com.recipemanager.api;

import com.recipemanager.model.MealResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface defining all API endpoints from TheMealDB
 * Each method represents one API call
 * Retrofit automatically handles the HTTP request/response
 */
public interface MealApiService {

    /**
     * Search recipes by name
     * Example: searchRecipes("pasta") → https://themealdb.com/api/json/v1/1/search.php?s=pasta
     * Returns list of recipes matching the search term
     */
    @GET("search.php")
    Call<MealResponse> searchRecipes(@Query("s") String searchQuery);

    /**
     * Filter recipes by category
     * Example: filterByCategory("Seafood") → .../filter.php?c=Seafood
     * Categories: Chicken, Beef, Seafood, Vegetarian, Dessert, etc.
     */
    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);

    /**
     * Get full recipe details by ID
     * Example: getRecipeById("52772") → .../lookup.php?i=52772
     * Used when user clicks a recipe card to see full details
     */
    @GET("lookup.php")
    Call<MealResponse> getRecipeById(@Query("i") String mealId);

    /**
     * Get a random recipe
     * Endpoint: .../random.php
     * Perfect for "I'm feeling lucky" feature
     */
    @GET("random.php")
    Call<MealResponse> getRandomRecipe();

    /**
     * List all available categories
     * Returns category names with images
     * Used to populate category filter chips
     */
    @GET("categories.php")
    Call<MealResponse> getCategories();
}
