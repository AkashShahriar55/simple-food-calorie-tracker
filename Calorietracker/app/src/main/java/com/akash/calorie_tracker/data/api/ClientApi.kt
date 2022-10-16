package com.akash.calorie_tracker.data.api

import com.akash.calorie_tracker.FOODS_URL
import com.akash.calorie_tracker.LOGIN_URL
import com.akash.calorie_tracker.domain.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ClientApi {

    @POST(FOODS_URL)
//    @FormUrlEncoded
    suspend fun createFood(@Body foodCreateRequest: FoodCreateRequest): Response<Food>



    @Multipart
    @POST(FOODS_URL)
//    @FormUrlEncoded
    suspend fun createFoodMultipart(
        @Part("food_data") food_data : RequestBody,
        @Part image: MultipartBody.Part
    ): Response<Food>


    @Multipart
    @POST(FOODS_URL)
//    @FormUrlEncoded
    fun createFoodMultipartCall(
        @Part("food_data") food_data : RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>

    @GET(FOODS_URL)
    suspend fun getFoods(
        @Query("from_date") fromDate:String,
        @Query("to_date") toDate:String
    ):Response<AllFoodsResponse>
}