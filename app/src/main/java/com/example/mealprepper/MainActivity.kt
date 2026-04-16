package com.example.mealprepper

import android.content.Intent
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DisplayMenu() }
    }
}

@Composable
fun DisplayMenu() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Meal Preparation App",
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, AddMealsToDBScreen::class.java))
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Add Meals to DB")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, SearchByIngredientScreen::class.java))
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Search for Meals By Ingredient")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, SearchForMealsScreen::class.java))
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Search for Meals (Database)")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, SearchMealsByNameScreen::class.java))
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Search Meals By Name (Web)")
        }
    }
}