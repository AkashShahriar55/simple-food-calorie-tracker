package com.akash.caloriecounter.caloriecounter.api;

import com.akash.caloriecounter.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {

    List<Food> findByUserId(int userId);

    @Query("SELECT f FROM Food f where f.user = ?1 and f.creationDate >= ?2 and f.creationDate <= ?3")
    List<Food> findWithDateFilter(User user, Date from, Date to);

    @Query("SELECT count(f) FROM Food f where f.creationDate >= ?1 and f.creationDate <= ?2")
    Integer countEntriesOfDays(Date from, Date to);


    @Query("SELECT AVG(f.calories) as avg , f.user.email as name FROM Food f where f.calories IS NOT NULL GROUP BY f.user")
    List<OnlyAvgData> getAvgCalories();
}
