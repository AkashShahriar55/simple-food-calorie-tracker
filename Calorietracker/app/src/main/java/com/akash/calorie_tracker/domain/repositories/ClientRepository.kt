package com.akash.calorie_tracker.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akash.calorie_tracker.domain.models.DailyCalorieLimit
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.akash.calorie_tracker.domain.models.ResponseState
import retrofit2.Response

interface ClientRepository {
    suspend fun addFoodEntry(
        foodCreateRequest: FoodCreateRequest
    ): Response<Food>


    suspend fun fetchAllFoodsByDate(fromDate:String,toDate:String):ResponseState<Unit>
    fun getFoodDataLiveData(): LiveData<List<Food>>

    fun dailyCalorieLimitLiveData(): LiveData<DailyCalorieLimit>
}