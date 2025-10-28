package com.recipemanager.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.recipemanager.R;
import com.recipemanager.database.FavoriteRecipe;
import com.recipemanager.model.Recipe;
import com.recipemanager.viewmodel.RecipeDetailViewModel;

/**
 * RecipeDetailFragment - Display full recipe details
 * Features:
 * - Full recipe information (image, ingredients, instructions)
 * - Add/remove from favorites
 * - Add notes and rating (for favorited recipes)
 * - Watch video button
 */
public class RecipeDetailFragment extends Fragment {

    // ViewModel
    private RecipeDetailViewModel viewModel;

    // UI Components
    private ImageView recipeImage;
    private TextView recipeName;
    private TextView recipeCategory;
    private TextView recipeIngredients;
    private TextView recipeInstructions;
    private MaterialButton btnWatchVideo;
    private FloatingActionButton btnFavorite;
    private LinearLayout userNotesSection;
    private EditText userNotesInput;
    private RatingBar ratingBar;
    private MaterialButton btnSaveChanges;
    private ProgressBar progressBar;

    // Current recipe
    private Recipe currentRecipe;

    // Flag to check if recipe is from favorites
    private boolean isFromFavorites = false;

    // Bundle keys
    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_FAVORITE = "favorite";

    /**
     * Create new instance with Recipe object
     */
    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create new instance with FavoriteRecipe object
     */
    public static RecipeDetailFragment newInstanceFromFavorite(FavoriteRecipe favorite) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FAVORITE, favorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(RecipeDetailViewModel.class);

        // Find views
        initViews(view);

        // Get recipe from arguments
        loadRecipeFromArguments();

        // Setup click listeners
        setupClickListeners();

        // Observe ViewModel
        observeViewModel();
    }

    /**
     * Initialize all view references
     */
    private void initViews(View view) {
        recipeImage = view.findViewById(R.id.recipe_image);
        recipeName = view.findViewById(R.id.recipe_name);
        recipeCategory = view.findViewById(R.id.recipe_category);
        recipeIngredients = view.findViewById(R.id.recipe_ingredients);
        recipeInstructions = view.findViewById(R.id.recipe_instructions);
        btnWatchVideo = view.findViewById(R.id.btn_watch_video);
        btnFavorite = view.findViewById(R.id.btn_favorite);
        userNotesSection = view.findViewById(R.id.user_notes_section);
        userNotesInput = view.findViewById(R.id.user_notes_input);
        ratingBar = view.findViewById(R.id.rating_bar);
        btnSaveChanges = view.findViewById(R.id.btn_save_changes);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    /**
     * Load recipe from arguments
     */
    private void loadRecipeFromArguments() {
        if (getArguments() != null) {
            // Check if coming from favorites
            if (getArguments().containsKey(ARG_FAVORITE)) {
                FavoriteRecipe favorite = (FavoriteRecipe) getArguments().getSerializable(ARG_FAVORITE);
                if (favorite != null) {
                    viewModel.setCurrentFavorite(favorite);
                    isFromFavorites = true;
                }
            }
            // Check if coming from search/API
            else if (getArguments().containsKey(ARG_RECIPE)) {
                Recipe recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
                if (recipe != null) {
                    viewModel.setCurrentRecipe(recipe);
                }
            }
        }
    }

    /**
     * Setup click listeners
     */
    private void setupClickListeners() {
        // Favorite button click
        btnFavorite.setOnClickListener(v -> {
            viewModel.toggleFavorite();
        });

        // Watch video button click
        btnWatchVideo.setOnClickListener(v -> {
            if (currentRecipe != null && currentRecipe.getVideoUrl() != null) {
                openVideo(currentRecipe.getVideoUrl());
            }
        });

        // Save changes button click
        btnSaveChanges.setOnClickListener(v -> {
            saveNotesAndRating();
        });
    }

    /**
     * Observe ViewModel LiveData
     */
    private void observeViewModel() {
        // Observe current recipe
        viewModel.getCurrentRecipe().observe(getViewLifecycleOwner(), recipe -> {
            if (recipe != null) {
                currentRecipe = recipe;
                displayRecipe(recipe);
            }
        });

        // Observe favorite status
        viewModel.getFavoriteStatus().observe(getViewLifecycleOwner(), isFavorited -> {
            updateFavoriteButton(isFavorited);

            // Show/hide user notes section based on favorite status
            if (isFavorited) {
                showUserNotesSection();
            } else {
                hideUserNotesSection();
            }
        });

        // Observe loading state
        viewModel.getLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Display recipe information
     */
    private void displayRecipe(Recipe recipe) {
        // Set recipe name
        recipeName.setText(recipe.getName());

        // Set category and area
        String categoryText = recipe.getCategory();
        if (recipe.getArea() != null && !recipe.getArea().isEmpty()) {
            categoryText += " • " + recipe.getArea();
        }
        recipeCategory.setText(categoryText);

        // Set ingredients (formatted)
        String ingredients = recipe.getFormattedIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            recipeIngredients.setText(ingredients);
        } else {
            recipeIngredients.setText("No ingredients available");
        }

        // Set instructions
        String instructions = recipe.getInstructions();
        if (instructions != null && !instructions.isEmpty()) {
            recipeInstructions.setText(instructions);
        } else {
            recipeInstructions.setText("No instructions available");
        }

        // Set video button visibility
        if (recipe.getVideoUrl() != null && !recipe.getVideoUrl().isEmpty()) {
            btnWatchVideo.setVisibility(View.VISIBLE);
        } else {
            btnWatchVideo.setVisibility(View.GONE);
        }

        // Load image
        Glide.with(this)
                .load(recipe.getImageUrl())
                .placeholder(R.color.background_gray)
                .error(R.color.error)
                .centerCrop()
                .into(recipeImage);

        // If recipe has user notes and rating, display them
        if (recipe.getUserNotes() != null && !recipe.getUserNotes().isEmpty()) {
            userNotesInput.setText(recipe.getUserNotes());
        }
        if (recipe.getRating() > 0) {
            ratingBar.setRating(recipe.getRating());
        }
    }

    /**
     * Update favorite button icon
     */
    private void updateFavoriteButton(boolean isFavorited) {
        if (isFavorited) {
            btnFavorite.setImageResource(android.R.drawable.star_big_on);
            Toast.makeText(getContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
        } else {
            btnFavorite.setImageResource(android.R.drawable.star_big_off);
            if (isFromFavorites) {
                // If we removed from favorites and were viewing from favorites tab, go back
                getParentFragmentManager().popBackStack();
            }
        }
    }

    /**
     * Show user notes section
     */
    private void showUserNotesSection() {
        userNotesSection.setVisibility(View.VISIBLE);
    }

    /**
     * Hide user notes section
     */
    private void hideUserNotesSection() {
        userNotesSection.setVisibility(View.GONE);
    }

    /**
     * Save notes and rating
     */
    private void saveNotesAndRating() {
        String notes = userNotesInput.getText().toString().trim();
        float rating = ratingBar.getRating();

        // Update via ViewModel
        viewModel.updateNotesAndRating(notes, rating);

        Toast.makeText(getContext(), "Changes saved!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Open video in browser or YouTube app
     */
    private void openVideo(String videoUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Cannot open video", Toast.LENGTH_SHORT).show();
        }
    }
}
