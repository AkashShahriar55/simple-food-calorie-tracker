package com.akash.calorie_tracker.data.repositories.datasource

import com.akash.calorie_tracker.domain.models.*
import okhttp3.ResponseBody
import retrofit2.Response

interface AdminDataSource {
    suspend fun getFoodListPaging(itemPerPage: Int, nextPageNumber: Int): Response<List<FoodWithUserInfo>>
    suspend fun getReports():Response<Reports>
    suspend fun update(foodEditRequest: FoodEditRequest):Response<ResponseBody>
    suspend fun delete(id:Int):Response<ResponseBody>
    suspend fun getUsers():Response<List<User>>
}
