package com.akash.calorie_tracker.data.repositories

import android.util.Log
import androidx.paging.PagingSource
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.data.repositories.datasource.AdminDataSource
import com.akash.calorie_tracker.data.repositories.pagging.FoodsWithUserPagingSource
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Reports
import com.akash.calorie_tracker.domain.models.ResponseState
import com.akash.calorie_tracker.domain.models.Status
import com.akash.calorie_tracker.domain.repositories.AdminRepository

class AdminRepositoryImpl(
    val adminDataSource: AdminDataSource,
    val sessionManager: SessionManager
) : AdminRepository {

    val foodsWithUserPagingSource = FoodsWithUserPagingSource(adminDataSource)
    override fun dataSource(): PagingSource<Int, FoodWithUserInfo> {
        return foodsWithUserPagingSource
    }

    override suspend fun getReports(): ResponseState<Reports> {
        val response = adminDataSource.getReports()
        Log.d("check", "getReports: " + response.message())
        return if(response.isSuccessful){
            if(response.body()!=null){
                ResponseState(Status.SUCCESSFUL,"Report fetched","Report fetch was successful",response.body())
            }else{
                ResponseState(Status.FAILED,"Body empty","no data fetched",null)
            }
        }else{
            ResponseState(Status.FAILED,"Report fetch failed","Report fetch was not successful",null)
        }

    }

    override fun logout() {
        sessionManager.logout()
    }


}