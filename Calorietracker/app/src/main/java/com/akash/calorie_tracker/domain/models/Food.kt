package com.akash.calorie_tracker.domain.models

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Food(
    var id:String,
    var name:String,
    @SerializedName("calories")
    var calorie:Int,
    @SerializedName("creationDate")
    var date:String,
    @SerializedName("creationTime")
    var time:String,

    var image:String
)
