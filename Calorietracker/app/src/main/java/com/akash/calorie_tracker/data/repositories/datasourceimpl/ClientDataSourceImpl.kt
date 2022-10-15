package com.akash.calorie_tracker.data.repositories.datasourceimpl

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.akash.calorie_tracker.data.api.ClientApi
import com.akash.calorie_tracker.data.repositories.datasource.ClientDataSource
import com.akash.calorie_tracker.domain.models.AllFoodsResponse
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ClientDataSourceImpl(
    val clientApi: ClientApi
):ClientDataSource {

    override suspend fun addFoodEntry(
        foodCreateRequest: FoodCreateRequest
    ): Response<Food> {
        return clientApi.createFood(foodCreateRequest)
    }

    override suspend fun allFoods(fromDate: String, toDate: String): Response<AllFoodsResponse> {
        Log.d("test", "allFoods: " + fromDate + " " + toDate)
        return clientApi.getFoods(fromDate, toDate)
    }


}