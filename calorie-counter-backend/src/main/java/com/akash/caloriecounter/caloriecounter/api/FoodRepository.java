package com.akash.caloriecounter.caloriecounter.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {

    List<Food> findByUserId(int userId);

}
