package com.akash.calorie_tracker.data.api

import androidx.lifecycle.LiveData
import com.akash.calorie_tracker.LOGIN_URL
import com.akash.calorie_tracker.domain.models.LoginRequest
import com.akash.calorie_tracker.domain.models.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @POST(LOGIN_URL)
//    @FormUrlEncoded
    suspend fun login(@Body loginRequest: LoginRequest ): Response<LoginResponse>

}