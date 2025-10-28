package com.recipemanager.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.recipemanager.R;
import com.recipemanager.model.Recipe;
import com.recipemanager.ui.adapters.RecipeAdapter;
import com.recipemanager.viewmodel.SearchViewModel;

/**
 * SearchFragment - Browse and search recipes from API
 * Features:
 * - Search by name
 * - Filter by category
 * - Random recipe button
 * - Pull to refresh
 */
public class SearchFragment extends Fragment {

    // ViewModel for managing data
    private SearchViewModel viewModel;

    // UI Components
    private EditText searchEditText;
    private RecyclerView recipesRecyclerView;
    private ProgressBar progressBar;
    private LinearLayout emptyState;
    private SwipeRefreshLayout swipeRefresh;
    private ChipGroup categoryChipGroup;
    private FloatingActionButton btnRandom;

    // Adapter for RecyclerView
    private RecipeAdapter recipeAdapter;

    // Available categories for filtering
    private final String[] categories = {
            "Chicken", "Beef", "Seafood", "Vegetarian",
            "Dessert", "Pasta", "Pork", "Breakfast"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Find views
        initViews(view);

        // Setup RecyclerView
        setupRecyclerView();

        // Setup category chips
        setupCategoryChips();

        // Setup search functionality
        setupSearch();

        // Setup random recipe button
        setupRandomButton();

        // Setup swipe to refresh
        setupSwipeRefresh();

        // Observe ViewModel data
        observeViewModel();

        // Load default recipes (search for "chicken" on first load)
        if (savedInstanceState == null) {
            searchRecipes("chicken");
        }
    }

    /**
     * Initialize all view references
     */
    private void initViews(View view) {
        searchEditText = view.findViewById(R.id.search_edit_text);
        recipesRecyclerView = view.findViewById(R.id.recipes_recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        emptyState = view.findViewById(R.id.empty_state);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        categoryChipGroup = view.findViewById(R.id.category_chip_group);
        btnRandom = view.findViewById(R.id.btn_random);
    }

    /**
     * Setup RecyclerView with GridLayoutManager
     */
    private void setupRecyclerView() {
        // Create adapter
        recipeAdapter = new RecipeAdapter();

        // Set click listener on recipe cards
        recipeAdapter.setOnRecipeClickListener(recipe -> {
            // Navigate to detail fragment
            openRecipeDetail(recipe);
        });

        // Setup RecyclerView with grid layout (2 columns)
        recipesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recipesRecyclerView.setAdapter(recipeAdapter);
        recipesRecyclerView.setHasFixedSize(true);  // Performance optimization
    }

    /**
     * Setup category filter chips
     */
    private void setupCategoryChips() {
        // Add a chip for each category
        for (String category : categories) {
            Chip chip = new Chip(getContext());
            chip.setText(category);
            chip.setCheckable(true);
            chip.setChipBackgroundColorResource(R.color.chip_background);
            chip.setTextColor(getResources().getColor(R.color.chip_text, null));

            // Set click listener
            chip.setOnClickListener(v -> {
                if (chip.isChecked()) {
                    // Filter by this category
                    filterByCategory(category);
                } else {
                    // Unchecked - show default results
                    searchRecipes("chicken");
                }
            });

            categoryChipGroup.addView(chip);
        }
    }

    /**
     * Setup search functionality with debouncing
     */
    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Search as user types (with debouncing in production)
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    // Clear category selection when searching
                    categoryChipGroup.clearCheck();
                    searchRecipes(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    /**
     * Setup random recipe button
     */
    private void setupRandomButton() {
        btnRandom.setOnClickListener(v -> {
            // Clear search and category
            searchEditText.setText("");
            categoryChipGroup.clearCheck();

            // Get random recipe
            getRandomRecipe();
        });
    }

    /**
     * Setup swipe to refresh
     */
    private void setupSwipeRefresh() {
        swipeRefresh.setColorSchemeResources(R.color.primary);
        swipeRefresh.setOnRefreshListener(() -> {
            // Refresh current search
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                searchRecipes(query);
            } else {
                searchRecipes("chicken");
            }
        });
    }

    /**
     * Observe ViewModel LiveData
     */
    private void observeViewModel() {
        // Observe loading state
        viewModel.getLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                showLoading();
            } else {
                hideLoading();
                swipeRefresh.setRefreshing(false);
            }
        });

        // Observe error messages
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                showError(error);
                viewModel.clearError();
            }
        });
    }

    /**
     * Search recipes by query
     */
    private void searchRecipes(String query) {
        viewModel.searchRecipes(query).observe(getViewLifecycleOwner(), recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                // Show results
                recipeAdapter.setRecipes(recipes);
                showRecipes();
            } else {
                // Show empty state
                showEmptyState();
            }
        });
    }

    /**
     * Filter recipes by category
     */
    private void filterByCategory(String category) {
        // Clear search text
        searchEditText.setText("");

        viewModel.filterByCategory(category).observe(getViewLifecycleOwner(), recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                recipeAdapter.setRecipes(recipes);
                showRecipes();
            } else {
                showEmptyState();
            }
        });
    }

    /**
     * Get a random recipe
     */
    private void getRandomRecipe() {
        viewModel.getRandomRecipe().observe(getViewLifecycleOwner(), recipe -> {
            if (recipe != null) {
                // Open detail directly
                openRecipeDetail(recipe);
            } else {
                showError("Failed to load random recipe");
            }
        });
    }

    /**
     * Navigate to recipe detail fragment
     */
    private void openRecipeDetail(Recipe recipe) {
        RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance(recipe);

        // Navigate to detail fragment
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, detailFragment)
                .addToBackStack(null)  // Add to back stack so user can press back
                .commit();
    }

    /**
     * Show loading indicator
     */
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recipesRecyclerView.setVisibility(View.GONE);
        emptyState.setVisibility(View.GONE);
    }

    /**
     * Hide loading indicator
     */
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Show recipes RecyclerView
     */
    private void showRecipes() {
        recipesRecyclerView.setVisibility(View.VISIBLE);
        emptyState.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Show empty state
     */
    private void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        recipesRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Show error message
     */
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
