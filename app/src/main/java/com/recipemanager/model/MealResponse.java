package com.recipemanager.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Wrapper class for API responses from TheMealDB
 * The API always returns data wrapped in a "meals" array
 * Example JSON: { "meals": [ {...recipe data...}, {...recipe data...} ] }
 */
public class MealResponse {

    // List of recipes returned by the API
    // This field name must match the JSON key ("meals")
    @SerializedName("meals")
    private List<Recipe> meals;

    // Empty constructor
    public MealResponse() {
    }

    /**
     * Get the list of recipes from the response
     * Returns null if no meals found (important for error handling)
     */
    public List<Recipe> getMeals() {
        return meals;
    }

    public void setMeals(List<Recipe> meals) {
        this.meals = meals;
    }

    /**
     * Check if the response contains any recipes
     * Prevents null pointer exceptions
     */
    public boolean hasResults() {
        return meals != null && !meals.isEmpty();
    }

    /**
     * Get the first recipe from the response
     * Useful for endpoints that return single meals (like random.php)
     */
    public Recipe getFirstMeal() {
        if (hasResults()) {
            return meals.get(0);
        }
        return null;
    }

    /**
     * Get the count of recipes in the response
     */
    public int getCount() {
        return hasResults() ? meals.size() : 0;
    }
}
