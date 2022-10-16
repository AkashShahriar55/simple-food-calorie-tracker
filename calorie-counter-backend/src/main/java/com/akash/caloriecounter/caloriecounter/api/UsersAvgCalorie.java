package com.akash.caloriecounter.caloriecounter.api;

public class UsersAvgCalorie {
    String name;
    Float avg_calories;


    public UsersAvgCalorie() {
    }

    public UsersAvgCalorie(String name, Float avg_calories) {
        this.name = name;
        this.avg_calories = avg_calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAvg_calories() {
        return avg_calories;
    }

    public void setAvg_calories(Float avg_calories) {
        this.avg_calories = avg_calories;
    }
}
