package com.akash.calorie_tracker.data.repositories.datasourceimpl

import com.akash.calorie_tracker.data.api.AdminApi
import com.akash.calorie_tracker.data.repositories.datasource.AdminDataSource
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Reports
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




}