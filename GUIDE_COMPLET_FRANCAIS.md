# 📱 GUIDE COMPLET - Application Android de Gestion de Recettes

## Guide Détaillé pour Débutants - 60 Diapositives

---

# 🎯 PARTIE 1 : INTRODUCTION ET CONCEPTS

---

## Diapositive 1 : Qu'est-ce que cette application ?

**Recipe Manager** est une application Android mobile qui permet de :
- ✅ Chercher des recettes de cuisine en ligne
- ✅ Sauvegarder vos recettes préférées
- ✅ Ajouter des notes personnelles
- ✅ Donner des notes (étoiles) aux recettes
- ✅ Supprimer les recettes que vous n'aimez plus
- ✅ Utiliser l'application SANS internet pour voir vos favoris

**En langage simple** : C'est comme un carnet de recettes numérique qui peut chercher des recettes sur internet ET garder vos favoris sur votre téléphone.

---

## Diapositive 2 : Qu'est-ce que CRUD ?

**CRUD** est un acronyme qui signifie :

- **C** = **CREATE** (Créer) → Ajouter une nouvelle recette aux favoris
- **R** = **READ** (Lire) → Voir la liste de recettes et leurs détails
- **U** = **UPDATE** (Mettre à jour) → Modifier les notes et étoiles d'une recette
- **D** = **DELETE** (Supprimer) → Enlever une recette des favoris

**Pourquoi c'est important ?**
Presque toutes les applications utilisent CRUD ! Facebook (créer un post, lire les posts, modifier un post, supprimer un post), Instagram, WhatsApp, etc.

---

## Diapositive 3 : Pourquoi notre app est une application CRUD ?

Notre application fait EXACTEMENT les 4 opérations CRUD :

### ✅ CREATE (Créer)
Quand vous appuyez sur l'étoile ⭐, l'app **CRÉE** une nouvelle entrée dans la base de données locale de votre téléphone.

```java
// Code qui crée une nouvelle recette favorite
public void insertFavorite(FavoriteRecipe recipe) {
    recipeDao.insertRecipe(recipe);  // Ajoute dans la base de données
}
```

### ✅ READ (Lire)
Quand vous ouvrez l'onglet "Favoris", l'app **LIT** toutes les recettes sauvegardées.

```java
// Code qui lit toutes les recettes
public LiveData<List<FavoriteRecipe>> getAllFavorites() {
    return recipeDao.getAllFavorites();  // Récupère toutes les recettes
}
```

### ✅ UPDATE (Mettre à jour)
Quand vous modifiez vos notes ou changez le nombre d'étoiles, l'app **MET À JOUR** la recette.

```java
// Code qui modifie une recette existante
public void updateNotesAndRating(String recipeId, String notes, float rating) {
    recipeDao.updateNotesAndRating(recipeId, notes, rating);
}
```

### ✅ DELETE (Supprimer)
Quand vous glissez (swipe) pour supprimer, l'app **SUPPRIME** la recette.

```java
// Code qui supprime une recette
public void deleteFavorite(FavoriteRecipe recipe) {
    recipeDao.deleteRecipe(recipe);  // Enlève de la base de données
}
```

**Conclusion** : Notre app est 100% CRUD car elle fait toutes ces opérations sur les recettes favorites.

---

## Diapositive 4 : Technologies Utilisées (Tech Stack)

### 📱 Langage de Programmation
- **Java 8** - Le langage principal pour Android
- Pourquoi Java ? C'est stable, bien documenté, et parfait pour les débutants

### 🏗️ Architecture
- **MVVM** (Model-View-ViewModel) - Sépare l'interface utilisateur de la logique
- **Repository Pattern** - Un endroit central pour gérer les données

### 📦 Bibliothèques Principales

#### 1. **Room Database** (Base de données locale)
- **Version** : 2.6.1
- **Rôle** : Sauvegarder les recettes sur le téléphone (fonctionne sans internet)
- **Documentation** : https://developer.android.com/training/data-storage/room

#### 2. **Retrofit** (Appels API)
- **Version** : 2.9.0
- **Rôle** : Chercher des recettes sur internet via TheMealDB API
- **Documentation** : https://square.github.io/retrofit/

#### 3. **Glide** (Chargement d'images)
- **Version** : 4.16.0
- **Rôle** : Afficher les photos de recettes rapidement
- **Documentation** : https://github.com/bumptech/glide

#### 4. **Material Design 3** (Interface utilisateur)
- **Version** : 1.11.0
- **Rôle** : Composants d'interface modernes et jolis
- **Documentation** : https://m3.material.io/

#### 5. **LiveData & ViewModel** (Android Architecture Components)
- **Version** : 2.7.0
- **Rôle** : Gérer les données qui changent (réactivité)
- **Documentation** : https://developer.android.com/topic/libraries/architecture

---

## Diapositive 5 : API Utilisée - TheMealDB

### 🌐 Source de Données Externe

**Nom** : TheMealDB
**Site web** : https://www.themealdb.com/
**Documentation API** : https://www.themealdb.com/api.php

**Qu'est-ce que c'est ?**
Une API gratuite qui donne accès à plus de 1000 recettes du monde entier.

**URL de Base** : `https://www.themealdb.com/api/json/v1/1/`

### 📍 Endpoints Utilisés (Points d'accès)

#### 1. Chercher par nom
```
GET https://www.themealdb.com/api/json/v1/1/search.php?s=pasta
```
Retourne toutes les recettes qui contiennent "pasta"

#### 2. Filtrer par catégorie
```
GET https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
```
Retourne toutes les recettes de fruits de mer

#### 3. Obtenir les détails d'une recette
```
GET https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772
```
Retourne les détails complets de la recette #52772

#### 4. Recette aléatoire
```
GET https://www.themealdb.com/api/json/v1/1/random.php
```
Retourne une recette au hasard (pour le bouton "J'ai de la chance")

---

## Diapositive 6 : Exemple de Réponse API

Quand on demande une recette, l'API répond avec du JSON (format de données) :

```json
{
  "meals": [
    {
      "idMeal": "52772",
      "strMeal": "Teriyaki Chicken",
      "strCategory": "Chicken",
      "strArea": "Japanese",
      "strInstructions": "Mélangez la sauce soja avec le miel...",
      "strMealThumb": "https://www.themealdb.com/images/media/meals/teriyaki.jpg",
      "strIngredient1": "soy sauce",
      "strMeasure1": "3 tbs",
      "strIngredient2": "water",
      "strMeasure2": "3 tbs",
      "strYoutube": "https://www.youtube.com/watch?v=..."
    }
  ]
}
```

Notre app **transforme** ce JSON en objets Java faciles à utiliser !

---

# 🏗️ PARTIE 2 : ARCHITECTURE DE L'APPLICATION

---

## Diapositive 7 : Qu'est-ce que l'Architecture MVVM ?

**MVVM** signifie **Model-View-ViewModel**

Imaginez une maison avec 3 étages :

```
┌─────────────────────────────┐
│  VIEW (Vue)                 │  ← Ce que l'utilisateur VOIT
│  Fragments, Layouts XML     │    (Boutons, textes, images)
└─────────────────────────────┘
           ↕ (observe)
┌─────────────────────────────┐
│  VIEWMODEL                  │  ← Cerveau de l'application
│  Logique métier             │    (Décide quoi faire)
└─────────────────────────────┘
           ↕ (demande)
┌─────────────────────────────┐
│  MODEL (Modèle)             │  ← Données de l'application
│  Repository, Database, API  │    (D'où viennent les infos)
└─────────────────────────────┘
```

**Pourquoi séparer ?**
- ✅ Code plus propre et organisé
- ✅ Facile à tester chaque partie séparément
- ✅ Si vous changez l'interface, la logique reste intacte

---

## Diapositive 8 : Structure des Dossiers du Projet

```
app/src/main/java/com/recipemanager/
│
├── 📁 model/                  # Modèles de données
│   ├── Recipe.java            # Recette (depuis API)
│   ├── MealResponse.java      # Réponse de l'API
│   └── FavoriteRecipe.java    # Recette favorite (base de données)
│
├── 📁 api/                    # Communication avec internet
│   ├── MealApiService.java    # Définition des endpoints
│   └── RetrofitClient.java    # Configuration Retrofit
│
├── 📁 database/               # Base de données locale
│   ├── RecipeDao.java         # Opérations CRUD
│   ├── RecipeDatabase.java    # Configuration Room
│   └── FavoriteRecipe.java    # Table de la BD
│
├── 📁 repository/             # Point central des données
│   └── RecipeRepository.java  # Gère API + Database
│
├── 📁 viewmodel/              # Logique métier
│   ├── SearchViewModel.java      # Pour la recherche
│   ├── FavoritesViewModel.java   # Pour les favoris
│   └── RecipeDetailViewModel.java # Pour les détails
│
├── 📁 ui/                     # Interface utilisateur
│   ├── activities/
│   │   └── MainActivity.java  # Activité principale
│   ├── fragments/
│   │   ├── SearchFragment.java       # Onglet recherche
│   │   ├── FavoritesFragment.java    # Onglet favoris
│   │   └── RecipeDetailFragment.java # Page de détails
│   └── adapters/
│       ├── RecipeAdapter.java    # Adaptateur pour la recherche
│       └── FavoriteAdapter.java  # Adaptateur pour les favoris
│
└── 📁 utils/                  # Outils utilitaires
    └── RecipeConverter.java   # Convertit Recipe ↔ FavoriteRecipe
```

**Chaque dossier a un rôle précis** - Comme les pièces d'une maison !

---

## Diapositive 9 : Le Flux de Données (Comment ça circule)

### Exemple : L'utilisateur cherche "pizza"

```
1. USER (Utilisateur)
   Tape "pizza" et appuie sur Entrée
   ↓

2. VIEW (SearchFragment.java)
   Détecte l'action, dit au ViewModel : "Cherche pizza"
   ↓

3. VIEWMODEL (SearchViewModel.java)
   Reçoit la demande, dit au Repository : "Va chercher des pizzas"
   ↓

4. REPOSITORY (RecipeRepository.java)
   Décide : "Je vais chercher sur internet"
   Contacte l'API via Retrofit
   ↓

5. API (TheMealDB)
   Cherche "pizza" dans sa base de données
   Renvoie une liste de recettes en JSON
   ↓

6. REPOSITORY
   Reçoit le JSON, le transforme en objets Recipe
   Renvoie au ViewModel
   ↓

7. VIEWMODEL
   Stocke les recettes dans LiveData
   LiveData notifie automatiquement la View
   ↓

8. VIEW
   Reçoit les recettes, les affiche dans une grille
   L'utilisateur voit les résultats ! 🎉
```

**Tout ça se passe en quelques millisecondes !**

---

## Diapositive 10 : Qu'est-ce que LiveData ?

**LiveData** = Données qui "vivent" et se mettent à jour automatiquement

### Analogie Simple
Imaginez un tableau d'affichage dans une école :
- Quand le directeur change l'annonce, TOUS les élèves voient le changement
- Pas besoin de demander "y a-t-il du nouveau ?" toutes les 5 minutes

**Dans notre app** :
```java
// Le ViewModel a un LiveData
LiveData<List<Recipe>> recipes;

// Le Fragment "observe" ce LiveData
recipes.observe(this, newRecipes -> {
    // Dès que les recettes changent, ce code s'exécute automatiquement
    adapter.setRecipes(newRecipes);  // Affiche les nouvelles recettes
});
```

**Avantages** :
- ✅ Automatique - pas besoin de rafraîchir manuellement
- ✅ Intelligent - s'arrête quand l'app est en arrière-plan
- ✅ Évite les bugs - ne met pas à jour si la vue est détruite

---

# 📊 PARTIE 3 : LA BASE DE DONNÉES (Room)

---

## Diapositive 11 : Qu'est-ce que Room Database ?

**Room** = Bibliothèque Android pour sauvegarder des données localement

### Pourquoi avons-nous besoin d'une base de données ?
Sans base de données :
- ❌ Quand vous fermez l'app, vous perdez tout
- ❌ Pas d'accès hors ligne

Avec Room :
- ✅ Les favoris restent même après fermeture
- ✅ Fonctionne sans internet
- ✅ Rapide et sécurisé

### Room utilise SQLite
SQLite = Mini base de données qui vit dans votre téléphone
- Utilisé par WhatsApp, Instagram, etc.
- Gratuit et très rapide

---

## Diapositive 12 : Structure de la Table "favorite_recipes"

Notre base de données a **UNE SEULE TABLE** appelée `favorite_recipes` :

```sql
CREATE TABLE favorite_recipes (
    id               TEXT PRIMARY KEY,  -- ID unique (ex: "52772")
    name             TEXT,               -- Nom (ex: "Spaghetti Carbonara")
    image_url        TEXT,               -- Lien vers la photo
    category         TEXT,               -- Catégorie (ex: "Pasta")
    area             TEXT,               -- Pays (ex: "Italian")
    instructions     TEXT,               -- Instructions de cuisine
    ingredients      TEXT,               -- Liste des ingrédients
    video_url        TEXT,               -- Lien YouTube
    user_notes       TEXT,               -- Notes personnelles
    rating           REAL,               -- Étoiles (0.0 à 5.0)
    date_added       INTEGER             -- Quand ajouté (timestamp)
);
```

### Exemple de ligne dans la table

| id    | name      | category | user_notes              | rating |
|-------|-----------|----------|-------------------------|--------|
| 52772 | Teriyaki  | Chicken  | "Délicieux ! Refaire !" | 5.0    |
| 52940 | Carbonara | Pasta    | "Trop de bacon"         | 3.5    |

---

## Diapositive 13 : Le DAO (Data Access Object)

**DAO** = Interface qui définit comment interagir avec la base de données

### Fichier : `RecipeDao.java`

```java
@Dao
public interface RecipeDao {

    // CREATE - Ajouter une recette
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(FavoriteRecipe recipe);

    // READ - Lire toutes les recettes
    @Query("SELECT * FROM favorite_recipes ORDER BY date_added DESC")
    LiveData<List<FavoriteRecipe>> getAllFavorites();

    // UPDATE - Modifier une recette
    @Update
    void updateRecipe(FavoriteRecipe recipe);

    // DELETE - Supprimer une recette
    @Delete
    void deleteRecipe(FavoriteRecipe recipe);

    // READ - Vérifier si une recette est favorite
    @Query("SELECT * FROM favorite_recipes WHERE id = :recipeId")
    LiveData<FavoriteRecipe> getRecipeById(String recipeId);
}
```

**C'est ici qu'on fait le CRUD !**

---

## Diapositive 14 : Entity (L'Entité de Base de Données)

Une **Entity** = Une ligne de la table

### Fichier : `FavoriteRecipe.java`

```java
@Entity(tableName = "favorite_recipes")  // Nom de la table
public class FavoriteRecipe {

    @PrimaryKey  // Clé primaire (unique)
    @NonNull
    private String id;

    private String name;
    private String imageUrl;
    private String category;
    private String area;
    private String instructions;
    private String ingredients;
    private String videoUrl;

    // Champs ajoutés par l'utilisateur
    private String userNotes;  // Notes personnelles
    private float rating;      // Étoiles
    private long dateAdded;    // Date d'ajout

    // Constructeur, getters, setters...
}
```

**Chaque variable = Une colonne dans la table**

---

## Diapositive 15 : RecipeDatabase (Configuration de Room)

Le fichier qui configure toute la base de données :

```java
@Database(
    entities = {FavoriteRecipe.class},  // Quelles tables ?
    version = 1                          // Version de la BD
)
public abstract class RecipeDatabase extends RoomDatabase {

    // Instance unique (Singleton pattern)
    private static RecipeDatabase instance;

    // Accès au DAO
    public abstract RecipeDao recipeDao();

    // Créer ou récupérer la base de données
    public static synchronized RecipeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                RecipeDatabase.class,
                "recipe_database"  // Nom du fichier BD
            )
            .fallbackToDestructiveMigration()  // Si version change, recrée la BD
            .build();
        }
        return instance;
    }
}
```

**Singleton** = Il n'y a qu'UNE SEULE instance de la base de données dans toute l'app

---

# 🌐 PARTIE 4 : CONNEXION À L'API (Retrofit)

---

## Diapositive 16 : Qu'est-ce que Retrofit ?

**Retrofit** = Bibliothèque qui simplifie les appels API en Java

### Sans Retrofit (le cauchemar)
```java
// 50 lignes de code pour un simple appel HTTP
URL url = new URL("https://api.com/data");
HttpURLConnection connection = (HttpURLConnection) url.openConnection();
connection.setRequestMethod("GET");
InputStream inputStream = connection.getInputStream();
// ... 40 lignes de plus pour lire la réponse ...
```

### Avec Retrofit (facile !)
```java
// 1 ligne de code
Call<MealResponse> call = apiService.searchRecipes("pasta");
```

**Retrofit s'occupe de tout** : connexion, conversion JSON, erreurs, etc.

---

## Diapositive 17 : MealApiService (Définition des Endpoints)

### Fichier : `MealApiService.java`

```java
public interface MealApiService {

    // Chercher par nom
    @GET("search.php")
    Call<MealResponse> searchRecipes(@Query("s") String searchQuery);
    // Devient : https://themealdb.com/api/json/v1/1/search.php?s=pasta

    // Filtrer par catégorie
    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);
    // Devient : .../filter.php?c=Chicken

    // Obtenir une recette par ID
    @GET("lookup.php")
    Call<MealResponse> getRecipeById(@Query("i") String mealId);
    // Devient : .../lookup.php?i=52772

    // Recette aléatoire
    @GET("random.php")
    Call<MealResponse> getRandomRecipe();
    // Devient : .../random.php
}
```

**Chaque méthode = Un endpoint de l'API**

---

## Diapositive 18 : RetrofitClient (Configuration)

### Fichier : `RetrofitClient.java`

```java
public class RetrofitClient {

    // URL de base de l'API
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private static Retrofit retrofit;
    private static MealApiService apiService;

    // Créer le client Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Logger pour voir les requêtes (utile pour déboguer)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)  // Timeout de 30s
                .build();

            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())  // JSON → Java
                .build();
        }
        return retrofit;
    }

    // Obtenir le service API
    public static MealApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(MealApiService.class);
        }
        return apiService;
    }
}
```

**Singleton** - On crée Retrofit UNE SEULE FOIS pour toute l'app

---

## Diapositive 19 : Modèles de Données (Recipe et MealResponse)

### Fichier : `Recipe.java` (version simplifiée)

```java
public class Recipe implements Serializable {

    @SerializedName("idMeal")       // Nom dans le JSON
    private String id;               // Nom dans notre code

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String imageUrl;

    @SerializedName("strYoutube")
    private String videoUrl;

    // 20 ingrédients (strIngredient1 à strIngredient20)
    @SerializedName("strIngredient1") private String ingredient1;
    @SerializedName("strIngredient2") private String ingredient2;
    // ... jusqu'à 20

    // 20 mesures (strMeasure1 à strMeasure20)
    @SerializedName("strMeasure1") private String measure1;
    @SerializedName("strMeasure2") private String measure2;
    // ... jusqu'à 20

    // Méthode pour formater les ingrédients
    public String getFormattedIngredients() {
        // Combine ingrédients et mesures
        // Exemple : "• 200g - Pâtes\n• 100g - Bacon"
    }
}
```

**@SerializedName** permet de mapper le JSON aux variables Java

---

## Diapositive 20 : MealResponse (Wrapper de Réponse)

### Fichier : `MealResponse.java`

```java
public class MealResponse {

    @SerializedName("meals")
    private List<Recipe> meals;  // Liste de recettes

    // Vérifie si on a des résultats
    public boolean hasResults() {
        return meals != null && !meals.isEmpty();
    }

    // Obtenir toutes les recettes
    public List<Recipe> getMeals() {
        return meals != null ? meals : new ArrayList<>();
    }

    // Obtenir la première recette (pour lookup et random)
    public Recipe getFirstMeal() {
        return hasResults() ? meals.get(0) : null;
    }

    // Nombre de résultats
    public int getCount() {
        return meals != null ? meals.size() : 0;
    }
}
```

L'API retourne toujours `{"meals": [...]}` donc on crée ce wrapper.

---

# 💼 PARTIE 5 : REPOSITORY (Le Chef d'Orchestre)

---

## Diapositive 21 : Le Rôle du Repository

**Repository** = Point central qui gère TOUTES les données

### Pourquoi un Repository ?

Sans Repository :
- ❌ Fragments appellent directement l'API et la base de données
- ❌ Code dupliqué partout
- ❌ Difficile de changer la source de données

Avec Repository :
- ✅ Un seul endroit pour gérer les données
- ✅ Les Fragments ne savent pas d'où viennent les données (API ou BD)
- ✅ Facile à tester et maintenir

### Analogie
Le Repository est comme un **chef d'orchestre** :
- Les musiciens (API et BD) jouent
- Le chef décide qui joue quand
- Le public (UI) entend seulement le résultat final

---

## Diapositive 22 : RecipeRepository - Structure

### Fichier : `RecipeRepository.java`

```java
public class RecipeRepository {

    private final RecipeDao recipeDao;        // Accès à la base de données
    private final MealApiService apiService;  // Accès à l'API
    private final ExecutorService executorService;  // Pour tâches en arrière-plan

    public RecipeRepository(Application application) {
        // Initialiser la base de données
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDao = database.recipeDao();

        // Initialiser l'API
        apiService = RetrofitClient.getApiService();

        // Créer un pool de threads pour les opérations BD
        executorService = Executors.newFixedThreadPool(2);
    }

    // ... Méthodes CRUD ...
}
```

**ExecutorService** = Gestionnaire de threads pour exécuter des tâches en arrière-plan

---

## Diapositive 23 : Repository - Opérations API

```java
// CHERCHER des recettes par nom (depuis l'API)
public LiveData<List<Recipe>> searchRecipes(String query) {
    MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

    apiService.searchRecipes(query).enqueue(new Callback<MealResponse>() {
        @Override
        public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                // Succès - on a des recettes
                recipesLiveData.postValue(response.body().getMeals());
            } else {
                // Échec - pas de résultats
                recipesLiveData.postValue(null);
            }
        }

        @Override
        public void onFailure(Call<MealResponse> call, Throwable t) {
            // Erreur réseau
            recipesLiveData.postValue(null);
        }
    });

    return recipesLiveData;
}
```

**Asynchrone** - L'appel API ne bloque pas l'interface

---

## Diapositive 24 : Repository - Opérations Database (CRUD)

```java
// ===== CREATE - Ajouter aux favoris =====
public void insertFavorite(FavoriteRecipe recipe) {
    executorService.execute(() -> {
        recipeDao.insertRecipe(recipe);  // En arrière-plan
    });
}

// ===== READ - Lire tous les favoris =====
public LiveData<List<FavoriteRecipe>> getAllFavorites() {
    return recipeDao.getAllFavorites();  // LiveData automatique
}

// ===== UPDATE - Modifier notes et étoiles =====
public void updateNotesAndRating(String recipeId, String notes, float rating) {
    executorService.execute(() -> {
        recipeDao.updateNotesAndRating(recipeId, notes, rating);
    });
}

// ===== DELETE - Supprimer un favori =====
public void deleteFavorite(FavoriteRecipe recipe) {
    executorService.execute(() -> {
        recipeDao.deleteRecipe(recipe);
    });
}
```

**Pourquoi executorService ?**
Room interdit les opérations de base de données sur le thread principal (ça ralentirait l'interface).

---

# 🎨 PARTIE 6 : VIEWMODELS (La Logique Métier)

---

## Diapositive 25 : Qu'est-ce qu'un ViewModel ?

**ViewModel** = Classe qui stocke et gère les données pour l'interface utilisateur

### Problème sans ViewModel
Quand vous tournez votre téléphone :
- ❌ L'Activity/Fragment est détruit et recréé
- ❌ Toutes les données sont perdues
- ❌ Il faut recharger depuis l'API (gaspillage)

### Solution avec ViewModel
- ✅ ViewModel survit aux rotations d'écran
- ✅ Données conservées
- ✅ Pas besoin de recharger

### Analogie
ViewModel = **Mémoire à court terme** de l'app
- L'interface peut être fermée et rouverte
- Le ViewModel se souvient de tout

---

## Diapositive 26 : SearchViewModel

### Fichier : `SearchViewModel.java`

```java
public class SearchViewModel extends AndroidViewModel {

    private RecipeRepository repository;
    private MutableLiveData<List<Recipe>> recipes;

    public SearchViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
        recipes = new MutableLiveData<>();
    }

    // Chercher des recettes
    public LiveData<List<Recipe>> searchRecipes(String query) {
        return repository.searchRecipes(query);
    }

    // Filtrer par catégorie
    public LiveData<List<Recipe>> filterByCategory(String category) {
        return repository.filterByCategory(category);
    }

    // Recette aléatoire
    public LiveData<Recipe> getRandomRecipe() {
        return repository.getRandomRecipe();
    }
}
```

**Simple et propre** - Le ViewModel ne fait que transmettre au Repository

---

## Diapositive 27 : FavoritesViewModel

### Fichier : `FavoritesViewModel.java`

```java
public class FavoritesViewModel extends AndroidViewModel {

    private RecipeRepository repository;
    private LiveData<List<FavoriteRecipe>> allFavorites;
    private LiveData<Integer> favoritesCount;

    public FavoritesViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
        allFavorites = repository.getAllFavorites();  // Chargé une seule fois
        favoritesCount = repository.getFavoritesCount();
    }

    // READ - Obtenir tous les favoris
    public LiveData<List<FavoriteRecipe>> getAllFavorites() {
        return allFavorites;
    }

    // READ - Nombre de favoris
    public LiveData<Integer> getFavoritesCount() {
        return favoritesCount;
    }

    // DELETE - Supprimer un favori
    public void deleteFavorite(FavoriteRecipe recipe) {
        repository.deleteFavorite(recipe);
    }

    // UPDATE - Modifier un favori
    public void updateFavorite(FavoriteRecipe recipe) {
        repository.updateFavorite(recipe);
    }
}
```

---

## Diapositive 28 : RecipeDetailViewModel

### Fichier : `RecipeDetailViewModel.java`

```java
public class RecipeDetailViewModel extends AndroidViewModel {

    private RecipeRepository repository;

    public RecipeDetailViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    // CREATE - Ajouter aux favoris
    public void addToFavorites(Recipe recipe) {
        // Convertir Recipe (API) en FavoriteRecipe (BD)
        FavoriteRecipe favorite = RecipeConverter.toFavoriteRecipe(recipe);
        repository.insertFavorite(favorite);
    }

    // DELETE - Retirer des favoris
    public void removeFromFavorites(String recipeId) {
        // Créer un objet temporaire juste pour la suppression
        FavoriteRecipe favorite = new FavoriteRecipe();
        favorite.setId(recipeId);
        repository.deleteFavorite(favorite);
    }

    // READ - Vérifier si c'est un favori
    public LiveData<FavoriteRecipe> getFavoriteById(String recipeId) {
        return repository.getFavoriteById(recipeId);
    }
}
```

---

# 🖼️ PARTIE 7 : INTERFACE UTILISATEUR (UI)

---

## Diapositive 29 : MainActivity - Le Conteneur Principal

### Fichier : `MainActivity.java`

```java
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        // Fragment par défaut : Recherche
        if (savedInstanceState == null) {
            loadFragment(new SearchFragment());
        }

        // Gérer les clics sur la navigation
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            if (item.getItemId() == R.id.nav_search) {
                fragment = new SearchFragment();
            } else if (item.getItemId() == R.id.nav_favorites) {
                fragment = new FavoritesFragment();
            }

            return loadFragment(fragment);
        });
    }

    // Charger un fragment
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
            return true;
        }
        return false;
    }
}
```

---

## Diapositive 30 : Layout de MainActivity

### Fichier : `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Zone où les fragments s'affichent -->
    <FrameLayout
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@id/bottom_navigation" />

    <!-- Navigation en bas avec 2 onglets -->
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_navigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:menu="@menu/bottom_nav_menu"
        app:layout_constraintBottom_toBottomOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## Diapositive 31 : SearchFragment - Recherche de Recettes

### Fichier : `SearchFragment.java` (version simplifiée)

```java
public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    private EditText searchInput;
    private ChipGroup categoryChips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialiser le ViewModel
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new RecipeAdapter(recipe -> {
            // Quand on clique sur une recette
            openRecipeDetails(recipe);
        });
        recyclerView.setAdapter(adapter);

        // Chercher "chicken" par défaut au démarrage
        searchRecipes("chicken");

        return view;
    }

    // Chercher des recettes
    private void searchRecipes(String query) {
        viewModel.searchRecipes(query).observe(getViewLifecycleOwner(), recipes -> {
            if (recipes != null) {
                adapter.setRecipes(recipes);  // Afficher les résultats
            } else {
                Toast.makeText(getContext(), "Aucune recette trouvée", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

---

## Diapositive 32 : RecipeAdapter - Affichage en Grille

### Fichier : `RecipeAdapter.java`

```java
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();
    private OnRecipeClickListener listener;

    // Interface pour gérer les clics
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    // Mettre à jour les recettes
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();  // Rafraîchir l'affichage
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Créer une carte de recette
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        // Remplir la carte avec les données
        Recipe recipe = recipes.get(position);
        holder.bind(recipe, listener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    // ViewHolder - détient les vues d'une carte
    class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView recipeCategory;
        ImageView recipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeCategory = itemView.findViewById(R.id.recipeCategory);
            recipeImage = itemView.findViewById(R.id.recipeImage);
        }

        public void bind(Recipe recipe, OnRecipeClickListener listener) {
            recipeName.setText(recipe.getName());
            recipeCategory.setText(recipe.getCategory());

            // Charger l'image avec Glide
            Glide.with(itemView.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(recipeImage);

            // Gérer le clic
            itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
        }
    }
}
```

---

## Diapositive 33 : Layout d'une Carte de Recette

### Fichier : `item_recipe_card.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardElevation="4dp"
    app:cardCornerRadius="12dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <!-- Image de la recette -->
        <ImageView
            android:id="@+id/recipeImage"
            android:layout_width="match_parent"
            android:layout_height="150dp"
            android:scaleType="centerCrop" />

        <!-- Nom de la recette -->
        <TextView
            android:id="@+id/recipeName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="8dp"
            android:textStyle="bold"
            android:textSize="16sp" />

        <!-- Catégorie -->
        <TextView
            android:id="@+id/recipeCategory"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingHorizontal="8dp"
            android:paddingBottom="8dp"
            android:textSize="14sp"
            android:textColor="@color/gray" />

    </LinearLayout>

</com.google.android.material.card.MaterialCardView>
```

---

## Diapositive 34 : RecipeDetailFragment - Détails d'une Recette

### Fichier : `RecipeDetailFragment.java` (simplifié)

```java
public class RecipeDetailFragment extends Fragment {

    private Recipe recipe;
    private RecipeDetailViewModel viewModel;
    private boolean isFavorite = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Récupérer la recette passée en argument
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable("recipe");
        }

        viewModel = new ViewModelProvider(this).get(RecipeDetailViewModel.class);

        // Afficher les détails
        TextView nameText = view.findViewById(R.id.recipeName);
        TextView categoryText = view.findViewById(R.id.recipeCategory);
        TextView ingredientsText = view.findViewById(R.id.ingredientsText);
        TextView instructionsText = view.findViewById(R.id.instructionsText);
        ImageView recipeImage = view.findViewById(R.id.recipeImage);
        FloatingActionButton favButton = view.findViewById(R.id.favButton);

        nameText.setText(recipe.getName());
        categoryText.setText(recipe.getCategory() + " • " + recipe.getArea());
        ingredientsText.setText(recipe.getFormattedIngredients());
        instructionsText.setText(recipe.getInstructions());

        Glide.with(this).load(recipe.getImageUrl()).into(recipeImage);

        // Bouton favori
        favButton.setOnClickListener(v -> {
            if (isFavorite) {
                viewModel.removeFromFavorites(recipe.getId());
                Toast.makeText(getContext(), "Retiré des favoris", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.addToFavorites(recipe);
                Toast.makeText(getContext(), "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
            }
            isFavorite = !isFavorite;
            updateFavButton(favButton);
        });

        return view;
    }

    private void updateFavButton(FloatingActionButton button) {
        button.setImageResource(isFavorite ? R.drawable.ic_star_filled : R.drawable.ic_star);
    }
}
```

---

## Diapositive 35 : FavoritesFragment - Liste des Favoris

### Fichier : `FavoritesFragment.java` (simplifié)

```java
public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private FavoriteAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyText = view.findViewById(R.id.emptyText);

        // Setup adapter
        adapter = new FavoriteAdapter(
            favorite -> openRecipeDetails(favorite),  // Clic
            favorite -> showDeleteDialog(favorite)     // Swipe pour supprimer
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Swipe to delete
        ItemTouchHelper touchHelper = new ItemTouchHelper(new SwipeToDeleteCallback());
        touchHelper.attachToRecyclerView(recyclerView);

        // Observer les favoris
        viewModel.getAllFavorites().observe(getViewLifecycleOwner(), favorites -> {
            if (favorites != null && !favorites.isEmpty()) {
                adapter.setFavorites(favorites);
                recyclerView.setVisibility(View.VISIBLE);
                emptyText.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void showDeleteDialog(FavoriteRecipe favorite) {
        new AlertDialog.Builder(getContext())
            .setTitle("Supprimer cette recette ?")
            .setMessage("Voulez-vous vraiment supprimer " + favorite.getName() + " ?")
            .setPositiveButton("Supprimer", (dialog, which) -> {
                viewModel.deleteFavorite(favorite);
                Toast.makeText(getContext(), "Recette supprimée", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Annuler", null)
            .show();
    }
}
```

---

# 🚀 PARTIE 8 : DÉVELOPPEMENT ÉTAPE PAR ÉTAPE

---

## Diapositive 36 : Étape 1 - Configuration du Projet

### Créer un nouveau projet Android Studio

1. **Ouvrir Android Studio**
2. **Créer un nouveau projet** : "Empty Activity"
3. **Configuration** :
   - Nom : Recipe Manager
   - Package : com.recipemanager
   - Langage : Java
   - Minimum SDK : API 24 (Android 7.0)

4. **Ajouter les dépendances** dans `app/build.gradle` :

```gradle
dependencies {
    // Room Database
    implementation 'androidx.room:room-runtime:2.6.1'
    annotationProcessor 'androidx.room:room-compiler:2.6.1'

    // Retrofit & Gson
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'

    // Glide
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.16.0'

    // ViewModel & LiveData
    implementation 'androidx.lifecycle:lifecycle-viewmodel:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-livedata:2.7.0'

    // Material Design
    implementation 'com.google.android.material:material:1.11.0'
}
```

5. **Sync Gradle** - Cliquez sur "Sync Now"

---

## Diapositive 37 : Étape 2 - Ajouter les Permissions

### Fichier : `AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Permission pour accéder à internet -->
    <uses-permission android:name="android.permission.INTERNET" />

    <!-- Permission pour l'état du réseau -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="Recipe Manager"
        android:theme="@style/Theme.RecipeManager"
        android:usesCleartextTraffic="true">  <!-- Pour HTTP/HTTPS -->

        <activity
            android:name=".ui.activities.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>

</manifest>
```

**INTERNET** est essentiel pour appeler l'API !

---

## Diapositive 38 : Étape 3 - Créer le Modèle Recipe

### Créer le dossier : `java/com/recipemanager/model/`
### Créer le fichier : `Recipe.java`

```java
package com.recipemanager.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Recipe implements Serializable {

    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String imageUrl;

    @SerializedName("strYoutube")
    private String videoUrl;

    // 20 ingrédients (ajoutez-les tous)
    @SerializedName("strIngredient1") private String ingredient1;
    // ... jusqu'à ingredient20

    // 20 mesures
    @SerializedName("strMeasure1") private String measure1;
    // ... jusqu'à measure20

    // Constructeur vide
    public Recipe() {}

    // Getters et Setters (générez-les avec Alt+Insert)

    // Méthode utilitaire
    public String getFormattedIngredients() {
        StringBuilder sb = new StringBuilder();
        // Boucle pour combiner ingrédients et mesures
        return sb.toString();
    }
}
```

---

## Diapositive 39 : Étape 4 - Créer MealResponse

### Fichier : `model/MealResponse.java`

```java
package com.recipemanager.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    private List<Recipe> meals;

    public boolean hasResults() {
        return meals != null && !meals.isEmpty();
    }

    public List<Recipe> getMeals() {
        return meals != null ? meals : new ArrayList<>();
    }

    public Recipe getFirstMeal() {
        return hasResults() ? meals.get(0) : null;
    }

    public int getCount() {
        return meals != null ? meals.size() : 0;
    }
}
```

---

## Diapositive 40 : Étape 5 - Configuration Retrofit

### Créer le dossier : `java/com/recipemanager/api/`

### Fichier 1 : `MealApiService.java`

```java
package com.recipemanager.api;

import com.recipemanager.model.MealResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    @GET("search.php")
    Call<MealResponse> searchRecipes(@Query("s") String query);

    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);

    @GET("lookup.php")
    Call<MealResponse> getRecipeById(@Query("i") String id);

    @GET("random.php")
    Call<MealResponse> getRandomRecipe();
}
```

### Fichier 2 : `RetrofitClient.java`

```java
package com.recipemanager.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit;
    private static MealApiService apiService;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }

    public static MealApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(MealApiService.class);
        }
        return apiService;
    }
}
```

---

## Diapositive 41 : Étape 6 - Créer l'Entity FavoriteRecipe

### Créer le dossier : `java/com/recipemanager/database/`
### Fichier : `FavoriteRecipe.java`

```java
package com.recipemanager.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "favorite_recipes")
public class FavoriteRecipe {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;
    private String imageUrl;
    private String category;
    private String area;
    private String instructions;
    private String ingredients;
    private String videoUrl;
    private String userNotes;
    private float rating;
    private long dateAdded;

    // Constructeur vide
    public FavoriteRecipe() {}

    // Getters et Setters (Alt+Insert → Generate → Getters and Setters)
}
```

---

## Diapositive 42 : Étape 7 - Créer le DAO

### Fichier : `database/RecipeDao.java`

```java
package com.recipemanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(FavoriteRecipe recipe);

    @Update
    void updateRecipe(FavoriteRecipe recipe);

    @Delete
    void deleteRecipe(FavoriteRecipe recipe);

    @Query("SELECT * FROM favorite_recipes ORDER BY date_added DESC")
    LiveData<List<FavoriteRecipe>> getAllFavorites();

    @Query("SELECT * FROM favorite_recipes WHERE id = :recipeId")
    LiveData<FavoriteRecipe> getRecipeById(String recipeId);

    @Query("SELECT COUNT(*) FROM favorite_recipes")
    LiveData<Integer> getFavoritesCount();

    @Query("UPDATE favorite_recipes SET user_notes = :notes, rating = :rating WHERE id = :recipeId")
    void updateNotesAndRating(String recipeId, String notes, float rating);
}
```

---

## Diapositive 43 : Étape 8 - Créer la Database

### Fichier : `database/RecipeDatabase.java`

```java
package com.recipemanager.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteRecipe.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase instance;

    public abstract RecipeDao recipeDao();

    public static synchronized RecipeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                RecipeDatabase.class,
                "recipe_database"
            )
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }
}
```

**Singleton** - Une seule instance de la base de données

---

## Diapositive 44 : Étape 9 - Créer le Repository

### Créer le dossier : `java/com/recipemanager/repository/`
### Fichier : `RecipeRepository.java`

(Voir le code complet aux diapositives 22-24)

Le Repository contient :
- ✅ Méthodes API (searchRecipes, filterByCategory, etc.)
- ✅ Méthodes Database (insertFavorite, updateFavorite, deleteFavorite, etc.)
- ✅ ExecutorService pour les tâches en arrière-plan

---

## Diapositive 45 : Étape 10 - Créer les ViewModels

### Créer le dossier : `java/com/recipemanager/viewmodel/`

### Fichier 1 : `SearchViewModel.java`

```java
package com.recipemanager.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private RecipeRepository repository;

    public SearchViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    public LiveData<List<Recipe>> searchRecipes(String query) {
        return repository.searchRecipes(query);
    }

    public LiveData<List<Recipe>> filterByCategory(String category) {
        return repository.filterByCategory(category);
    }

    public LiveData<Recipe> getRandomRecipe() {
        return repository.getRandomRecipe();
    }
}
```

### Fichier 2 : `FavoritesViewModel.java`
(Voir diapositive 27)

### Fichier 3 : `RecipeDetailViewModel.java`
(Voir diapositive 28)

---

## Diapositive 46 : Étape 11 - Créer MainActivity

### Créer le dossier : `java/com/recipemanager/ui/activities/`
### Fichier : `MainActivity.java`

(Voir le code à la diapositive 29)

MainActivity fait :
- ✅ Affiche la BottomNavigationView
- ✅ Gère le changement d'onglets
- ✅ Charge les fragments

---

## Diapositive 47 : Étape 12 - Créer les Layouts XML

### 1. Layout de MainActivity : `res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@id/bottom_navigation" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_navigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:menu="@menu/bottom_nav_menu"
        app:layout_constraintBottom_toBottomOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 2. Menu pour la navigation : `res/menu/bottom_nav_menu.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/nav_search"
        android:icon="@drawable/ic_search"
        android:title="Recherche" />

    <item
        android:id="@+id/nav_favorites"
        android:icon="@drawable/ic_favorite"
        android:title="Favoris" />

</menu>
```

---

## Diapositive 48 : Étape 13 - Créer SearchFragment

### Créer le dossier : `java/com/recipemanager/ui/fragments/`
### Fichier : `SearchFragment.java`

(Voir le code à la diapositive 31)

### Layout : `res/layout/fragment_search.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- Barre de recherche -->
    <EditText
        android:id="@+id/searchInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Chercher une recette..."
        android:padding="16dp"
        android:imeOptions="actionSearch" />

    <!-- Chips de catégories -->
    <HorizontalScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.google.android.material.chip.ChipGroup
            android:id="@+id/categoryChips"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="8dp" />

    </HorizontalScrollView>

    <!-- Grille de recettes -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

</LinearLayout>
```

---

## Diapositive 49 : Étape 14 - Créer RecipeAdapter

### Créer le dossier : `java/com/recipemanager/ui/adapters/`
### Fichier : `RecipeAdapter.java`

(Voir le code à la diapositive 32)

### Layout d'une carte : `res/layout/item_recipe_card.xml`

(Voir le code à la diapositive 33)

---

## Diapositive 50 : Étape 15 - Créer FavoritesFragment

### Fichier : `FavoritesFragment.java`

(Voir diapositive 35)

### Layout : `res/layout/fragment_favorites.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Liste des favoris -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <!-- Message si vide -->
    <TextView
        android:id="@+id/emptyText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="Aucun favori enregistré"
        android:textSize="18sp"
        android:visibility="gone" />

</FrameLayout>
```

---

## Diapositive 51 : Étape 16 - Créer FavoriteAdapter

### Fichier : `FavoriteAdapter.java`

Similaire à RecipeAdapter, mais pour FavoriteRecipe

```java
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavoriteRecipe> favorites = new ArrayList<>();

    public void setFavorites(List<FavoriteRecipe> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_favorite_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavoriteRecipe favorite = favorites.get(position);
        holder.bind(favorite);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Views + bind()
    }
}
```

---

## Diapositive 52 : Étape 17 - Créer RecipeDetailFragment

### Fichier : `RecipeDetailFragment.java`

(Voir diapositive 34)

### Layout : `res/layout/fragment_recipe_detail.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.core.widget.NestedScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <!-- Grande image -->
        <ImageView
            android:id="@+id/recipeImage"
            android:layout_width="match_parent"
            android:layout_height="250dp"
            android:scaleType="centerCrop" />

        <!-- Nom -->
        <TextView
            android:id="@+id/recipeName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp"
            android:textSize="24sp"
            android:textStyle="bold" />

        <!-- Catégorie et pays -->
        <TextView
            android:id="@+id/recipeCategory"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingHorizontal="16dp"
            android:textSize="16sp" />

        <!-- Titre Ingrédients -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp"
            android:text="Ingrédients"
            android:textSize="20sp"
            android:textStyle="bold" />

        <!-- Liste ingrédients -->
        <TextView
            android:id="@+id/ingredientsText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingHorizontal="16dp"
            android:textSize="16sp" />

        <!-- Titre Instructions -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp"
            android:text="Instructions"
            android:textSize="20sp"
            android:textStyle="bold" />

        <!-- Instructions -->
        <TextView
            android:id="@+id/instructionsText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingHorizontal="16dp"
            android:paddingBottom="80dp"
            android:textSize="16sp" />

    </LinearLayout>

</androidx.core.widget.NestedScrollView>

<!-- Bouton favori flottant -->
<com.google.android.material.floatingactionbutton.FloatingActionButton
    android:id="@+id/favButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom|end"
    android:layout_margin="16dp"
    android:src="@drawable/ic_star" />
```

---

## Diapositive 53 : Étape 18 - Ajouter les Couleurs et Styles

### Fichier : `res/values/colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="orange_primary">#FF9800</color>
    <color name="orange_dark">#F57C00</color>
    <color name="white">#FFFFFF</color>
    <color name="gray">#757575</color>
    <color name="light_gray">#EEEEEE</color>
    <color name="black">#000000</color>
</resources>
```

### Fichier : `res/values/strings.xml`

```xml
<resources>
    <string name="app_name">Recipe Manager</string>
    <string name="search_hint">Chercher une recette...</string>
    <string name="no_favorites">Aucun favori enregistré</string>
    <string name="delete_confirm">Voulez-vous vraiment supprimer cette recette ?</string>
    <string name="added_to_favorites">Ajouté aux favoris</string>
    <string name="removed_from_favorites">Retiré des favoris</string>
</resources>
```

---

## Diapositive 54 : Étape 19 - Tester l'Application

### Checklist de Tests

1. **Recherche de recettes**
   - Ouvrez l'app
   - Elle devrait chercher "chicken" automatiquement
   - Tapez "pasta" → Appuyez sur Entrée
   - Vérifiez que les recettes de pâtes s'affichent

2. **Cliquer sur une recette**
   - Cliquez sur une carte
   - Vérifiez que la page de détails s'ouvre
   - Vérifiez que l'image, les ingrédients et instructions s'affichent

3. **Ajouter aux favoris**
   - Sur la page de détails, cliquez sur l'étoile
   - Message "Ajouté aux favoris" devrait apparaître
   - Allez à l'onglet "Favoris"
   - La recette devrait être là

4. **Modifier notes et étoiles**
   - Dans les favoris, cliquez sur une recette
   - Ajoutez des notes personnelles
   - Changez le nombre d'étoiles
   - Sauvegardez
   - Fermez et rouvrez l'app
   - Les modifications devraient être conservées

5. **Supprimer un favori**
   - Glissez (swipe) une carte de favori vers la gauche ou droite
   - Confirmation devrait apparaître
   - Confirmez la suppression
   - La recette disparaît

6. **Test hors ligne**
   - Désactivez le WiFi
   - Ouvrez l'onglet Favoris
   - Les recettes sauvegardées devraient toujours s'afficher
   - La recherche devrait afficher une erreur (normal)

7. **Test de rotation**
   - Tournez votre téléphone
   - Les données ne devraient PAS se perdre (merci ViewModel !)

---

# 📚 PARTIE 9 : CONCEPTS IMPORTANTS EXPLIQUÉS

---

## Diapositive 55 : Pourquoi utiliser des Threads en Arrière-plan ?

### Le Problème

Android a un **Thread Principal** (Main Thread / UI Thread) :
- C'est lui qui affiche l'interface
- Il doit être TOUJOURS rapide et réactif
- Si vous faites des opérations longues dessus, l'app freeze (gèle)

### Opérations Longues
- ❌ Appels API (peut prendre 1-5 secondes)
- ❌ Lecture/écriture dans la base de données
- ❌ Chargement d'images
- ❌ Calculs complexes

### Solution : Background Threads

```java
// ❌ MAUVAIS - Sur le thread principal (app gèle)
public void saveRecipe(FavoriteRecipe recipe) {
    database.insert(recipe);  // BLOQUE l'interface !
}

// ✅ BON - Sur un thread en arrière-plan
public void saveRecipe(FavoriteRecipe recipe) {
    executorService.execute(() -> {
        database.insert(recipe);  // Exécuté en arrière-plan
    });
}
```

**Retrofit** fait les appels API en arrière-plan automatiquement.
**Room** nécessite qu'on utilise un ExecutorService manuellement.

---

## Diapositive 56 : Comment fonctionne Glide (Chargement d'Images) ?

**Glide** = Bibliothèque ultra-optimisée pour charger des images

### Sans Glide (cauchemar)
```java
// 30+ lignes de code pour télécharger et afficher une image
new Thread(() -> {
    try {
        URL url = new URL(imageUrl);
        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        runOnUiThread(() -> imageView.setImageBitmap(bitmap));
    } catch (Exception e) {
        e.printStackTrace();
    }
}).start();
```

### Avec Glide (1 ligne)
```java
Glide.with(context)
    .load(imageUrl)
    .placeholder(R.drawable.placeholder)  // Image en attendant
    .error(R.drawable.error)              // Si erreur
    .into(imageView);
```

### Ce que Glide fait automatiquement
- ✅ Télécharge l'image en arrière-plan
- ✅ Met en cache (stocke localement pour réutiliser)
- ✅ Redimensionne pour optimiser la mémoire
- ✅ Gère les erreurs
- ✅ Annule si la vue est détruite
- ✅ Affiche un placeholder pendant le chargement

**C'est magique !**

---

## Diapositive 57 : Conversion Recipe ↔ FavoriteRecipe

### Le Problème
- **Recipe** = Données de l'API (40+ champs)
- **FavoriteRecipe** = Données de la BD (11 champs)

Quand on ajoute aux favoris, on doit **convertir** Recipe → FavoriteRecipe

### Fichier Utilitaire : `utils/RecipeConverter.java`

```java
package com.recipemanager.utils;

import com.recipemanager.database.FavoriteRecipe;
import com.recipemanager.model.Recipe;

public class RecipeConverter {

    // Convertir Recipe (API) vers FavoriteRecipe (BD)
    public static FavoriteRecipe toFavoriteRecipe(Recipe recipe) {
        FavoriteRecipe favorite = new FavoriteRecipe();

        favorite.setId(recipe.getId());
        favorite.setName(recipe.getName());
        favorite.setImageUrl(recipe.getImageUrl());
        favorite.setCategory(recipe.getCategory());
        favorite.setArea(recipe.getArea());
        favorite.setInstructions(recipe.getInstructions());
        favorite.setVideoUrl(recipe.getVideoUrl());

        // Formater les ingrédients en une seule chaîne
        favorite.setIngredients(recipe.getFormattedIngredients());

        // Champs par défaut
        favorite.setUserNotes("");
        favorite.setRating(0.0f);
        favorite.setDateAdded(System.currentTimeMillis());  // Timestamp actuel

        return favorite;
    }

    // Convertir FavoriteRecipe (BD) vers Recipe (pour affichage)
    public static Recipe toRecipe(FavoriteRecipe favorite) {
        Recipe recipe = new Recipe();

        recipe.setId(favorite.getId());
        recipe.setName(favorite.getName());
        recipe.setImageUrl(favorite.getImageUrl());
        recipe.setCategory(favorite.getCategory());
        recipe.setArea(favorite.getArea());
        recipe.setInstructions(favorite.getInstructions());
        recipe.setVideoUrl(favorite.getVideoUrl());

        // Les ingrédients sont déjà formatés

        return recipe;
    }
}
```

**Utilisation** :
```java
// Ajouter aux favoris
FavoriteRecipe favorite = RecipeConverter.toFavoriteRecipe(recipe);
repository.insertFavorite(favorite);
```

---

## Diapositive 58 : Gestion des Erreurs

### Erreurs Possibles

1. **Pas d'internet**
   - L'appel API échoue
   - Solution : Afficher un Toast "Pas de connexion internet"

2. **API ne répond pas**
   - Timeout après 30 secondes
   - Solution : Afficher "Erreur de serveur"

3. **Aucun résultat**
   - L'API retourne `{"meals": null}`
   - Solution : Afficher "Aucune recette trouvée"

4. **Erreur de base de données**
   - Peu probable avec Room, mais possible
   - Solution : try-catch et log

### Exemple dans Repository

```java
public LiveData<List<Recipe>> searchRecipes(String query) {
    MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

    apiService.searchRecipes(query).enqueue(new Callback<MealResponse>() {
        @Override
        public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                if (response.body().hasResults()) {
                    // ✅ Succès
                    recipesLiveData.postValue(response.body().getMeals());
                } else {
                    // ⚠️ Pas de résultats
                    recipesLiveData.postValue(null);
                    Log.d(TAG, "No results for: " + query);
                }
            } else {
                // ❌ Erreur API
                recipesLiveData.postValue(null);
                Log.e(TAG, "API Error: " + response.code());
            }
        }

        @Override
        public void onFailure(Call<MealResponse> call, Throwable t) {
            // ❌ Erreur réseau
            recipesLiveData.postValue(null);
            Log.e(TAG, "Network Error: " + t.getMessage());
        }
    });

    return recipesLiveData;
}
```

### Dans le Fragment

```java
viewModel.searchRecipes(query).observe(getViewLifecycleOwner(), recipes -> {
    if (recipes != null && !recipes.isEmpty()) {
        // ✅ Afficher les recettes
        adapter.setRecipes(recipes);
        emptyText.setVisibility(View.GONE);
    } else {
        // ⚠️ Afficher message vide
        adapter.setRecipes(new ArrayList<>());
        emptyText.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Aucune recette trouvée", Toast.LENGTH_SHORT).show();
    }
});
```

---

# 🎓 PARTIE 10 : RÉSUMÉ ET SOURCES

---

## Diapositive 59 : Récapitulatif Complet

### ✅ Ce que vous avez appris

1. **Concept CRUD**
   - CREATE, READ, UPDATE, DELETE
   - Application dans un contexte réel

2. **Architecture MVVM**
   - Séparation Model-View-ViewModel
   - Pourquoi c'est important

3. **Room Database**
   - Créer une base de données locale
   - DAO, Entity, Database
   - Opérations CRUD avec SQL

4. **Retrofit & API**
   - Appels HTTP avec Retrofit
   - Parsing JSON avec Gson
   - Gestion asynchrone

5. **LiveData & ViewModel**
   - Programmation réactive
   - Survie aux rotations d'écran

6. **RecyclerView & Adapters**
   - Affichage de listes efficaces
   - ViewHolder pattern

7. **Glide**
   - Chargement et cache d'images

8. **Fragments & Navigation**
   - BottomNavigationView
   - Gestion de fragments

9. **Threads**
   - Thread principal vs arrière-plan
   - ExecutorService

10. **Material Design**
    - Composants modernes
    - Cards, FAB, Chips

### 💪 Compétences Acquises

- ✅ Créer une app Android complète de A à Z
- ✅ Intégrer une API REST
- ✅ Gérer une base de données locale
- ✅ Implémenter une architecture propre
- ✅ Gérer l'état de l'application
- ✅ Optimiser les performances
- ✅ Gérer les erreurs
- ✅ Créer une UI moderne

---

## Diapositive 60 : Sources et Références

### 📚 Documentation Officielle

1. **Android Developers**
   - https://developer.android.com/
   - Guide complet sur Android

2. **Room Database**
   - https://developer.android.com/training/data-storage/room
   - Documentation officielle Room

3. **Retrofit**
   - https://square.github.io/retrofit/
   - Documentation et exemples

4. **Glide**
   - https://github.com/bumptech/glide
   - Guide d'utilisation Glide

5. **Material Design 3**
   - https://m3.material.io/
   - Guidelines de design

6. **Architecture Components**
   - https://developer.android.com/topic/libraries/architecture
   - ViewModel, LiveData, etc.

### 🌐 API Utilisée

**TheMealDB**
- Site web : https://www.themealdb.com/
- Documentation API : https://www.themealdb.com/api.php
- Gratuit pour usage non-commercial
- Plus de 1000 recettes disponibles

### 📖 Tutoriels Recommandés

1. **Codelabs Android**
   - https://codelabs.developers.google.com/android-kotlin-fundamentals/
   - Tutoriels interactifs Google

2. **Vogella Tutorials**
   - https://www.vogella.com/tutorials/android.html
   - Tutoriels détaillés

### 🎥 Vidéos YouTube (Recommandées)

- Chaîne "Android Developers" (Google)
- "Coding in Flow" (Tutoriels Android)
- "Philipp Lackner" (Android avancé)

### 📦 Bibliothèques Gradle

Toutes les bibliothèques utilisées sont hébergées sur Maven Central :
- https://mvnrepository.com/

### 🛠️ Outils de Développement

1. **Android Studio**
   - https://developer.android.com/studio
   - IDE officiel pour Android

2. **Git & GitHub**
   - Version control et partage de code

3. **Postman**
   - https://www.postman.com/
   - Tester les API

---

## 🎉 CONCLUSION

### Félicitations !

Vous avez maintenant une compréhension complète de :
- ✅ Comment fonctionne une application CRUD
- ✅ Comment développer une app Android moderne
- ✅ Comment intégrer une API
- ✅ Comment gérer une base de données locale
- ✅ Les bonnes pratiques d'architecture

### Prochaines Étapes

1. **Améliorations possibles** :
   - Mode sombre
   - Partage de recettes
   - Liste de courses
   - Planificateur de repas
   - Recherche avancée

2. **Nouvelles compétences à apprendre** :
   - Kotlin (langage moderne pour Android)
   - Jetpack Compose (UI moderne)
   - Firebase (backend cloud)
   - Tests unitaires

### 💼 Pour votre Présentation

Cette application démontre :
- Maîtrise des fondamentaux Android
- Compréhension de l'architecture MVVM
- Capacité à intégrer des API
- Gestion de base de données
- Code propre et bien organisé
- Expérience utilisateur soignée

**Bonne chance pour votre présentation ! 🚀**

---

**Guide créé avec ❤️ pour les développeurs débutants**
**Toutes les explications en français simple et accessible**
**60 diapositives complètes pour tout comprendre**
