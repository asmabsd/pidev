package com.example.pidev.dtos;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String nTel;
    private boolean using2FA;

    public boolean isUsing2FA() {
        return using2FA;
    }

    public void setUsing2FA(boolean using2FA) {
        this.using2FA = using2FA;
    }

    // <--- add this


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getnTel() {
        return nTel;
    }

    public void setnTel(String nTel) {
        this.nTel = nTel;
    }

    public String getNumPasseport() {
        return numPasseport;
    }

    public void setNumPasseport(String numPasseport) {
        this.numPasseport = numPasseport;
    }

    private String numPasseport;
}