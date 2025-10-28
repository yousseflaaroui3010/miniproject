# Recipe Manager - Development Guide 🛠️

This guide explains how to build, run, and understand the Recipe Manager Android application.

## 📋 Table of Contents
1. [Quick Start](#quick-start)
2. [Project Structure Explained](#project-structure-explained)
3. [How Components Work Together](#how-components-work-together)
4. [Common Tasks](#common-tasks)
5. [Troubleshooting](#troubleshooting)
6. [Code Examples](#code-examples)

---

## 🚀 Quick Start

### Step 1: Open in Android Studio

1. Launch Android Studio
2. Click "Open" or "Open an Existing Project"
3. Navigate to `C:\Users\lenovo\OneDrive\Desktop\miniproject`
4. Click "OK"

### Step 2: Wait for Gradle Sync

- Android Studio will automatically start syncing
- Watch the bottom status bar for progress
- This may take 2-5 minutes on first run
- If errors occur, click "Sync Project with Gradle Files" (elephant icon)

### Step 3: Run the App

1. **Connect Device** or **Start Emulator**
   - For emulator: Tools → Device Manager → Create Device
   - Recommended: Pixel 4, API 30+

2. **Click Run**
   - Green triangle button in toolbar
   - Or press `Shift + F10`

3. **Select Device**
   - Choose your connected device or emulator
   - Click "OK"

4. **Wait for Build**
   - First build takes 1-2 minutes
   - Subsequent builds are faster

---

## 📁 Project Structure Explained

### Root Files
```
miniproject/
├── app/                    # Main application module
├── build.gradle           # Project-level Gradle config
├── settings.gradle        # Project settings
├── gradle.properties      # Gradle properties
├── README.md             # Project documentation
└── project_SBS.md        # Step-by-step build guide
```

### app/ Directory
```
app/
├── src/
│   └── main/
│       ├── java/com/recipemanager/  # All Java code
│       │   ├── model/               # Data models
│       │   ├── api/                 # Retrofit API
│       │   ├── database/            # Room database
│       │   ├── repository/          # Data layer
│       │   ├── viewmodel/           # Business logic
│       │   ├── ui/                  # User interface
│       │   │   ├── activities/      # MainActivity
│       │   │   ├── fragments/       # Screen fragments
│       │   │   └── adapters/        # RecyclerView adapters
│       │   └── utils/               # Helper classes
│       ├── res/                     # Resources
│       │   ├── layout/              # XML layouts
│       │   ├── values/              # Strings, colors, etc.
│       │   ├── drawable/            # Images (empty for now)
│       │   ├── menu/                # Menu files
│       │   └── xml/                 # Configuration files
│       └── AndroidManifest.xml      # App configuration
├── build.gradle                     # App-level Gradle config
└── proguard-rules.pro              # Code obfuscation rules
```

---

## 🔄 How Components Work Together

### Data Flow Example: Searching for Recipes

```
1. USER types "pasta" in SearchFragment
   ↓
2. SearchFragment calls SearchViewModel.searchRecipes("pasta")
   ↓
3. SearchViewModel calls RecipeRepository.searchRecipes("pasta")
   ↓
4. RecipeRepository calls RetrofitClient.getApiService().searchRecipes("pasta")
   ↓
5. Retrofit makes HTTP request to TheMealDB API
   ↓
6. API returns JSON → Retrofit converts to List<Recipe>
   ↓
7. Repository wraps result in LiveData and returns it
   ↓
8. ViewModel observes LiveData, updates UI-facing LiveData
   ↓
9. SearchFragment observes ViewModel's LiveData
   ↓
10. Fragment updates RecipeAdapter with new data
    ↓
11. RecyclerView displays recipe cards to USER
```

### Favorite Flow Example: Saving a Recipe

```
1. USER clicks star button in RecipeDetailFragment
   ↓
2. Fragment calls RecipeDetailViewModel.toggleFavorite()
   ↓
3. ViewModel calls RecipeRepository.insertFavorite(recipe)
   ↓
4. Repository runs database insert on background thread
   ↓
5. Room inserts FavoriteRecipe into SQLite database
   ↓
6. ViewModel updates isFavorited LiveData to true
   ↓
7. Fragment observes change, updates star icon to filled
   ↓
8. USER sees visual confirmation (star is now filled)
```

---

## 🔧 Common Tasks

### Adding a New Category

**File**: `SearchFragment.java:52`
```java
private final String[] categories = {
    "Chicken", "Beef", "Seafood", "Vegetarian",
    "Dessert", "Pasta", "Pork", "Breakfast",
    "Lamb",  // ← Add new category here
    "Vegan"  // ← Another new category
};
```

### Changing the Default Search

**File**: `SearchFragment.java:113`
```java
// Load default recipes on first load
if (savedInstanceState == null) {
    searchRecipes("chicken");  // ← Change "chicken" to anything
}
```

### Changing Grid Column Count

**File**: `SearchFragment.java:128`
```java
// Change 2 to 3 for 3 columns, or 1 for list view
recipesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
```

### Adding a Database Field

**Step 1**: Add field to `FavoriteRecipe.java`
```java
@ColumnInfo(name = "cooking_time")
private int cookingTime;

public int getCookingTime() { return cookingTime; }
public void setCookingTime(int time) { this.cookingTime = time; }
```

**Step 2**: Add DAO method in `RecipeDao.java`
```java
@Query("SELECT * FROM favorite_recipes WHERE cooking_time < :maxTime")
LiveData<List<FavoriteRecipe>> getQuickRecipes(int maxTime);
```

**Step 3**: Increment database version in `RecipeDatabase.java`
```java
@Database(entities = {FavoriteRecipe.class}, version = 2)  // ← Change to 2
```

### Changing App Colors

**File**: `res/values/colors.xml`
```xml
<!-- Change primary color -->
<color name="primary">#FF6B35</color>  <!-- Orange (current) -->
<color name="primary">#2196F3</color>  <!-- Blue (example) -->
<color name="primary">#4CAF50</color>  <!-- Green (example) -->
```

---

## 🐛 Troubleshooting

### Problem: "Unresolved reference" errors

**Cause**: Gradle hasn't synced or dependencies missing

**Solution**:
1. Click "Sync Project with Gradle Files" (elephant icon)
2. If that fails: File → Invalidate Caches → Restart
3. If still failing: Check `build.gradle` dependencies

### Problem: App crashes on launch

**Cause**: Usually missing internet permission or null pointer

**Solution**:
1. Check Logcat (bottom panel in Android Studio)
2. Look for stack trace (red text)
3. Common fixes:
   - Ensure `AndroidManifest.xml` has `<uses-permission android:name="android.permission.INTERNET" />`
   - Check for null checks in code

### Problem: Images not loading

**Cause**: No internet or Glide not configured

**Solution**:
1. Check device has internet connection
2. Ensure `AndroidManifest.xml` has internet permission
3. Check if URLs are valid (open in browser)
4. Add to `AndroidManifest.xml`:
   ```xml
   android:usesCleartextTraffic="true"
   ```

### Problem: Database not saving favorites

**Cause**: Room operations on main thread or DAO not called

**Solution**:
1. Check Repository uses `executorService.execute()` for inserts
2. Verify DAO methods are annotated correctly
3. Check Logcat for "Cannot access database on main thread" error

### Problem: Screen rotation loses data

**Cause**: Not using ViewModel properly

**Solution**:
1. Ensure data is stored in ViewModel, not Fragment
2. Use LiveData for reactive updates
3. ViewModel survives rotation automatically

---

## 💡 Code Examples

### Example 1: Making a New API Call

**Step 1**: Add endpoint to `MealApiService.java`
```java
@GET("list.php?a=list")
Call<MealResponse> getAreas();
```

**Step 2**: Add repository method
```java
public LiveData<List<String>> getAreas() {
    MutableLiveData<List<String>> areasLiveData = new MutableLiveData<>();

    apiService.getAreas().enqueue(new Callback<MealResponse>() {
        @Override
        public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
            if (response.isSuccessful()) {
                // Process response
                areasLiveData.postValue(processAreas(response.body()));
            }
        }

        @Override
        public void onFailure(Call<MealResponse> call, Throwable t) {
            areasLiveData.postValue(null);
        }
    });

    return areasLiveData;
}
```

**Step 3**: Call from ViewModel
```java
public LiveData<List<String>> getAreas() {
    return repository.getAreas();
}
```

**Step 4**: Observe in Fragment
```java
viewModel.getAreas().observe(getViewLifecycleOwner(), areas -> {
    if (areas != null) {
        // Update UI with areas
    }
});
```

### Example 2: Adding a New Fragment

**Step 1**: Create XML layout `fragment_settings.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Settings"
        android:textSize="24sp" />

</LinearLayout>
```

**Step 2**: Create Fragment class
```java
package com.recipemanager.ui.fragments;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
```

**Step 3**: Add to bottom navigation `menu/bottom_nav_menu.xml`
```xml
<item
    android:id="@+id/navigation_settings"
    android:icon="@android:drawable/ic_menu_preferences"
    android:title="Settings" />
```

**Step 4**: Handle in MainActivity
```java
int itemId = item.getItemId();
if (itemId == R.id.navigation_settings) {
    selectedFragment = new SettingsFragment();
}
```

### Example 3: Custom Database Query

**In RecipeDao.java**:
```java
// Get favorites added in last 7 days
@Query("SELECT * FROM favorite_recipes WHERE date_added > :sevenDaysAgo ORDER BY date_added DESC")
LiveData<List<FavoriteRecipe>> getRecentFavorites(long sevenDaysAgo);

// Usage in Repository:
public LiveData<List<FavoriteRecipe>> getRecentFavorites() {
    long sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000);
    return recipeDao.getRecentFavorites(sevenDaysAgo);
}
```

---

## 🎓 Learning Resources

### Understanding MVVM
1. **Model**: Recipe.java, FavoriteRecipe.java (data)
2. **View**: SearchFragment.java (UI)
3. **ViewModel**: SearchViewModel.java (business logic)

### Understanding Room
- **Entity**: FavoriteRecipe.java (table structure)
- **DAO**: RecipeDao.java (database operations)
- **Database**: RecipeDatabase.java (database instance)

### Understanding Retrofit
- **Service**: MealApiService.java (API endpoints)
- **Client**: RetrofitClient.java (configuration)
- **Models**: Recipe.java, MealResponse.java (JSON mapping)

---

## 📚 Additional Tips

### Debugging
1. **Logcat**: View → Tool Windows → Logcat
2. **Breakpoints**: Click left margin of code line
3. **Debug Mode**: Green bug icon (Shift + F9)

### Performance
- Use RecyclerView, not ListView
- Cache images with Glide
- Run database ops on background thread
- Use ViewBinding instead of findViewById

### Best Practices
- Always check for null
- Use try-catch for network/database ops
- Handle configuration changes with ViewModel
- Follow naming conventions (camelCase for variables)

---

**Happy Coding! 🚀**

For questions or issues, review the comments in the code - they're written in simple English to help you understand each part.
