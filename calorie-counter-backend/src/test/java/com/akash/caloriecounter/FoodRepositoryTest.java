package com.akash.caloriecounter;

import com.akash.caloriecounter.caloriecounter.api.Food;
import com.akash.caloriecounter.caloriecounter.api.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class FoodRepositoryTest {

    @Autowired
    FoodRepository foodRepository;

    @Test
    public void testFood(){
        Food food = new Food();
        food.setName("test");
        food.setCalories(100);
        Calendar myCalendar = new GregorianCalendar(2022, Calendar.OCTOBER, 15);
        Date myDate = myCalendar.getTime();
        food.setCreationDate(myDate);
        foodRepository.save(food);
    }
}
