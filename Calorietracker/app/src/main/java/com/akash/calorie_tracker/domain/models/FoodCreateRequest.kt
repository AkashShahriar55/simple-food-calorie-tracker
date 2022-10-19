package com.akash.calorie_tracker.domain.models

import android.net.Uri
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File
import java.util.*
import javax.annotation.meta.Exclusive

data class FoodCreateRequest(

    @Expose
    val id:String?,

    @Expose
    val name:String,
    @Expose
    val calories:Float,
    @Expose
    val creationDate:String,

    @Expose
    val creationTime:String,

    @Expose
    @SerializedName("user")
    val user:User?,

    @SerializedName("image")
    val image: File?

)
