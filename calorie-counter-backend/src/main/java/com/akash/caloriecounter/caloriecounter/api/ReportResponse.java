package com.akash.caloriecounter.caloriecounter.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class ReportResponse {


    int this_week_count;
    int last_week_count;

    String text;

    List<UsersAvgCalorie> avgCalorieList;

    public ReportResponse() {
    }

    public ReportResponse(int this_week_count, int last_week_count, String text, List<UsersAvgCalorie> avgCalorieList) {
        this.this_week_count = this_week_count;
        this.last_week_count = last_week_count;
        this.text = text;
        this.avgCalorieList = avgCalorieList;
    }

    public int getThis_week_count() {
        return this_week_count;
    }

    public void setThis_week_count(int this_week_count) {
        this.this_week_count = this_week_count;
    }

    public int getLast_week_count() {
        return last_week_count;
    }

    public void setLast_week_count(int last_week_count) {
        this.last_week_count = last_week_count;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<UsersAvgCalorie> getAvgCalorieList() {
        return avgCalorieList;
    }

    public void setAvgCalorieList(List<UsersAvgCalorie> avgCalorieList) {
        this.avgCalorieList = avgCalorieList;
    }
}
