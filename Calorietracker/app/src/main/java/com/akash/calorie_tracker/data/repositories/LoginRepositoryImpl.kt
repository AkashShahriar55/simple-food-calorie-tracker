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

    val loginStatusLiveData = MutableLiveData<ResponseState<User>>()


    override suspend fun login(loginRequest: LoginRequest):LiveData<ResponseState<User>> {
        val response = loginDataSource.login(loginRequest)

        if(!response.isSuccessful){
            loginStatusLiveData.value = ResponseState(Status.Login_failed,"Login failed","Email or password is incorrect.",null)
        }else{
            response.body()?.let {
                Log.d("response", "login: " + it)

                val user =  User(
                    it.id,
                    it.roles,
                    it.username
                )


                sessionManager.setUser(user,it.authToken)
                loginStatusLiveData.value = ResponseState(
                    Status.Login_successfull,
                    "Login Successful",
                    "Successfully Logged in",
                    user
                )
            } ?: kotlin.run {
                loginStatusLiveData.value = ResponseState(Status.Error,"Empty Response","Something went wrong !",null)
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

    override fun loginWithSession(email: String): Boolean {
        return sessionManager.login(email)
    }


}