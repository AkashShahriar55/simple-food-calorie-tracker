package com.akash.caloriecounter.caloriecounter.api;

import com.akash.caloriecounter.user.api.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "foods")
public class Food {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(nullable = false,length = 128)
    private String name;

    private float calories;

    @Column(name = "calorie_limit")
    private float calorieLimit;

    @Temporal(TemporalType.DATE)
    private Date creationDate = new Date(System.currentTimeMillis());

    @ManyToOne
    @JsonIgnore
    private User user;

    public Food() {
    }

    public Food(int id, String name, float calories, float calorieLimit, Date creationDate) {
        Id = id;
        this.name = name;
        this.calories = calories;
        this.calorieLimit = calorieLimit;
        this.creationDate = creationDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getCalorieLimit() {
        return calorieLimit;
    }

    public void setCalorieLimit(float calorieLimit) {
        this.calorieLimit = calorieLimit;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
