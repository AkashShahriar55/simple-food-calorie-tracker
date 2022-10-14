package com.akash.calorie_tracker.domain.usecases

import com.akash.calorie_tracker.domain.repositories.FoodRepository

class FoodUserUserCase(
    val foodRepository: FoodRepository
) {
}