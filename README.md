# Meal Prep Assister

A meal preparation Android app built with **Kotlin** and **Jetpack Compose**.

The app combines a local **Room** database with the **TheMealDB** web service so users can save meals, search meals by ingredient, search saved meals locally, and retrieve meal names directly from the web.

---

## Screenshots

> _Will add screenshots here once testing has been completed._

- Main menu
- Main Menu (Rotated)
- Add Meals to DB screen
- Add Meals to DB screen (Rotated)
- Search by Ingredient screen
- Search by Ingredient screen (Rotated)
- Search for Meals (database) screen
- Search for Meals (database) screen (Rotated)
- Search Meals by Name (web) screen
- Search Meals by Name (web) screen (Rotated)

---

## Features

- **Local Room database** — stores full meal records on the device
- **Hardcoded starter meals** — supports inserting initial meal data into SQLite
- **Ingredient-based web search** — retrieves full meal details from TheMealDB API
- **Database save after retrieval** — stores web-fetched meals in the same local tables
- **Case-insensitive local search** — searches saved meals by partial name or ingredient match
- **Meal thumbnails** — displays meal images in search results
- **Direct web name search** — searches meals from TheMealDB by name substring
- **Portrait and landscape support** — designed to behave properly across rotation
- **Rotation-safe state** — preserves screen state and visible data during configuration changes

---

## Architecture

```text
app/
└── com.example.mealprepper/
    ├── MainActivity.kt                # Main menu and navigation
    ├── AddMealsToDBScreen.kt          # Inserts predefined meals into Room
    ├── SearchByIngredientScreen.kt    # Task 3/4 web search by ingredient + save to DB
    ├── SearchForMealsScreen.kt        # Task 5/6 local database search + image display
    ├── SearchMealsByNameScreen.kt     # Task 7 direct web search by meal name
    ├── Meal.kt                        # Room entity / meal data model
    ├── MealDao.kt                     # Database queries and inserts
    ├── MealDatabase.kt                # Room database setup
    └── MealStateUtils.kt              # Helpers for saving/restoring UI state on rotation
```

### Key design decisions

| Decision | Why |
|---|---|
| **Room** for persistence | Required by the coursework for storing meals locally |
| **Jetpack Compose** for UI | Coursework requires Compose and forbids View-based UI  |
| **Standard Android networking APIs** | Coursework forbids third-party libraries such as Retrofit, Volley, Glide, Coil, and OKHttp  |
| **`rememberSaveable` for screen state** | Helps preserve text, messages, and visible results across rotation [file:94] |
| **TheMealDB API integration** | Required for ingredient-based and name-based meal retrieval  |

---

## Built With

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room](https://developer.android.com/training/data-storage/room)
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [TheMealDB API](https://www.themealdb.com/api.php)
- Android Studio

---

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/MealPrepper.git
   ```

2. Open the project in **Android Studio**

3. Build and run on an emulator or Android device

4. Make sure the app has internet access, because Tasks 3, 4, and 7 rely on the **TheMealDB** web service 

### Requirements

- Android Studio
- Android SDK configured
- Internet connection for API-based features
- Emulator or device running a supported Android version

---

## Coursework Tasks Implemented

This project was built around the **5COSC023W Mobile Application Development** coursework specification. The brief requires an Android app that uses **Room** and **TheMealDB**, includes multiple meal search workflows, displays meal data and images, and preserves the same screen and data across device rotation. 

Implemented tasks include:

1. **Main menu screen** with navigation buttons
2. **Add Meals to DB** using Room and predefined meal data
3. **Search for Meals By Ingredient** using TheMealDB web service
4. **Save retrieved meals to the database**
5. **Search saved meals locally** using a case-insensitive substring match
6. **Display images** for meals returned from the local database search
7. **Search meals by name directly from the web service** rather than the local database
8. **Handle rotation correctly** so the app restores the same screen and visible data after configuration changes

---

## API Notes

The app uses **TheMealDB** API for:
- searching meals by ingredient,
- retrieving full meal details,
- searching meals by name from the web service.

Example endpoints used by the coursework:
- `filter.php?i=ingredient`
- `lookup.php?i=idMeal`
- `search.php?s=name`

---

## 📐 How Search Works

### Ingredient search
The user enters an ingredient, the app requests matching meal IDs from the API, then fetches full details for each returned meal before displaying them. This matches the coursework requirement to retrieve all meal details for meals containing the given ingredient. 

### Local database search
The user enters any partial string, and the app searches the local Room database for meals whose name or ingredient fields contain that substring in a case-insensitive way. The coursework explicitly requires case-insensitive substring matching rather than exact matching. 

### Web name search
The user enters part of a meal name, and the app retrieves meals directly from the web service rather than from the database. This is separate from the Room search and exists specifically for Task 7. 

---

## Academic Context

Built as coursework for **5COSC023W Mobile Application Development** at the **University of Westminster**. The specification requires use of Jetpack Compose, Room, TheMealDB, no third-party libraries for networking or image loading, and proper handling of device rotation. 

---

## Licence

This project is for educational and portfolio purposes.
