package com.akash.caloriecounter.models;

import com.akash.caloriecounter.user.api.User;

import java.util.List;

public class AuthenticationResponse {

    private final String auth_token;
    private final int status_code;
    private final List<String> roles;
    private final String id;
    private final String username;

    public AuthenticationResponse(String auth_token, int status_code,List<String> roles,String id,String username) {
        this.auth_token = auth_token;
        this.status_code = status_code;
        this.roles = roles;
        this.id = id;
        this.username = username;
    }


    public List<String> getRoles() {
        return roles;
    }


    public String getAuth_token() {
        return auth_token;
    }

    public int getStatus_code() {
        return status_code;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return auth_token;
    }
}
