package com.akash.calorie_tracker.architecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.usecases.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(
   private val loginUseCases: LoginUseCases
): ViewModel()  {


    val handler = CoroutineExceptionHandler {
            context, exception ->
        if(exception is SocketTimeoutException)
            _loginResponse.value = ResponseState<User>(Status.TIMEOUT,"Timeout","Please check your internet connection",null)
        println("Caught $exception")
    }


    private val _loginResponse:MutableLiveData<ResponseState<User>> = MutableLiveData()
    var loginResponse: LiveData<ResponseState<User>> = _loginResponse

    fun login(loginRequest: LoginRequest):Boolean{

        if(loginUseCases.loginWithSession(loginRequest.email))
            return true

        viewModelScope.launch(handler) {
            _loginResponse.value = loginUseCases.login(loginRequest).value
        }
        return false
    }

    fun getRoles(username: String): List<Role> {
        return loginUseCases.getSavedRoles(username)
    }


}