package com.akash.calorie_tracker.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.akash.calorie_tracker.domain.models.*

interface AdminRepository {

    fun dataSource():PagingSource<Int,FoodWithUserInfo>

    suspend fun getReports():ResponseState<Reports>
    fun logout()

    suspend fun deleteFood(foodEditRequest: FoodWithUserInfo):ResponseState<Unit>


    suspend fun updateFood(foodEditRequest: FoodEditRequest):ResponseState<Unit>

    suspend fun getUsers():ResponseState<List<User>>
}