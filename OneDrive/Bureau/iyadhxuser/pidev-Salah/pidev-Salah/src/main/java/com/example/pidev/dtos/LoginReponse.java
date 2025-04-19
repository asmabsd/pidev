package com.example.pidev.dtos;

public class LoginReponse {

    private String token;
    private Long expiresIn;
    private UserDto user;
    private String message; // ✅ Add this
    private String recaptchaResponse; // reCAPTCHA response token


    public String getToken() {
        return token;
    }

    public String getRecaptchaResponse() {
        return recaptchaResponse;
    }

    public void setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
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

    public String getMessage() {
        return message;
    }

    public LoginReponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
