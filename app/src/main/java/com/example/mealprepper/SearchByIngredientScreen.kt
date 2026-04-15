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

    var ingredientInput by remember { mutableStateOf("") }
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
                        val retrievedMeals = searchMealsByIngredient(ingredientInput)
                        withContext(Dispatchers.Main) {
                            meals.clear()
                            meals.addAll(retrievedMeals)
                            message = if (retrievedMeals.isEmpty()) {
                                "No meals found"
                            } else {
                                "Meals retrieved"
                            }
                        }
                    }
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
                            message = "Meals saved to database"
                        }
                    }
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

@Composable
fun MealItem(meal: Meal) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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