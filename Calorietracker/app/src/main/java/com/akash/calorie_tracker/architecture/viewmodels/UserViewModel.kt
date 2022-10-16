package com.akash.calorie_tracker.architecture.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.repositories.ClientRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.rxjava2.Result.response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    val clientRepository: ClientRepository
)
    :ViewModel() {

    val handler = CoroutineExceptionHandler {
            context, exception ->
        val gson = Gson()

        Log.d("test", ": " + exception.cause)
        if(exception is SocketTimeoutException)
            _foodDataResponse.value = ResponseState<Unit>(Status.TIMEOUT,"Timeout","Please check your internet connection",null)
        println("Caught $exception")
    }



    fun logout(){
        clientRepository.logout()
    }



    private val _addFoodDataResponse: MutableLiveData<Food> = MutableLiveData()
    var addFoodDataResponse: LiveData<Food> = _addFoodDataResponse

        fun createFood(createRequest: FoodCreateRequest): LiveData<Food> {
            viewModelScope.launch(handler) {
                val response = clientRepository.addFoodEntry(createRequest)

                Log.d("test", "createFood: " + response.message())

                if(response.isSuccessful){
                    _addFoodDataResponse.value = response.body()
                    _foodDataResponse.value = ResponseState(Status.SUCCESSFUL,"Add food success","Add food was successful",null)
                }else{
                    _foodDataResponse.value = ResponseState(Status.FAILED,"Add food failed","Add food was not successful",null)
                }


            }

            return addFoodDataResponse

        }



    private val _foodDataResponse: MutableLiveData<ResponseState<Unit>> = MutableLiveData()
    var foodDataResponse: LiveData<ResponseState<Unit>> = _foodDataResponse

    fun fetchFoodData(fromDate: String, toDate: String):LiveData<ResponseState<Unit>> {
        viewModelScope.launch(handler) {
            _foodDataResponse.value = clientRepository.fetchAllFoodsByDate(fromDate, toDate)
        }


        return foodDataResponse
    }

    fun getFoodDataLiveData():LiveData<List<Food>> {
        return clientRepository.getFoodDataLiveData()
    }

    fun dailyCalorieLimitLiveData():LiveData<DailyCalorieLimit> {
        return clientRepository.dailyCalorieLimitLiveData()
    }


}