package com.akash.caloriecounter.caloriecounter.api;

import com.akash.caloriecounter.user.api.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


public class FoodWithUserExposed extends Food {

    @Override
    public User getUser() {
        return super.getUser();
    }
}
