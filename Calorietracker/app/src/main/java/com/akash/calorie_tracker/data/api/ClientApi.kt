package com.akash.calorie_tracker.data.api

import com.akash.calorie_tracker.FOODS_URL
import com.akash.calorie_tracker.LOGIN_URL
import com.akash.calorie_tracker.domain.models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ClientApi {

    @POST(FOODS_URL)
//    @FormUrlEncoded
    suspend fun createFood(@Body foodCreateRequest: FoodCreateRequest): Response<Food>

    @GET(FOODS_URL)
    suspend fun getFoods(
        @Query("from_date") fromDate:String,
        @Query("to_date") toDate:String
    ):Response<AllFoodsResponse>
}