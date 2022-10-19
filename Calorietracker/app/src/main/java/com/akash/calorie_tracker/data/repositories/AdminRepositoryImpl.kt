package com.akash.calorie_tracker.data.repositories

import android.util.Log
import androidx.paging.PagingSource
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.data.repositories.datasource.AdminDataSource
import com.akash.calorie_tracker.data.repositories.pagging.FoodsWithUserPagingSource
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.repositories.AdminRepository

class AdminRepositoryImpl(
    val adminDataSource: AdminDataSource,
    val sessionManager: SessionManager
) : AdminRepository {

    override fun dataSource(): PagingSource<Int, FoodWithUserInfo> {
        return FoodsWithUserPagingSource(adminDataSource)
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

    override suspend fun deleteFood(foodEditRequest: FoodWithUserInfo): ResponseState<Unit> {
        if(foodEditRequest.id == null) return ResponseState(Status.FAILED,"Id is null","no id for food entry",null)
        val response = adminDataSource.delete(foodEditRequest.id!!.toInt())
        Log.d("check", "deleteFood: " + response.message())
        return if(response.isSuccessful){
            if(response.body()!=null){
                ResponseState(Status.SUCCESSFUL,"Food deleted","food delete was successful",null)
            }else{
                ResponseState(Status.FAILED,"Body empty","no data fetched",null)
            }
        }else{
            ResponseState(Status.FAILED,"Food delete failed","Food delete was not successful",null)
        }
    }

    override suspend fun updateFood(foodEditRequest: FoodEditRequest): ResponseState<Unit> {
        val response = adminDataSource.update(foodEditRequest)
        Log.d("check", "updateFood: " + response.message())
        return if(response.isSuccessful){
            if(response.body()!=null){
                ResponseState(Status.SUCCESSFUL,"Food updated","Food update was successful",null)
            }else{
                ResponseState(Status.FAILED,"Body empty","no data fetched",null)
            }
        }else{
            ResponseState(Status.FAILED,"Food update failed","Food update was not successful",null)
        }
    }

    override suspend fun getUsers(): ResponseState<List<User>> {
        val response = adminDataSource.getUsers()
        Log.d("check", "getReports: " + response.message())
        return if(response.isSuccessful){
            if(response.body()!=null){
                ResponseState(Status.SUCCESSFUL,"User fetched","User fetch was successful",response.body())
            }else{
                ResponseState(Status.FAILED,"Body empty","no data fetched",null)
            }
        }else{
            ResponseState(Status.FAILED,"User fetch failed","User fetch was not successful",null)
        }
    }


}