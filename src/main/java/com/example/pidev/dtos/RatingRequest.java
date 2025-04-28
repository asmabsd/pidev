package com.example.pidev.dtos;

public class RatingRequest {
    private double rating;

    public RatingRequest() {}

    public RatingRequest(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
