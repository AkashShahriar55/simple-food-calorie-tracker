package com.akash.calorie_tracker.data.repositories.datasource

import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Reports
import retrofit2.Response

interface AdminDataSource {
    suspend fun getFoodListPaging(itemPerPage: Int, nextPageNumber: Int): Response<List<FoodWithUserInfo>>
    suspend fun getReports():Response<Reports>
}
