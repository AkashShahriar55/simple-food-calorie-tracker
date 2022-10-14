package com.akash.calorie_tracker.data.repositories.datasource

import androidx.lifecycle.LiveData
import com.akash.calorie_tracker.data.api.LoginApi
import com.akash.calorie_tracker.domain.models.LoginRequest
import com.akash.calorie_tracker.domain.models.LoginResponse
import retrofit2.Response

interface LoginDataSource {
    suspend fun login(loginRequest: LoginRequest):Response<LoginResponse>
}