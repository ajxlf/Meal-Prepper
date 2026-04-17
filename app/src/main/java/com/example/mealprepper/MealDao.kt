package com.example.mealprepper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Room DAO structure, including @Dao, @Query, @Insert, and suspend database methods, based on Lecture 9 - Working with Databases Part II.

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<Meal>)

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<Meal>

    @Query("""
        SELECT * FROM meals
        WHERE LOWER(strMeal) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient1, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient2, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient3, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient4, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient5, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient6, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient7, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient8, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient9, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient10, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient11, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient12, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient13, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient14, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient15, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient16, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient17, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient18, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient19, '')) LIKE '%' || LOWER(:searchText) || '%'
        OR LOWER(COALESCE(strIngredient20, '')) LIKE '%' || LOWER(:searchText) || '%'
    """)
    suspend fun searchMeals(searchText: String): List<Meal>
}