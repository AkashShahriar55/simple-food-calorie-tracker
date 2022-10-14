package com.akash.calorie_tracker.architecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.calorie_tracker.domain.models.*
import com.akash.calorie_tracker.domain.usecases.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(
   private val loginUseCases: LoginUseCases
): ViewModel()  {


    private val _loginResponse:MutableLiveData<ResponseState<LoginData>> = MutableLiveData()
    var loginResponse: LiveData<ResponseState<LoginData>> = _loginResponse

    fun login(loginRequest: LoginRequest):Boolean{

        if(loginUseCases.isAlreadyLoggedIn(loginRequest.email))
            return true

        viewModelScope.launch {
            _loginResponse.value = loginUseCases.login(loginRequest).value
        }
        return false
    }

    fun getRoles(username: String): List<Role> {
        return loginUseCases.getSavedRoles(username)
    }


}