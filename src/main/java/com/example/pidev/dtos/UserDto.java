package com.example.pidev.dtos;

import com.example.pidev.entity.User.User;


public class UserDto {
    private Long id; // ✅ Ajouter ça

    private String email;
    private String firstName;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String lastName;
    private String role;

    // Getters et setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public UserDto(User user) {
        this.id = user.getId(); // ❗ Ajoute cette ligne
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().getName();
    }

}