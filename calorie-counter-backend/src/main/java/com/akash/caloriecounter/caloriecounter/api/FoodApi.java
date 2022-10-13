package com.akash.caloriecounter.caloriecounter.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodApi {

    @Autowired FoodRepository foodRepository;


    @GetMapping
    public List<Food> list(){
        return foodRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Food> create(@RequestBody @Valid Food food) {
        Food savedFood = foodRepository.save(food);
        URI productURI = URI.create("/foods/" + savedFood.getId());
        return ResponseEntity.created(productURI).body(savedFood);
    }

}
