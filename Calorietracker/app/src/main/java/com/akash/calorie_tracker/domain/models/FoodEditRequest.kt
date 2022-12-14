package com.akash.calorie_tracker.domain.models

import android.net.Uri
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File
import java.util.*
import javax.annotation.meta.Exclusive

data class FoodEditRequest(


    val id:String,

    @Expose
    val name:String,
    @Expose
    val calories:Float,
    @Expose
    val creationDate:String,

    @SerializedName("user_id")
    val userId:String?,

    @SerializedName("image")
    val image: File?

)
