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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                        val results = db.mealDao().searchMeals(searchInput)
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
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Meal: ${meal.strMeal}")
        Text("Category: ${meal.strCategory}")
        Text("Area: ${meal.strArea}")
        Text("Instructions: ${meal.strInstructions}")
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
    }
}