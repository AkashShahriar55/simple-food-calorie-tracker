package com.akash.calorie_tracker.data.api

import com.akash.calorie_tracker.*
import com.akash.calorie_tracker.domain.models.FoodEditRequest
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Reports
import com.akash.calorie_tracker.domain.models.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {


    @GET(ALL_FOODS_ADMIN)
    suspend fun getFoodListPaging(
        @Query("item_per_page") itemPerPage: Int,
        @Query("page_no") nextPageNumber: Int):Response<List<FoodWithUserInfo>>

    @GET(REPORT)
    suspend fun getReport():Response<Reports>


    @PUT(UPDATE)
    suspend fun updateFood(foodEditRequest: FoodEditRequest):Response<ResponseBody>

    @DELETE("$DELETE/{Id}")
    suspend fun deleteFood(@Path("Id") id:Int ):Response<ResponseBody>


    @GET(USERS)
    suspend fun getUsers():Response<List<User>>



}