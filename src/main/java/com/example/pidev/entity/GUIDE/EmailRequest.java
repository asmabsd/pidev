package com.example.pidev.entity.GUIDE;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public  class EmailRequest {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String guideEmail;
    private String userEmail;

    // Getter and setter methods
    public String getGuideEmail() {
        return guideEmail != null ? guideEmail.trim() : null;
    }

    public void setGuideEmail(String guideEmail) {
        this.guideEmail = guideEmail;
    }

    public String getUserEmail() {

        return userEmail != null ? userEmail.trim() : null;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }}