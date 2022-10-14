package com.akash.calorie_tracker.domain.models

import com.google.gson.annotations.SerializedName
import java.net.IDN

data class LoginResponse(
    @SerializedName("status_code")
    var statusCode:Int,

    @SerializedName("auth_token")
    var authToken:String,

    @SerializedName("id")
    var id: String,

    @SerializedName("roles")
    var roles:List<String>,

    @SerializedName("username")
    var username:String
)
