package com.akash.calorie_tracker.domain.usecases

import androidx.lifecycle.LiveData
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.repositories.LoginRepository

class LoginUseCases(
    private val loginRepository: LoginRepository
) {


    suspend fun login(loginRequest: LoginRequest):LiveData<ResponseState<LoginData>> {
        return loginRepository.login(loginRequest)
    }

    fun isAlreadyLoggedIn(username:String):Boolean {
        return loginRepository.isAlreadyLoggedIn(username)
    }

    fun getSavedRoles(username: String):List<Role>{
        return loginRepository.getSavedRoles(username)
    }


}