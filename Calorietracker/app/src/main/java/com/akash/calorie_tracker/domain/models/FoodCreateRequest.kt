package com.akash.calorie_tracker.domain.models

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.util.*

data class FoodCreateRequest(
    val name:String,
    val calories:Float,
    val creationDate:String,

    @SerializedName("user_id")
    val userId:String?,

    @SerializedName("image")
    val image:String?

)
