package com.example.mealprepper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class SearchForMealsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SearchForMealsContent() }
    }
}

@Composable
fun SearchForMealsContent() {
    val context = LocalContext.current
    val db = MealDatabase.getDatabase(context)

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
        Text("Search for Meals", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            label = { Text("Enter meal name or ingredient") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (searchInput.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val results = db.mealDao().searchMeals(searchInput.trim())
                        withContext(Dispatchers.Main) {
                            meals.clear()
                            meals.addAll(results)
                            message = if (results.isEmpty()) {
                                "No meals found"
                            } else {
                                "${results.size} meals found"
                            }
                        }
                    }
                } else {
                    message = "Please enter search text"
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
            MealSearchItem(meal)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun MealSearchItem(meal: Meal) {
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
            Text("Ingredient1: ${meal.strIngredient1}")
            Text("Ingredient2: ${meal.strIngredient2}")
            Text("Ingredient3: ${meal.strIngredient3}")
            Text("Ingredient4: ${meal.strIngredient4}")
            Text("Ingredient5: ${meal.strIngredient5}")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}