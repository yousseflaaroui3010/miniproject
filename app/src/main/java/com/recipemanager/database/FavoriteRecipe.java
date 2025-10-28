package com.recipemanager.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.io.Serializable;

/**
 * Room database entity representing a saved/favorite recipe
 * This is the table structure for local storage
 * Each instance of this class = one row in the database
 * Implements Serializable so it can be passed between fragments
 */
@Entity(tableName = "favorite_recipes")
public class FavoriteRecipe implements Serializable {

    private static final long serialVersionUID = 1L;

    // Primary key - uses the meal ID from the API (prevents duplicates)
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    // Recipe name (e.g., "Spaghetti Carbonara")
    @ColumnInfo(name = "name")
    private String name;

    // URL to recipe image
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    // Category (e.g., "Chicken", "Dessert")
    @ColumnInfo(name = "category")
    private String category;

    // Country/area of origin (e.g., "Italian")
    @ColumnInfo(name = "area")
    private String area;

    // Full cooking instructions (can be long text)
    @ColumnInfo(name = "instructions")
    private String instructions;

    // Ingredients formatted as a single string
    // Example: "200g Pasta, 100g Bacon, 2 Eggs"
    @ColumnInfo(name = "ingredients")
    private String ingredients;

    // YouTube video URL (optional, can be null)
    @ColumnInfo(name = "video_url")
    private String videoUrl;

    // ===== USER-SPECIFIC FIELDS (not from API) =====

    // Personal notes added by user
    @ColumnInfo(name = "user_notes")
    private String userNotes;

    // User's rating (1.0 to 5.0 stars, default 0.0 = not rated)
    @ColumnInfo(name = "rating")
    private float rating;

    // Timestamp when user saved this recipe (for sorting by date)
    @ColumnInfo(name = "date_added")
    private long dateAdded;

    // Empty constructor required by Room
    public FavoriteRecipe() {
    }

    // Constructor with all fields
    public FavoriteRecipe(@NonNull String id, String name, String imageUrl, String category,
                          String area, String instructions, String ingredients, String videoUrl,
                          String userNotes, float rating, long dateAdded) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.videoUrl = videoUrl;
        this.userNotes = userNotes;
        this.rating = rating;
        this.dateAdded = dateAdded;
    }

    // Getters and Setters for all fields

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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
}
