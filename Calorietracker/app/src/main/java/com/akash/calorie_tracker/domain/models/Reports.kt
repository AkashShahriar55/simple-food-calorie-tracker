package com.akash.calorie_tracker.domain.models

import com.google.gson.annotations.SerializedName

data class Reports(
    @SerializedName("this_week_count")
    val thisWeekCount:Int,

    @SerializedName("last_week_count")
    val lastWeekCount:Int,

    @SerializedName("avgCalorieList")
    val avgCalorieList:List<AvgCalorie>
)
