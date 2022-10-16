package com.akash.calorie_tracker.data.api

import com.akash.calorie_tracker.ALL_FOODS_ADMIN
import com.akash.calorie_tracker.REPORT
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Reports
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AdminApi {


    @GET(ALL_FOODS_ADMIN)
    suspend fun getFoodListPaging(
        @Query("item_per_page") itemPerPage: Int,
        @Query("page_no") nextPageNumber: Int):Response<List<FoodWithUserInfo>>

    @GET(REPORT)
    suspend fun getReport():Response<Reports>


}