# Recipe Manager Android App 🍳

A production-grade Android recipe manager application built with Java, following modern Android development best practices (MVVM architecture, Room database, Retrofit for API calls).

## 📱 Features

### Core Features
- **Recipe Search & Browse**: Search 1000+ recipes from TheMealDB API
- **Category Filtering**: Filter recipes by categories (Chicken, Beef, Seafood, Vegetarian, Dessert, etc.)
- **Recipe Details**: View full recipe details including ingredients, instructions, and video tutorials
- **Favorites Management**: Save recipes locally for offline access
- **Personal Notes**: Add personal notes and ratings to saved recipes
- **Swipe to Delete**: Remove favorites with intuitive swipe gestures
- **Random Recipe**: "I'm Feeling Lucky" button for recipe discovery
- **Offline Support**: Access saved recipes without internet connection

## 🏗️ Architecture

This app follows **MVVM (Model-View-ViewModel)** architecture with clear separation of concerns:

```
app/
├── model/          # Data models (Recipe, MealResponse)
├── api/            # Retrofit API service interfaces
├── database/       # Room database (Entity, DAO, Database)
├── repository/     # Repository pattern (single source of truth)
├── viewmodel/      # ViewModels (business logic, survives rotation)
├── ui/
│   ├── activities/ # MainActivity
│   ├── fragments/  # SearchFragment, FavoritesFragment, RecipeDetailFragment
│   └── adapters/   # RecyclerView adapters
└── utils/          # Helper classes and converters
```

### Key Architectural Patterns

1. **Repository Pattern**: Single source of truth for data operations
2. **MVVM**: Clear separation between UI and business logic
3. **LiveData**: Reactive UI updates
4. **Room Database**: Type-safe local storage
5. **Retrofit**: Type-safe HTTP client for API calls

## 🛠️ Technologies & Libraries

### Core Android
- **Language**: Java 8
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

### Architecture Components
- **ViewModel**: Survives configuration changes
- **LiveData**: Observable data holder
- **Room**: Local database (SQLite wrapper)

### Networking
- **Retrofit 2.9.0**: REST API client
- **OkHttp 4.12.0**: HTTP client with logging interceptor
- **Gson**: JSON serialization/deserialization

### UI Components
- **Material Design 3**: Modern UI components
- **RecyclerView**: Efficient list display
- **SwipeRefreshLayout**: Pull-to-refresh functionality
- **Glide 4.16.0**: Image loading and caching

### Data Source
- **TheMealDB API**: Free recipe API (v1/1)
- Base URL: `https://www.themealdb.com/api/json/v1/1/`

## 📦 Project Structure

### Data Layer
```java
// Recipe model with API mapping
public class Recipe {
    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    // ... 20 ingredient fields

    // Helper method to format ingredients
    public String getFormattedIngredients() { ... }
}

// Room entity for local storage
@Entity(tableName = "favorite_recipes")
public class FavoriteRecipe {
    @PrimaryKey
    private String id;

    private String userNotes;  // User-added field
    private float rating;      // User-added field
}
```

### Repository Layer
```java
public class RecipeRepository {
    // API operations
    public LiveData<List<Recipe>> searchRecipes(String query);
    public LiveData<Recipe> getRecipeDetails(String id);

    // Database operations
    public void insertFavorite(FavoriteRecipe recipe);
    public LiveData<List<FavoriteRecipe>> getAllFavorites();
    public void deleteFavorite(FavoriteRecipe recipe);
}
```

### ViewModel Layer
```java
public class SearchViewModel extends AndroidViewModel {
    private RecipeRepository repository;

    public LiveData<List<Recipe>> searchRecipes(String query) {
        return repository.searchRecipes(query);
    }
}
```

### UI Layer
```java
public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private RecipeAdapter adapter;

    // Observe ViewModel data
    viewModel.searchRecipes("pasta").observe(this, recipes -> {
        adapter.setRecipes(recipes);
    });
}
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 8 or later
- Android device or emulator with API 24+

### Installation

1. **Clone the repository**
   ```bash
   cd C:\Users\lenovo\OneDrive\Desktop\miniproject
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project directory
   - Click "OK"

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle dependencies
   - Wait for the sync to complete

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green triangle)
   - Select your device
   - Wait for the app to build and install

### Build from Command Line
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

## 📖 API Documentation

### TheMealDB Endpoints Used

1. **Search by name**
   ```
   GET /search.php?s={query}
   Example: /search.php?s=pasta
   ```

2. **Filter by category**
   ```
   GET /filter.php?c={category}
   Example: /filter.php?c=Seafood
   ```

3. **Lookup by ID**
   ```
   GET /lookup.php?i={mealId}
   Example: /lookup.php?i=52772
   ```

4. **Random meal**
   ```
   GET /random.php
   ```

### Sample API Response
```json
{
  "meals": [
    {
      "idMeal": "52772",
      "strMeal": "Teriyaki Chicken",
      "strCategory": "Chicken",
      "strArea": "Japanese",
      "strInstructions": "...",
      "strMealThumb": "https://...",
      "strIngredient1": "soy sauce",
      "strMeasure1": "3 tbs",
      "strYoutube": "https://youtube.com/..."
    }
  ]
}
```

## 💾 Database Schema

### favorite_recipes Table
| Column       | Type   | Description                    |
|-------------|--------|--------------------------------|
| id          | String | Primary key (from API)         |
| name        | String | Recipe name                    |
| imageUrl    | String | Image URL                      |
| category    | String | Category (Chicken, Beef, etc.) |
| area        | String | Country of origin              |
| instructions| String | Cooking instructions           |
| ingredients | String | Formatted ingredients list     |
| videoUrl    | String | YouTube video URL              |
| userNotes   | String | User's personal notes          |
| rating      | Float  | User's rating (0-5 stars)      |
| dateAdded   | Long   | Timestamp when saved           |

## 🎨 UI/UX Features

### Material Design 3
- Modern card-based layouts
- Smooth transitions and animations
- Proper elevation and shadows
- Color scheme following Material guidelines

### User Experience
- **Loading States**: Progress indicators during API calls
- **Empty States**: Friendly messages when no data
- **Error Handling**: Toast messages for errors
- **Pull to Refresh**: SwipeRefreshLayout in search
- **Swipe to Delete**: Intuitive gesture in favorites
- **Image Caching**: Glide handles caching automatically

## 🔒 Best Practices Implemented

### Code Quality
- **SOLID Principles**: Single responsibility, dependency injection
- **Null Safety**: Proper null checks throughout
- **Error Handling**: Try-catch blocks, null checks
- **Resource Management**: Proper cleanup in lifecycle methods
- **Memory Leaks**: Using application context where appropriate

### Threading
- **Background Operations**: Database and network calls off main thread
- **LiveData**: Thread-safe reactive updates
- **ExecutorService**: Thread pool for database operations

### Performance
- **Image Caching**: Glide caches images automatically
- **ViewHolder Pattern**: RecyclerView optimization
- **Database Indexing**: Primary key on ID
- **Lazy Loading**: Data loaded only when needed

### Security
- **No Hardcoded Keys**: API is free, but pattern established
- **ProGuard Rules**: Code obfuscation for release
- **HTTPS Only**: Secure API calls

## 📝 Comments Philosophy

All comments are written in **super simple English** for easy understanding:

```java
// Good: "Load image using Glide (handles caching automatically)"
// Not: "Utilize Glide's advanced image loading paradigm"

// Good: "Check if recipe exists to prevent duplicates"
// Not: "Implement duplicate prevention mechanism"
```

## 🧪 Testing

### Manual Testing Checklist
- [ ] Search for recipes by name
- [ ] Filter by each category
- [ ] View recipe details
- [ ] Add recipe to favorites
- [ ] Edit notes and rating
- [ ] Delete from favorites
- [ ] Test offline mode (turn off WiFi)
- [ ] Test screen rotation (data persists)
- [ ] Test swipe to delete
- [ ] Test random recipe button

### Edge Cases Handled
- ✅ Empty search results
- ✅ No internet connection
- ✅ API errors
- ✅ Missing recipe data
- ✅ Screen rotation
- ✅ Duplicate favorites
- ✅ Empty favorites list

## 🚧 Known Limitations

1. **No Pagination**: Loads all search results at once
2. **No Image Caching Config**: Uses Glide defaults
3. **No Search History**: Could add recent searches
4. **No Meal Planning**: Bonus feature from spec (can be added)

## 🔮 Future Enhancements

- [ ] Meal planner with calendar integration
- [ ] Share recipe functionality
- [ ] Dark mode support
- [ ] Recipe search suggestions
- [ ] Category images/icons
- [ ] Ingredient shopping list
- [ ] Cooking timer integration
- [ ] Recipe difficulty rating

## 📄 License

This project is created for educational purposes. TheMealDB API is free to use for non-commercial purposes.

## 🙏 Credits

- **API**: [TheMealDB](https://www.themealdb.com/)
- **Architecture**: Android Architecture Components
- **Design**: Material Design 3 Guidelines

## 👨‍💻 Developer Notes

### Why This Architecture?

1. **MVVM**: Separates UI from business logic, makes testing easier
2. **Repository**: Single source of truth, easy to swap data sources
3. **LiveData**: Reactive updates, lifecycle-aware
4. **Room**: Type-safe, compile-time verified SQL

### Code Organization

- **Package by feature**: Not used (went with layer-based for simplicity)
- **Package by layer**: Used for clear separation of concerns
- **Naming**: Descriptive names (no abbreviations)

### Performance Considerations

- RecyclerView with ViewHolder pattern (efficient)
- Glide caching (reduces network calls)
- LiveData (updates only when active)
- Room (optimized SQLite wrapper)

---

**Built with ❤️ following Android best practices**
