package com.example.mealprepper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SearchByIngredientScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SearchByIngredientContent() }
    }
}

@Composable
fun SearchByIngredientContent() {
    val context = LocalContext.current
    val db = MealDatabase.getDatabase(context)

    var ingredientInput by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }
    var mealsJson by rememberSaveable { mutableStateOf("[]") }

    val meals = remember(mealsJson) {
        mutableStateListOf<Meal>().apply {
            addAll(mealsFromJsonString(mealsJson))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search for Meals By Ingredient", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = ingredientInput,
            onValueChange = { ingredientInput = it },
            label = { Text("Enter ingredient") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (ingredientInput.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val retrievedMeals = searchMealsByIngredientFromApi(ingredientInput.trim())
                            withContext(Dispatchers.Main) {
                                mealsJson = mealsToJsonString(retrievedMeals)
                                message = if (retrievedMeals.isEmpty()) {
                                    "No meals found"
                                } else {
                                    "${retrievedMeals.size} meals retrieved from web service"
                                }
                            }
                        } catch (_: Exception) {
                            withContext(Dispatchers.Main) {
                                message = "Error retrieving meals"
                            }
                        }
                    }
                } else {
                    message = "Please enter an ingredient"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retrieve Meals")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (meals.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.mealDao().insertMeals(meals.toList())
                        withContext(Dispatchers.Main) {
                            message = "Retrieved meals saved to database"
                        }
                    }
                } else {
                    message = "Retrieve meals first"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save meals to Database")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (message.isNotEmpty()) {
            Text(message, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
        }

        meals.forEach { meal ->
            MealItem(meal)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

fun searchMealsByIngredientFromApi(ingredient: String): List<Meal> {
    val encodedIngredient = URLEncoder.encode(ingredient, "UTF-8")
    val filterUrl =
        "https://www.themealdb.com/api/json/v1/1/filter.php?i=$encodedIngredient"

    val filterResponse = fetchUrlText(filterUrl)
    val filterJson = JSONObject(filterResponse)
    val mealsArray = filterJson.optJSONArray("meals") ?: return emptyList()

    val meals = mutableListOf<Meal>()

    for (i in 0 until mealsArray.length()) {
        val basicMeal = mealsArray.getJSONObject(i)
        val idMeal = basicMeal.getString("idMeal")

        val lookupUrl =
            "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$idMeal"

        val lookupResponse = fetchUrlText(lookupUrl)
        val lookupJson = JSONObject(lookupResponse)
        val detailArray = lookupJson.optJSONArray("meals") ?: continue
        val mealObject = detailArray.getJSONObject(0)

        meals.add(parseMealFromJson(mealObject))
    }

    return meals
}

fun fetchUrlText(urlString: String): String {
    val connection = (URL(urlString).openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        connectTimeout = 10000
        readTimeout = 10000
    }

    return try {
        connection.inputStream.bufferedReader().use { it.readText() }
    } finally {
        connection.disconnect()
    }
}

fun parseMealFromJson(jsonObject: JSONObject): Meal {
    return Meal(
        idMeal = jsonObject.optString("idMeal", ""),
        strMeal = jsonObject.optString("strMeal", ""),
        strDrinkAlternate = jsonObject.optStringOrNull("strDrinkAlternate"),
        strCategory = jsonObject.optStringOrNull("strCategory"),
        strArea = jsonObject.optStringOrNull("strArea"),
        strInstructions = jsonObject.optStringOrNull("strInstructions"),
        strMealThumb = jsonObject.optStringOrNull("strMealThumb"),
        strTags = jsonObject.optStringOrNull("strTags"),
        strYoutube = jsonObject.optStringOrNull("strYoutube"),
        strIngredient1 = jsonObject.optStringOrNull("strIngredient1"),
        strIngredient2 = jsonObject.optStringOrNull("strIngredient2"),
        strIngredient3 = jsonObject.optStringOrNull("strIngredient3"),
        strIngredient4 = jsonObject.optStringOrNull("strIngredient4"),
        strIngredient5 = jsonObject.optStringOrNull("strIngredient5"),
        strIngredient6 = jsonObject.optStringOrNull("strIngredient6"),
        strIngredient7 = jsonObject.optStringOrNull("strIngredient7"),
        strIngredient8 = jsonObject.optStringOrNull("strIngredient8"),
        strIngredient9 = jsonObject.optStringOrNull("strIngredient9"),
        strIngredient10 = jsonObject.optStringOrNull("strIngredient10"),
        strIngredient11 = jsonObject.optStringOrNull("strIngredient11"),
        strIngredient12 = jsonObject.optStringOrNull("strIngredient12"),
        strIngredient13 = jsonObject.optStringOrNull("strIngredient13"),
        strIngredient14 = jsonObject.optStringOrNull("strIngredient14"),
        strIngredient15 = jsonObject.optStringOrNull("strIngredient15"),
        strIngredient16 = jsonObject.optStringOrNull("strIngredient16"),
        strIngredient17 = jsonObject.optStringOrNull("strIngredient17"),
        strIngredient18 = jsonObject.optStringOrNull("strIngredient18"),
        strIngredient19 = jsonObject.optStringOrNull("strIngredient19"),
        strIngredient20 = jsonObject.optStringOrNull("strIngredient20"),
        strMeasure1 = jsonObject.optStringOrNull("strMeasure1"),
        strMeasure2 = jsonObject.optStringOrNull("strMeasure2"),
        strMeasure3 = jsonObject.optStringOrNull("strMeasure3"),
        strMeasure4 = jsonObject.optStringOrNull("strMeasure4"),
        strMeasure5 = jsonObject.optStringOrNull("strMeasure5"),
        strMeasure6 = jsonObject.optStringOrNull("strMeasure6"),
        strMeasure7 = jsonObject.optStringOrNull("strMeasure7"),
        strMeasure8 = jsonObject.optStringOrNull("strMeasure8"),
        strMeasure9 = jsonObject.optStringOrNull("strMeasure9"),
        strMeasure10 = jsonObject.optStringOrNull("strMeasure10"),
        strMeasure11 = jsonObject.optStringOrNull("strMeasure11"),
        strMeasure12 = jsonObject.optStringOrNull("strMeasure12"),
        strMeasure13 = jsonObject.optStringOrNull("strMeasure13"),
        strMeasure14 = jsonObject.optStringOrNull("strMeasure14"),
        strMeasure15 = jsonObject.optStringOrNull("strMeasure15"),
        strMeasure16 = jsonObject.optStringOrNull("strMeasure16"),
        strMeasure17 = jsonObject.optStringOrNull("strMeasure17"),
        strMeasure18 = jsonObject.optStringOrNull("strMeasure18"),
        strMeasure19 = jsonObject.optStringOrNull("strMeasure19"),
        strMeasure20 = jsonObject.optStringOrNull("strMeasure20"),
        strSource = jsonObject.optStringOrNull("strSource"),
        strImageSource = jsonObject.optStringOrNull("strImageSource"),
        strCreativeCommonsConfirmed = jsonObject.optStringOrNull("strCreativeCommonsConfirmed"),
        dateModified = jsonObject.optStringOrNull("dateModified")
    )
}

fun JSONObject.optStringOrNull(key: String): String? {
    if (!has(key) || isNull(key)) return null
    val value = optString(key, "").trim()
    return value.ifEmpty { null }
}

@Composable
fun MealItem(meal: Meal) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Meal: ${meal.strMeal}")
        Text("Drink Alternate: ${meal.strDrinkAlternate}")
        Text("Category: ${meal.strCategory}")
        Text("Area: ${meal.strArea}")
        Text("Instructions: ${meal.strInstructions}")
        Text("Tags: ${meal.strTags}")
        Text("Youtube: ${meal.strYoutube}")
        Text("Ingredient1: ${meal.strIngredient1}")
        Text("Ingredient2: ${meal.strIngredient2}")
        Text("Ingredient3: ${meal.strIngredient3}")
        Text("Ingredient4: ${meal.strIngredient4}")
        Text("Ingredient5: ${meal.strIngredient5}")
        Text("Ingredient6: ${meal.strIngredient6}")
        Text("Ingredient7: ${meal.strIngredient7}")
        Text("Ingredient8: ${meal.strIngredient8}")
        Text("Ingredient9: ${meal.strIngredient9}")
        Text("Ingredient10: ${meal.strIngredient10}")
        Text("Ingredient11: ${meal.strIngredient11}")
        Text("Ingredient12: ${meal.strIngredient12}")
        Text("Ingredient13: ${meal.strIngredient13}")
        Text("Ingredient14: ${meal.strIngredient14}")
        Text("Ingredient15: ${meal.strIngredient15}")
        Text("Ingredient16: ${meal.strIngredient16}")
        Text("Ingredient17: ${meal.strIngredient17}")
        Text("Ingredient18: ${meal.strIngredient18}")
        Text("Ingredient19: ${meal.strIngredient19}")
        Text("Ingredient20: ${meal.strIngredient20}")
        Text("Measure1: ${meal.strMeasure1}")
        Text("Measure2: ${meal.strMeasure2}")
        Text("Measure3: ${meal.strMeasure3}")
        Text("Measure4: ${meal.strMeasure4}")
        Text("Measure5: ${meal.strMeasure5}")
        Text("Measure6: ${meal.strMeasure6}")
        Text("Measure7: ${meal.strMeasure7}")
        Text("Measure8: ${meal.strMeasure8}")
        Text("Measure9: ${meal.strMeasure9}")
        Text("Measure10: ${meal.strMeasure10}")
        Text("Measure11: ${meal.strMeasure11}")
        Text("Measure12: ${meal.strMeasure12}")
        Text("Measure13: ${meal.strMeasure13}")
        Text("Measure14: ${meal.strMeasure14}")
        Text("Measure15: ${meal.strMeasure15}")
        Text("Measure16: ${meal.strMeasure16}")
        Text("Measure17: ${meal.strMeasure17}")
        Text("Measure18: ${meal.strMeasure18}")
        Text("Measure19: ${meal.strMeasure19}")
        Text("Measure20: ${meal.strMeasure20}")
    }
}