package com.akash.caloriecounter;

import com.akash.caloriecounter.auth.api.AuthApi;
import com.akash.caloriecounter.caloriecounter.api.Food;
import com.akash.caloriecounter.models.AuthenticationRequest;
import com.akash.caloriecounter.user.api.Role;
import com.akash.caloriecounter.user.api.User;
import com.akash.caloriecounter.user.api.UserRepository;
import com.akash.caloriecounter.user.api.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;





    @Test
    public void testCreateUser(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "akash";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User("akashshahriar.admin@gmail.com",encodedPassword);

        User savedUser = userRepository.save(user);
        assert savedUser != null;
        assert savedUser.getId() > 0;

    }

    @Test
    public void createUserAndAssignRole(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "akash";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User("akashshahriar.user2@gmail.com",encodedPassword);

        User savedUser = userRepository.save(user);

        assert savedUser != null;
        assert savedUser.getId() > 0;

        Integer roleId = 2;
        user.addRole(new Role(roleId));

        User updatedUser = userRepository.save(user);
        assert  updatedUser.getRoles().size() > 0;
    }


    @Test
    public void updateCalorieLimit(){
        User user = userRepository.findByEmail("akashshahriar.user@gmail.com").get();
        user.setCalorieLimit(2100f);
        userRepository.save(user);

    }

    @Test
    public void userFind(){
        String username = "akashshahriar71@gmail.com";



        User user = userRepository.findByEmail(username).get();

        assert user != null;
        assert user.getEmail().equals(username);

    }

    @Test
    public void testAssignRoleToUser() {
        Integer userId = 8;
        Integer roleId = 1;
        User user = userRepository.findById(userId).get();
        user.addRole(new Role(roleId));

        User updatedUser = userRepository.save(user);
        assert  updatedUser.getRoles().size() > 0;

    }



}
