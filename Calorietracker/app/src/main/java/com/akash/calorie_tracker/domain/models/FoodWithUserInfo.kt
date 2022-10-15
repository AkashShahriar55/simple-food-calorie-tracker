package com.akash.calorie_tracker.domain.models

import android.graphics.Bitmap

data class FoodWithUserInfo(
    var id:String,
    var name:String,
    var calorie:Int,
    var date:String,
    var image:Bitmap,
    var user: User
)
