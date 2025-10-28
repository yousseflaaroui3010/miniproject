package com.recipemanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room database singleton class
 * This creates and manages the SQLite database on the user's phone
 * Singleton pattern ensures only one database instance exists (prevents memory leaks)
 */
@Database(entities = {FavoriteRecipe.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    // Database name (stored in app's private storage)
    private static final String DATABASE_NAME = "recipe_manager_db";

    // Singleton instance
    private static RecipeDatabase instance;

    /**
     * Abstract method to get the DAO
     * Room automatically implements this
     */
    public abstract RecipeDao recipeDao();

    /**
     * Get database instance (creates it if doesn't exist)
     * Synchronized ensures thread safety (only one thread can access at a time)
     */
    public static synchronized RecipeDatabase getInstance(Context context) {
        // If database doesn't exist, create it
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),  // Use app context to prevent memory leaks
                    RecipeDatabase.class,
                    DATABASE_NAME
            )
            .fallbackToDestructiveMigration()  // If schema changes, recreate database (use migrations in production)
            .build();
        }
        return instance;
    }

    /**
     * Destroy the database instance (useful for testing)
     */
    public static void destroyInstance() {
        instance = null;
    }
}
