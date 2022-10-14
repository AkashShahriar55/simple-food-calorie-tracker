package com.akash.calorie_tracker.domain.models

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id")
    val id:String,

    @SerializedName("email")
    val email:String

)
