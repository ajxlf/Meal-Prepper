package com.example.mealprepper

import org.json.JSONArray
import org.json.JSONObject

fun mealsToJsonString(meals: List<Meal>): String {
    val jsonArray = JSONArray()

    meals.forEach { meal ->
        val obj = JSONObject()
        obj.put("idMeal", meal.idMeal)
        obj.put("strMeal", meal.strMeal)
        obj.put("strDrinkAlternate", meal.strDrinkAlternate)
        obj.put("strCategory", meal.strCategory)
        obj.put("strArea", meal.strArea)
        obj.put("strInstructions", meal.strInstructions)
        obj.put("strMealThumb", meal.strMealThumb)
        obj.put("strTags", meal.strTags)
        obj.put("strYoutube", meal.strYoutube)
        obj.put("strIngredient1", meal.strIngredient1)
        obj.put("strIngredient2", meal.strIngredient2)
        obj.put("strIngredient3", meal.strIngredient3)
        obj.put("strIngredient4", meal.strIngredient4)
        obj.put("strIngredient5", meal.strIngredient5)
        obj.put("strIngredient6", meal.strIngredient6)
        obj.put("strIngredient7", meal.strIngredient7)
        obj.put("strIngredient8", meal.strIngredient8)
        obj.put("strIngredient9", meal.strIngredient9)
        obj.put("strIngredient10", meal.strIngredient10)
        obj.put("strIngredient11", meal.strIngredient11)
        obj.put("strIngredient12", meal.strIngredient12)
        obj.put("strIngredient13", meal.strIngredient13)
        obj.put("strIngredient14", meal.strIngredient14)
        obj.put("strIngredient15", meal.strIngredient15)
        obj.put("strIngredient16", meal.strIngredient16)
        obj.put("strIngredient17", meal.strIngredient17)
        obj.put("strIngredient18", meal.strIngredient18)
        obj.put("strIngredient19", meal.strIngredient19)
        obj.put("strIngredient20", meal.strIngredient20)
        obj.put("strMeasure1", meal.strMeasure1)
        obj.put("strMeasure2", meal.strMeasure2)
        obj.put("strMeasure3", meal.strMeasure3)
        obj.put("strMeasure4", meal.strMeasure4)
        obj.put("strMeasure5", meal.strMeasure5)
        obj.put("strMeasure6", meal.strMeasure6)
        obj.put("strMeasure7", meal.strMeasure7)
        obj.put("strMeasure8", meal.strMeasure8)
        obj.put("strMeasure9", meal.strMeasure9)
        obj.put("strMeasure10", meal.strMeasure10)
        obj.put("strMeasure11", meal.strMeasure11)
        obj.put("strMeasure12", meal.strMeasure12)
        obj.put("strMeasure13", meal.strMeasure13)
        obj.put("strMeasure14", meal.strMeasure14)
        obj.put("strMeasure15", meal.strMeasure15)
        obj.put("strMeasure16", meal.strMeasure16)
        obj.put("strMeasure17", meal.strMeasure17)
        obj.put("strMeasure18", meal.strMeasure18)
        obj.put("strMeasure19", meal.strMeasure19)
        obj.put("strMeasure20", meal.strMeasure20)
        obj.put("strSource", meal.strSource)
        obj.put("strImageSource", meal.strImageSource)
        obj.put("strCreativeCommonsConfirmed", meal.strCreativeCommonsConfirmed)
        obj.put("dateModified", meal.dateModified)

        jsonArray.put(obj)
    }

    return jsonArray.toString()
}

fun mealsFromJsonString(jsonString: String): List<Meal> {
    val meals = mutableListOf<Meal>()
    val jsonArray = JSONArray(jsonString)

    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)

        meals.add(
            Meal(
                idMeal = obj.optString("idMeal", ""),
                strMeal = obj.optString("strMeal", ""),
                strDrinkAlternate = obj.optStringOrNullHelper("strDrinkAlternate"),
                strCategory = obj.optStringOrNullHelper("strCategory"),
                strArea = obj.optStringOrNullHelper("strArea"),
                strInstructions = obj.optStringOrNullHelper("strInstructions"),
                strMealThumb = obj.optStringOrNullHelper("strMealThumb"),
                strTags = obj.optStringOrNullHelper("strTags"),
                strYoutube = obj.optStringOrNullHelper("strYoutube"),
                strIngredient1 = obj.optStringOrNullHelper("strIngredient1"),
                strIngredient2 = obj.optStringOrNullHelper("strIngredient2"),
                strIngredient3 = obj.optStringOrNullHelper("strIngredient3"),
                strIngredient4 = obj.optStringOrNullHelper("strIngredient4"),
                strIngredient5 = obj.optStringOrNullHelper("strIngredient5"),
                strIngredient6 = obj.optStringOrNullHelper("strIngredient6"),
                strIngredient7 = obj.optStringOrNullHelper("strIngredient7"),
                strIngredient8 = obj.optStringOrNullHelper("strIngredient8"),
                strIngredient9 = obj.optStringOrNullHelper("strIngredient9"),
                strIngredient10 = obj.optStringOrNullHelper("strIngredient10"),
                strIngredient11 = obj.optStringOrNullHelper("strIngredient11"),
                strIngredient12 = obj.optStringOrNullHelper("strIngredient12"),
                strIngredient13 = obj.optStringOrNullHelper("strIngredient13"),
                strIngredient14 = obj.optStringOrNullHelper("strIngredient14"),
                strIngredient15 = obj.optStringOrNullHelper("strIngredient15"),
                strIngredient16 = obj.optStringOrNullHelper("strIngredient16"),
                strIngredient17 = obj.optStringOrNullHelper("strIngredient17"),
                strIngredient18 = obj.optStringOrNullHelper("strIngredient18"),
                strIngredient19 = obj.optStringOrNullHelper("strIngredient19"),
                strIngredient20 = obj.optStringOrNullHelper("strIngredient20"),
                strMeasure1 = obj.optStringOrNullHelper("strMeasure1"),
                strMeasure2 = obj.optStringOrNullHelper("strMeasure2"),
                strMeasure3 = obj.optStringOrNullHelper("strMeasure3"),
                strMeasure4 = obj.optStringOrNullHelper("strMeasure4"),
                strMeasure5 = obj.optStringOrNullHelper("strMeasure5"),
                strMeasure6 = obj.optStringOrNullHelper("strMeasure6"),
                strMeasure7 = obj.optStringOrNullHelper("strMeasure7"),
                strMeasure8 = obj.optStringOrNullHelper("strMeasure8"),
                strMeasure9 = obj.optStringOrNullHelper("strMeasure9"),
                strMeasure10 = obj.optStringOrNullHelper("strMeasure10"),
                strMeasure11 = obj.optStringOrNullHelper("strMeasure11"),
                strMeasure12 = obj.optStringOrNullHelper("strMeasure12"),
                strMeasure13 = obj.optStringOrNullHelper("strMeasure13"),
                strMeasure14 = obj.optStringOrNullHelper("strMeasure14"),
                strMeasure15 = obj.optStringOrNullHelper("strMeasure15"),
                strMeasure16 = obj.optStringOrNullHelper("strMeasure16"),
                strMeasure17 = obj.optStringOrNullHelper("strMeasure17"),
                strMeasure18 = obj.optStringOrNullHelper("strMeasure18"),
                strMeasure19 = obj.optStringOrNullHelper("strMeasure19"),
                strMeasure20 = obj.optStringOrNullHelper("strMeasure20"),
                strSource = obj.optStringOrNullHelper("strSource"),
                strImageSource = obj.optStringOrNullHelper("strImageSource"),
                strCreativeCommonsConfirmed = obj.optStringOrNullHelper("strCreativeCommonsConfirmed"),
                dateModified = obj.optStringOrNullHelper("dateModified")
            )
        )
    }

    return meals
}

fun JSONObject.optStringOrNullHelper(key: String): String? {
    if (!has(key) || isNull(key)) return null
    val value = optString(key, "").trim()
    return value.ifEmpty { null }
}