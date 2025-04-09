package com.example.pidev.dtos;
public class LoginReponse {

    private String token;
    private Long expiresIn;
    private UserDto user;


    public String getToken() {
        return token;
    }

    public LoginReponse setToken(String token) {
        this.token = token;
        return this;
    }
    public UserDto getUser() {
        return user;
    }
    public LoginReponse setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public LoginReponse setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
