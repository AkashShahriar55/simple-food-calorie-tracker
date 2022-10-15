package com.akash.calorie_tracker.domain.models

enum class Status{
    SUCCESSFUL,
    FAILED,
    TIMEOUT,
    Login_successfull,
    Login_failed,
    Error
}
data class  ResponseState<T>
    (val status: Status,val title:String,val message:String,val data:T?)