package com.akash.calorie_tracker.data.repositories.datasourceimpl

import android.util.Log
import com.akash.calorie_tracker.data.api.ClientApi
import com.akash.calorie_tracker.data.repositories.datasource.ClientDataSource
import com.akash.calorie_tracker.domain.models.AllFoodsResponse
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Call
import retrofit2.Response

class ClientDataSourceImpl(
    val clientApi: ClientApi
):ClientDataSource {

    val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    override suspend fun addFoodEntry(
        foodCreateRequest: FoodCreateRequest
    ): Response<Food> {

        val image = foodCreateRequest.image?.readBytes() ?: byteArrayOf()
        val foodData =  gson.toJson(foodCreateRequest)
        Log.d("check", "addFoodEntry: " + image.size + " " + foodData)

        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), image)

        val foodDataRequest = RequestBody.create(MediaType.parse("application/json"), foodData)

////        val multipartBody = MultipartBody.Builder()
////            .setType(MultipartBody.FORM)
////            .addFormDataPart("food_data", foodData)
////            .addFormDataPart("image", foodCreateRequest.image?.name, requestFile)
////            .build()
////
////        return clientApi.createFoodMultipart(multipartBody)
//
//
//        clientApi.createFoodMultipartCall(foodDataRequest,MultipartBody.Part.createFormData(
//            "image",
//            foodCreateRequest.image?.name ?: "",
//            requestFile
//        )).enqueue(object :retrofit2.Callback<ResponseBody>{
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                Log.d("test", "onResponse: $response")
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Log.d("test", "onResponse: ${t.message}")
//            }
//
//        })

        return clientApi.createFoodMultipart(
            food_data = foodDataRequest,
            image = MultipartBody.Part.createFormData(
                "image",
                foodCreateRequest.image?.name ?: "",
                requestFile
            )
        )
    }

    override suspend fun allFoods(fromDate: String, toDate: String): Response<AllFoodsResponse> {
        Log.d("test", "allFoods: " + fromDate + " " + toDate)
        return clientApi.getFoods(fromDate, toDate)
    }




}