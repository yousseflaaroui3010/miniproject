package com.recipemanager.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Singleton class that creates and manages the Retrofit instance
 * This ensures we only have one Retrofit client throughout the app (saves memory)
 * Handles all the setup for making API calls to TheMealDB
 */
public class RetrofitClient {

    // Base URL for TheMealDB free API
    // We're using the free tier (v1/1) - no API key required
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    // Singleton instance - only one Retrofit client exists
    private static Retrofit retrofit = null;

    /**
     * Get the Retrofit instance (creates it if doesn't exist)
     * Thread-safe singleton pattern
     */
    public static Retrofit getClient() {
        // If retrofit doesn't exist yet, create it
        if (retrofit == null) {
            // Create HTTP logging interceptor for debugging
            // This prints API requests/responses in Logcat (super helpful for debugging)
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Build OkHttp client with custom settings
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)  // Add logging
                    .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
                    .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
                    .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
                    .retryOnConnectionFailure(true)        // Retry if connection fails
                    .build();

            // Build Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)                                    // Set base URL
                    .client(okHttpClient)                                 // Use custom OkHttp client
                    .addConverterFactory(GsonConverterFactory.create())   // JSON to Java conversion
                    .build();
        }

        return retrofit;
    }

    /**
     * Get the MealApiService instance
     * This is what you'll actually use to make API calls
     * Example usage: RetrofitClient.getApiService().searchRecipes("pasta")
     */
    public static MealApiService getApiService() {
        return getClient().create(MealApiService.class);
    }

    /**
     * Reset the Retrofit instance (useful for testing)
     */
    public static void resetClient() {
        retrofit = null;
    }
}
