package com.akash.caloriecounter;

import com.akash.caloriecounter.auth.api.AuthApi;
import com.akash.caloriecounter.models.AuthenticationRequest;
import com.akash.caloriecounter.user.api.User;
import com.akash.caloriecounter.user.api.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;




    @Test
    public void testCreateUser(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "akash71";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User("akashshahriar1@gmail.com",rawPassword);

        User savedUser = userRepository.save(user);
        assert savedUser != null;
        assert savedUser.getId() > 0;

    }

    @Test
    public void userFind(){
        String username = "akashshahriar71@gmail.com";



        User user = userRepository.findByEmail(username).get();

        assert user != null;
        assert user.getEmail().equals(username);

    }
}