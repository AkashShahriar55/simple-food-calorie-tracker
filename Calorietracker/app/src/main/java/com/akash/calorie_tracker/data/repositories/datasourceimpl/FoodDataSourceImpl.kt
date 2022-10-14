package com.akash.calorie_tracker.data.repositories.datasourceimpl

import com.akash.calorie_tracker.data.api.FoodApi
import com.akash.calorie_tracker.data.repositories.datasource.FoodDataSource

class FoodDataSourceImpl(
    val foodApi: FoodApi
) : FoodDataSource {
}