package com.akash.calorie_tracker.domain.repositories

import androidx.lifecycle.LiveData
import com.akash.calorie_tracker.domain.models.*

interface LoginRepository {
   suspend fun login(loginRequest: LoginRequest):LiveData<ResponseState<LoginData>>
   fun isAlreadyLoggedIn(username:String):Boolean
   fun getSavedRoles(username: String): List<Role>
}