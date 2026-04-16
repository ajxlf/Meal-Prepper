package com.example.mealprepper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SearchMealsByNameScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SearchMealsByNameContent() }
    }
}

@Composable
fun SearchMealsByNameContent() {
    val context = LocalContext.current

    var searchInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val meals = remember { mutableStateListOf<Meal>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search Meals By Name", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            label = { Text("Enter part of meal name") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (searchInput.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val results = searchMealsByNameFromApi(searchInput.trim())
                            withContext(Dispatchers.Main) {
                                meals.clear()
                                meals.addAll(results)
                                message = if (results.isEmpty()) {
                                    "No meals found"
                                } else {
                                    "${results.size} meals retrieved from web service"
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                message = "Error retrieving meals"
                            }
                        }
                    }
                } else {
                    message = "Please enter a search string"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (message.isNotEmpty()) {
            Text(message, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
        }

        meals.forEach { meal ->
            MealNameResultItem(meal)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

fun searchMealsByNameFromApi(searchText: String): List<Meal> {
    val encodedSearch = URLEncoder.encode(searchText, "UTF-8")
    val url =
        "https://www.themealdb.com/api/json/v1/1/search.php?s=$encodedSearch"

    val response = fetchUrlTextTask7(url)
    val json = JSONObject(response)
    val mealsArray = json.optJSONArray("meals") ?: return emptyList()

    val matchedMeals = mutableListOf<Meal>()
    val searchLower = searchText.trim().lowercase()

    for (i in 0 until mealsArray.length()) {
        val mealObject = mealsArray.getJSONObject(i)
        val meal = parseMealFromJsonTask7(mealObject)

        if (meal.strMeal.lowercase().contains(searchLower)) {
            matchedMeals.add(meal)
        }
    }

    return matchedMeals
}

fun fetchUrlTextTask7(urlString: String): String {
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

fun parseMealFromJsonTask7(jsonObject: JSONObject): Meal {
    return Meal(
        idMeal = jsonObject.optString("idMeal", ""),
        strMeal = jsonObject.optString("strMeal", ""),
        strDrinkAlternate = jsonObject.optStringOrNullTask7("strDrinkAlternate"),
        strCategory = jsonObject.optStringOrNullTask7("strCategory"),
        strArea = jsonObject.optStringOrNullTask7("strArea"),
        strInstructions = jsonObject.optStringOrNullTask7("strInstructions"),
        strMealThumb = jsonObject.optStringOrNullTask7("strMealThumb"),
        strTags = jsonObject.optStringOrNullTask7("strTags"),
        strYoutube = jsonObject.optStringOrNullTask7("strYoutube"),
        strIngredient1 = jsonObject.optStringOrNullTask7("strIngredient1"),
        strIngredient2 = jsonObject.optStringOrNullTask7("strIngredient2"),
        strIngredient3 = jsonObject.optStringOrNullTask7("strIngredient3"),
        strIngredient4 = jsonObject.optStringOrNullTask7("strIngredient4"),
        strIngredient5 = jsonObject.optStringOrNullTask7("strIngredient5"),
        strIngredient6 = jsonObject.optStringOrNullTask7("strIngredient6"),
        strIngredient7 = jsonObject.optStringOrNullTask7("strIngredient7"),
        strIngredient8 = jsonObject.optStringOrNullTask7("strIngredient8"),
        strIngredient9 = jsonObject.optStringOrNullTask7("strIngredient9"),
        strIngredient10 = jsonObject.optStringOrNullTask7("strIngredient10"),
        strIngredient11 = jsonObject.optStringOrNullTask7("strIngredient11"),
        strIngredient12 = jsonObject.optStringOrNullTask7("strIngredient12"),
        strIngredient13 = jsonObject.optStringOrNullTask7("strIngredient13"),
        strIngredient14 = jsonObject.optStringOrNullTask7("strIngredient14"),
        strIngredient15 = jsonObject.optStringOrNullTask7("strIngredient15"),
        strIngredient16 = jsonObject.optStringOrNullTask7("strIngredient16"),
        strIngredient17 = jsonObject.optStringOrNullTask7("strIngredient17"),
        strIngredient18 = jsonObject.optStringOrNullTask7("strIngredient18"),
        strIngredient19 = jsonObject.optStringOrNullTask7("strIngredient19"),
        strIngredient20 = jsonObject.optStringOrNullTask7("strIngredient20"),
        strMeasure1 = jsonObject.optStringOrNullTask7("strMeasure1"),
        strMeasure2 = jsonObject.optStringOrNullTask7("strMeasure2"),
        strMeasure3 = jsonObject.optStringOrNullTask7("strMeasure3"),
        strMeasure4 = jsonObject.optStringOrNullTask7("strMeasure4"),
        strMeasure5 = jsonObject.optStringOrNullTask7("strMeasure5"),
        strMeasure6 = jsonObject.optStringOrNullTask7("strMeasure6"),
        strMeasure7 = jsonObject.optStringOrNullTask7("strMeasure7"),
        strMeasure8 = jsonObject.optStringOrNullTask7("strMeasure8"),
        strMeasure9 = jsonObject.optStringOrNullTask7("strMeasure9"),
        strMeasure10 = jsonObject.optStringOrNullTask7("strMeasure10"),
        strMeasure11 = jsonObject.optStringOrNullTask7("strMeasure11"),
        strMeasure12 = jsonObject.optStringOrNullTask7("strMeasure12"),
        strMeasure13 = jsonObject.optStringOrNullTask7("strMeasure13"),
        strMeasure14 = jsonObject.optStringOrNullTask7("strMeasure14"),
        strMeasure15 = jsonObject.optStringOrNullTask7("strMeasure15"),
        strMeasure16 = jsonObject.optStringOrNullTask7("strMeasure16"),
        strMeasure17 = jsonObject.optStringOrNullTask7("strMeasure17"),
        strMeasure18 = jsonObject.optStringOrNullTask7("strMeasure18"),
        strMeasure19 = jsonObject.optStringOrNullTask7("strMeasure19"),
        strMeasure20 = jsonObject.optStringOrNullTask7("strMeasure20"),
        strSource = jsonObject.optStringOrNullTask7("strSource"),
        strImageSource = jsonObject.optStringOrNullTask7("strImageSource"),
        strCreativeCommonsConfirmed = jsonObject.optStringOrNullTask7("strCreativeCommonsConfirmed"),
        dateModified = jsonObject.optStringOrNullTask7("dateModified")
    )
}

fun JSONObject.optStringOrNullTask7(key: String): String? {
    if (!has(key) || isNull(key)) return null
    val value = optString(key, "").trim()
    return if (value.isEmpty()) null else value
}

@Composable
fun MealNameResultItem(meal: Meal) {
    val bitmapState = produceState<Bitmap?>(initialValue = null, key1 = meal.strMealThumb) {
        value = try {
            if (!meal.strMealThumb.isNullOrEmpty()) {
                BitmapFactory.decodeStream(URL(meal.strMealThumb).openStream())
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        bitmapState.value?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = meal.strMeal,
                modifier = Modifier.size(100.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxWidth()
        ) {
            Text("Meal: ${meal.strMeal}")
            Text("Category: ${meal.strCategory}")
            Text("Area: ${meal.strArea}")
            Text("Instructions: ${meal.strInstructions}")
        }
    }
}