package com.akash.caloriecounter.auth.api;

import com.akash.caloriecounter.Utils.JWTutils;
import com.akash.caloriecounter.user.api.User;
import com.akash.caloriecounter.user.api.UserService;
import com.akash.caloriecounter.models.AuthenticationRequest;
import com.akash.caloriecounter.models.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthApi {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private JWTutils jwtTokenUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthApi.class);


    @PostMapping( "/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            AuthenticationResponse response = new AuthenticationResponse( accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }



    }
}
