package com.akash.calorie_tracker.data.repositories.datasourceimpl

import com.akash.calorie_tracker.data.api.AdminApi
import com.akash.calorie_tracker.data.repositories.datasource.AdminDataSource
import com.akash.calorie_tracker.domain.models.FoodEditRequest
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Reports
import okhttp3.ResponseBody
import retrofit2.Response

class AdminDataSourceImpl(
    val adminApi: AdminApi
) : AdminDataSource {
    override suspend fun getFoodListPaging(
        itemPerPage: Int,
        nextPageNumber: Int
    ): Response<List<FoodWithUserInfo>> {
        return adminApi.getFoodListPaging(itemPerPage,nextPageNumber)
    }

    override suspend fun getReports(): Response<Reports> {
        return adminApi.getReport()
    }

    override suspend fun update(foodEditRequest: FoodEditRequest): Response<ResponseBody> {
        return adminApi.updateFood(foodEditRequest)
    }

    override suspend fun delete(foodEditRequest: FoodEditRequest): Response<ResponseBody> {
        return adminApi.deleteFood(foodEditRequest)
    }


}