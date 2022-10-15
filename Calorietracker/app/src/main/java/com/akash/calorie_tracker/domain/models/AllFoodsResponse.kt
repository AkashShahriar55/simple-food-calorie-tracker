package com.akash.calorie_tracker.domain.models

import com.google.gson.annotations.SerializedName

data class AllFoodsResponse(
    val calorieLimit:Float,
    @SerializedName("foods")
    val foodList:List<Food>
)
