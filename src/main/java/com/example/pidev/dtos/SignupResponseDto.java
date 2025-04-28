package com.example.pidev.dtos;

import com.example.pidev.entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class SignupResponseDto {
    public SignupResponseDto(User user, String qrCodeUri) {
        this.user = user;
        this.qrCodeUri = qrCodeUri;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQrCodeUri() {
        return qrCodeUri;
    }

    public void setQrCodeUri(String qrCodeUri) {
        this.qrCodeUri = qrCodeUri;
    }

    private User user;
    private String qrCodeUri;
}