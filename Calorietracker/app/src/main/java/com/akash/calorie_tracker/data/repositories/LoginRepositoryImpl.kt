package com.akash.calorie_tracker.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.data.repositories.datasource.LoginDataSource
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.repositories.LoginRepository
import com.akash.calorie_tracker.helpers.DataFormatHelper

class LoginRepositoryImpl(
    val loginDataSource: LoginDataSource,
    val sessionManager: SessionManager
):LoginRepository {

    val loginStatusLiveData = MutableLiveData<ResponseState<LoginData>>()


    override suspend fun login(loginRequest: LoginRequest):LiveData<ResponseState<LoginData>> {
        val response = loginDataSource.login(loginRequest)

        if(!response.isSuccessful){
            loginStatusLiveData.value = ResponseState(Status.Login_failed,"Login failed",null)
        }else{
            response.body()?.let {
                Log.d("response", "login: " + it)
                sessionManager.saveAuthToken(it.username,it.authToken)
                sessionManager.saveRoles(it.username,it.roles)
                loginStatusLiveData.value = ResponseState(
                    Status.Login_successfull,
                    "Successfully Logged in",
                    LoginData(
                        it.id,
                        DataFormatHelper.extractRoles(it.roles)
                    )
                )
            } ?: kotlin.run {
                loginStatusLiveData.value = ResponseState(Status.Error,"Something went wrong",null)
            }

        }
        return loginStatusLiveData
    }

    override fun isAlreadyLoggedIn(username:String): Boolean {
        return sessionManager.fetchAuthToken(username) != null
    }

    override fun getSavedRoles(username: String): List<Role> {
        return  sessionManager.getRoles(username)
    }


}