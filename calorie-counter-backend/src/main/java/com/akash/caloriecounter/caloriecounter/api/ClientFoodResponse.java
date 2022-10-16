package com.akash.caloriecounter.caloriecounter.api;

import java.util.List;

public class ClientFoodResponse {
    Float calorieLimit;
    List<Food> foods;

    public Float getCalorieLimit() {
        return calorieLimit;
    }

    public void setCalorieLimit(Float calorieLimit) {
        this.calorieLimit = calorieLimit;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public ClientFoodResponse(Float calorieLimit, List<Food> foods) {
        this.calorieLimit = calorieLimit;
        this.foods = foods;
    }

    public ClientFoodResponse() {
    }
}
