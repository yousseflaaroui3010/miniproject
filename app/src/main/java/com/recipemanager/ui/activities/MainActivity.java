package com.recipemanager.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.recipemanager.R;
import com.recipemanager.ui.fragments.FavoritesFragment;
import com.recipemanager.ui.fragments.SearchFragment;

/**
 * Main Activity - Entry point of the app
 * Manages bottom navigation and fragment switching
 * Uses a single activity with multiple fragments (modern Android pattern)
 */
public class MainActivity extends AppCompatActivity {

    // Bottom navigation view
    private BottomNavigationView bottomNavigation;

    // Current fragment being displayed
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize bottom navigation
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Set up navigation listener
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Determine which fragment to show based on menu item clicked
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.navigation_favorites) {
                selectedFragment = new FavoritesFragment();
            }

            // Replace current fragment with selected one
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });

        // Load SearchFragment by default (only if savedInstanceState is null)
        // This prevents recreating fragment on rotation
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.navigation_search);
        }
    }

    /**
     * Load a fragment into the container
     * Uses replace to swap fragments
     */
    private void loadFragment(Fragment fragment) {
        // Only replace if it's a different fragment (performance optimization)
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        currentFragment = fragment;

        // Perform fragment transaction
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }

    /**
     * Handle back press - exit app if on main fragments
     */
    @Override
    public void onBackPressed() {
        // Check if there are fragments in back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            // No fragments in back stack, exit app
            super.onBackPressed();
        }
    }
}
