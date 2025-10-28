package com.recipemanager.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Main recipe data model that represents a single recipe
 * Used for both API responses and local database storage
 * Each field maps to the API's JSON structure
 * Implements Serializable so it can be passed between fragments
 */
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    // Unique identifier from TheMealDB API
    @SerializedName("idMeal")
    private String id;

    // Name of the recipe (e.g., "Spaghetti Carbonara")
    @SerializedName("strMeal")
    private String name;

    // Category like "Chicken", "Beef", "Dessert"
    @SerializedName("strCategory")
    private String category;

    // Country of origin (e.g., "Italian", "Chinese")
    @SerializedName("strArea")
    private String area;

    // Full cooking instructions as a single text block
    @SerializedName("strInstructions")
    private String instructions;

    // URL to the recipe image
    @SerializedName("strMealThumb")
    private String imageUrl;

    // YouTube video link (if available)
    @SerializedName("strYoutube")
    private String videoUrl;

    // Individual ingredient fields from API (TheMealDB has up to 20 ingredients)
    @SerializedName("strIngredient1") private String ingredient1;
    @SerializedName("strIngredient2") private String ingredient2;
    @SerializedName("strIngredient3") private String ingredient3;
    @SerializedName("strIngredient4") private String ingredient4;
    @SerializedName("strIngredient5") private String ingredient5;
    @SerializedName("strIngredient6") private String ingredient6;
    @SerializedName("strIngredient7") private String ingredient7;
    @SerializedName("strIngredient8") private String ingredient8;
    @SerializedName("strIngredient9") private String ingredient9;
    @SerializedName("strIngredient10") private String ingredient10;
    @SerializedName("strIngredient11") private String ingredient11;
    @SerializedName("strIngredient12") private String ingredient12;
    @SerializedName("strIngredient13") private String ingredient13;
    @SerializedName("strIngredient14") private String ingredient14;
    @SerializedName("strIngredient15") private String ingredient15;
    @SerializedName("strIngredient16") private String ingredient16;
    @SerializedName("strIngredient17") private String ingredient17;
    @SerializedName("strIngredient18") private String ingredient18;
    @SerializedName("strIngredient19") private String ingredient19;
    @SerializedName("strIngredient20") private String ingredient20;

    // Individual measure fields (amounts like "1 cup", "200g")
    @SerializedName("strMeasure1") private String measure1;
    @SerializedName("strMeasure2") private String measure2;
    @SerializedName("strMeasure3") private String measure3;
    @SerializedName("strMeasure4") private String measure4;
    @SerializedName("strMeasure5") private String measure5;
    @SerializedName("strMeasure6") private String measure6;
    @SerializedName("strMeasure7") private String measure7;
    @SerializedName("strMeasure8") private String measure8;
    @SerializedName("strMeasure9") private String measure9;
    @SerializedName("strMeasure10") private String measure10;
    @SerializedName("strMeasure11") private String measure11;
    @SerializedName("strMeasure12") private String measure12;
    @SerializedName("strMeasure13") private String measure13;
    @SerializedName("strMeasure14") private String measure14;
    @SerializedName("strMeasure15") private String measure15;
    @SerializedName("strMeasure16") private String measure16;
    @SerializedName("strMeasure17") private String measure17;
    @SerializedName("strMeasure18") private String measure18;
    @SerializedName("strMeasure19") private String measure19;
    @SerializedName("strMeasure20") private String measure20;

    // User-added fields (not from API, only for local favorites)
    private String userNotes;      // Personal notes about the recipe
    private float rating;          // User's rating (1.0 to 5.0 stars)
    private long dateAdded;        // When user saved it (timestamp)

    // Empty constructor required for database operations
    public Recipe() {
    }

    // Getters and setters for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Combines all 20 ingredient fields into a single formatted string
     * Filters out empty/null ingredients
     * Format: "Measure - Ingredient" per line
     * Example: "200g - Pasta\n100g - Bacon"
     */
    public String getFormattedIngredients() {
        StringBuilder ingredients = new StringBuilder();

        // Array of all ingredient fields for easy iteration
        String[] ingredientArray = {
            ingredient1, ingredient2, ingredient3, ingredient4, ingredient5,
            ingredient6, ingredient7, ingredient8, ingredient9, ingredient10,
            ingredient11, ingredient12, ingredient13, ingredient14, ingredient15,
            ingredient16, ingredient17, ingredient18, ingredient19, ingredient20
        };

        // Array of all measure fields
        String[] measureArray = {
            measure1, measure2, measure3, measure4, measure5,
            measure6, measure7, measure8, measure9, measure10,
            measure11, measure12, measure13, measure14, measure15,
            measure16, measure17, measure18, measure19, measure20
        };

        // Loop through all 20 possible ingredients
        for (int i = 0; i < 20; i++) {
            String ingredient = ingredientArray[i];
            String measure = measureArray[i];

            // Only add if ingredient exists and is not empty
            if (ingredient != null && !ingredient.trim().isEmpty()) {
                // Add measure if it exists, otherwise just ingredient
                if (measure != null && !measure.trim().isEmpty()) {
                    ingredients.append("• ").append(measure.trim()).append(" - ").append(ingredient.trim()).append("\n");
                } else {
                    ingredients.append("• ").append(ingredient.trim()).append("\n");
                }
            }
        }

        return ingredients.toString().trim();
    }

    /**
     * Returns a comma-separated string of all ingredients with measures
     * Used for storing in database as a single column
     */
    public String getIngredientsAsString() {
        return getFormattedIngredients().replace("\n", ", ");
    }
}
