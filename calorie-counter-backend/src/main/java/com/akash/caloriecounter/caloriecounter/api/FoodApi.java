package com.akash.caloriecounter.caloriecounter.api;


import com.akash.caloriecounter.user.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodApi {

    @Autowired FoodRepository foodRepository;


    @GetMapping("/all")
    @RolesAllowed("ROlE_ADMIN")
    public List<Food> allList(){
        return foodRepository.findAll();
    }



    @GetMapping
    @RolesAllowed({"ROLE_USER"})
    public List<Food> list(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return foodRepository.findByUserId(user.getId());
    }


    @PostMapping
    public ResponseEntity<Food> create(@RequestBody @Valid Food food) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof User){
            food.setUser((User) authentication.getPrincipal());
        }
        Food savedFood = foodRepository.save(food);
        URI productURI = URI.create("/foods/" + savedFood.getId());
        return ResponseEntity.created(productURI).body(savedFood);
    }

}
