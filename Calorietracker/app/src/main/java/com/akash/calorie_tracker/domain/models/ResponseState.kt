package com.akash.calorie_tracker.domain.models

enum class Status{
    Login_successfull,
    Login_failed,
    Error
}
data class  ResponseState<T>
    (val status: Status,val message:String,val data:T?)