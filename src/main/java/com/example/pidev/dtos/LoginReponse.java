package com.example.pidev.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class LoginReponse {

    private String token;
    private Long expiresIn;
    private UserDto user;
    private String message;
    private String recaptchaResponse;
    private boolean using2FA;
    private String secret2FA;


    public String getSecret2FA() {
        return secret2FA;
    }

    public void setSecret2FA(String secret2FA) {
        this.secret2FA = secret2FA;
    }

    public String getSecretImageUri() {
        return secretImageUri;
    }

    public void setSecretImageUri(String secretImageUri) {
        this.secretImageUri = secretImageUri;
    }

    private String secretImageUri;

    public boolean isUsing2FA() {
        return using2FA;
    }

    public LoginReponse setUsing2FA(boolean using2FA) {
        this.using2FA = using2FA;
        return this;
    }

    public String getToken() {
        return token;
    }

    public LoginReponse setToken(String token) {
        this.token = token;
        return this;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public LoginReponse setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public UserDto getUser() {
        return user;
    }

    public LoginReponse setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LoginReponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRecaptchaResponse() {
        return recaptchaResponse;
    }

    public LoginReponse setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
        return this;
    }

}
