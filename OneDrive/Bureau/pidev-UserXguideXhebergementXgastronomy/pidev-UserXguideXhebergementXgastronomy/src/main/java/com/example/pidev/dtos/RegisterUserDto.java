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
    private String numPasseport;

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getNTel() {
        return nTel;
    }

    public String getNumPasseport() {
        return numPasseport;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setnTel(String nTel) {
        this.nTel = nTel;
    }

    public void setNumPasseport(String numPasseport) {
        this.numPasseport = numPasseport;
    }
}