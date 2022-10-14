package com.akash.calorie_tracker.data.repositories

import com.akash.calorie_tracker.data.repositories.datasource.FoodDataSource
import com.akash.calorie_tracker.domain.repositories.FoodRepository

class FoodRepositoryImpl(
    val foodDataSource: FoodDataSource
) : FoodRepository {
}