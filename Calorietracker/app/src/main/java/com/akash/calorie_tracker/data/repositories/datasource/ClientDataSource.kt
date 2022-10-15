package com.akash.calorie_tracker.data.repositories.datasource

import android.net.Uri
import com.akash.calorie_tracker.domain.models.AllFoodsResponse
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.akash.calorie_tracker.domain.models.LoginResponse
import retrofit2.Response

interface ClientDataSource {
    suspend fun addFoodEntry(foodCreateRequest: FoodCreateRequest): Response<Food>

    suspend fun allFoods(fromDate:String,toDate:String):Response<AllFoodsResponse>
}