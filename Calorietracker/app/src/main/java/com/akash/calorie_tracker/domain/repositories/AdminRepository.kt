package com.akash.calorie_tracker.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Reports
import com.akash.calorie_tracker.domain.models.ResponseState

interface AdminRepository {

    fun dataSource():PagingSource<Int,FoodWithUserInfo>

    suspend fun getReports():ResponseState<Reports>
    fun logout()
}