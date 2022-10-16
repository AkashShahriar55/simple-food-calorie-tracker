package com.akash.caloriecounter.caloriecounter.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class foodService {

    @Autowired
    FoodRepository foodRepository;


}
