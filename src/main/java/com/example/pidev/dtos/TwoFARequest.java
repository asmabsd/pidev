// dto/TwoFARequest.java
package com.example.pidev.dtos;

import lombok.Data;

@Data
public class TwoFARequest {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    private String otpCode;
}
