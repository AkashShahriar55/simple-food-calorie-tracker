package com.akash.calorie_tracker.domain.models

import com.google.gson.annotations.SerializedName

data class AvgCalorie(
    @SerializedName("name")
    val email:String,

    @SerializedName("avg_calories")
    val avgCalories: Float
)
