package com.akash.calorie_tracker.data.repositories.datasourceimpl

import android.util.Log
import androidx.lifecycle.LiveData
import com.akash.calorie_tracker.data.api.LoginApi
import com.akash.calorie_tracker.data.repositories.datasource.LoginDataSource
import com.akash.calorie_tracker.domain.models.LoginRequest
import com.akash.calorie_tracker.domain.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginDatasourceImpl(
    val loginApi: LoginApi
) : LoginDataSource {


    override suspend fun login(loginRequest: LoginRequest):Response<LoginResponse> {
        return loginApi.login(loginRequest)
    }
}