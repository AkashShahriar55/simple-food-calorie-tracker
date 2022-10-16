package com.akash.calorie_tracker.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.data.repositories.datasource.ClientDataSource
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.repositories.ClientRepository
import retrofit2.Response

class ClientRepositoryImpl(
    val clientDataSource: ClientDataSource,
    val sessionManager: SessionManager) :ClientRepository {



    private val _foodDataList: MutableLiveData<List<Food>> = MutableLiveData()
    var foodDataList: LiveData<List<Food>> = _foodDataList

    private val _dailyCalorieLimit: MutableLiveData<DailyCalorieLimit> = MutableLiveData()
    var dailyCalorieLimit: LiveData<DailyCalorieLimit> = _dailyCalorieLimit


    override suspend fun addFoodEntry(foodCreateRequest: FoodCreateRequest): Response<Food> {
        return clientDataSource.addFoodEntry(foodCreateRequest)
    }

    override suspend fun fetchAllFoodsByDate(
        fromDate: String,
        toDate: String
    ): ResponseState<Unit> {
        val response = clientDataSource.allFoods(fromDate,toDate)

        if(response.isSuccessful){

            val allFoodsResponse = response.body()
            allFoodsResponse?.let {
                Log.d("data", "fetchAllFoods: " + it)
                _foodDataList.value = it.foodList
                calculateDaylyCalorieLimit(it)
                return ResponseState(Status.SUCCESSFUL,"Fetched","All data have fetched",null)
            } ?: kotlin.run {
                return ResponseState(Status.Error,"Data is null","Something got wrong",null)
            }
        }else{
            return ResponseState(Status.FAILED,"Fetch failed","no data fetched",null)
        }


    }

    private fun calculateDaylyCalorieLimit(it: AllFoodsResponse) {
        val calorieLimit = it.calorieLimit
        var totalConsumed = 0f;
        for(food in it.foodList){
            totalConsumed+=food.calorie
        }

        _dailyCalorieLimit.value = DailyCalorieLimit(totalConsumed, calorieLimit)
    }

    override fun getFoodDataLiveData(): LiveData<List<Food>> {
        return foodDataList
    }

    override fun dailyCalorieLimitLiveData(): LiveData<DailyCalorieLimit> {
        return dailyCalorieLimit
    }

    override fun logout() {
        sessionManager.logout()
    }


}