package com.example.mealprepper

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

fun searchMealsByIngredient(ingredient: String): List<Meal> {
    val meals = mutableListOf<Meal>()
    val ingredientUrl =
        "https://www.themealdb.com/api/json/v1/1/filter.php?i=${ingredient.trim()}"

    val ingredientResponse = readUrl(ingredientUrl)
    val ingredientJson = JSONObject(ingredientResponse)
    val mealArray = ingredientJson.optJSONArray("meals") ?: return meals

    for (i in 0 until mealArray.length()) {
        val mealObject = mealArray.getJSONObject(i)
        val idMeal = mealObject.optString("idMeal")
        val detailUrl = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$idMeal"
        val detailResponse = readUrl(detailUrl)
        val detailJson = JSONObject(detailResponse)
        val detailMeals = detailJson.optJSONArray("meals") ?: JSONArray()

        if (detailMeals.length() > 0) {
            val mealDetails = detailMeals.getJSONObject(0)
            meals.add(parseMeal(mealDetails))
        }
    }

    return meals
}

fun readUrl(urlString: String): String {
    val url = URL(urlString)
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.connect()

    val response = connection.inputStream.bufferedReader().use { it.readText() }
    connection.disconnect()
    return response
}

fun parseMeal(json: JSONObject): Meal {
    return Meal(
        strMeal = json.optString("strMeal"),
        strDrinkAlternate = json.optString("strDrinkAlternate"),
        strCategory = json.optString("strCategory"),
        strArea = json.optString("strArea"),
        strInstructions = json.optString("strInstructions"),
        strMealThumb = json.optString("strMealThumb"),
        strTags = json.optString("strTags"),
        strYoutube = json.optString("strYoutube"),
        strIngredient1 = json.optString("strIngredient1"),
        strIngredient2 = json.optString("strIngredient2"),
        strIngredient3 = json.optString("strIngredient3"),
        strIngredient4 = json.optString("strIngredient4"),
        strIngredient5 = json.optString("strIngredient5"),
        strIngredient6 = json.optString("strIngredient6"),
        strIngredient7 = json.optString("strIngredient7"),
        strIngredient8 = json.optString("strIngredient8"),
        strIngredient9 = json.optString("strIngredient9"),
        strIngredient10 = json.optString("strIngredient10"),
        strIngredient11 = json.optString("strIngredient11"),
        strIngredient12 = json.optString("strIngredient12"),
        strIngredient13 = json.optString("strIngredient13"),
        strIngredient14 = json.optString("strIngredient14"),
        strIngredient15 = json.optString("strIngredient15"),
        strIngredient16 = json.optString("strIngredient16"),
        strIngredient17 = json.optString("strIngredient17"),
        strIngredient18 = json.optString("strIngredient18"),
        strIngredient19 = json.optString("strIngredient19"),
        strIngredient20 = json.optString("strIngredient20"),
        strMeasure1 = json.optString("strMeasure1"),
        strMeasure2 = json.optString("strMeasure2"),
        strMeasure3 = json.optString("strMeasure3"),
        strMeasure4 = json.optString("strMeasure4"),
        strMeasure5 = json.optString("strMeasure5"),
        strMeasure6 = json.optString("strMeasure6"),
        strMeasure7 = json.optString("strMeasure7"),
        strMeasure8 = json.optString("strMeasure8"),
        strMeasure9 = json.optString("strMeasure9"),
        strMeasure10 = json.optString("strMeasure10"),
        strMeasure11 = json.optString("strMeasure11"),
        strMeasure12 = json.optString("strMeasure12"),
        strMeasure13 = json.optString("strMeasure13"),
        strMeasure14 = json.optString("strMeasure14"),
        strMeasure15 = json.optString("strMeasure15"),
        strMeasure16 = json.optString("strMeasure16"),
        strMeasure17 = json.optString("strMeasure17"),
        strMeasure18 = json.optString("strMeasure18"),
        strMeasure19 = json.optString("strMeasure19"),
        strMeasure20 = json.optString("strMeasure20"),
        strSource = json.optString("strSource"),
        strImageSource = json.optString("strImageSource"),
        strCreativeCommonsConfirmed = json.optString("strCreativeCommonsConfirmed"),
        dateModified = json.optString("dateModified")
    )
}