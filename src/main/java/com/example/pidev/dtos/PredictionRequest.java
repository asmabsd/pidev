package com.example.pidev.dtos;

public class PredictionRequest {
    private String location;
    private double rating;

    public PredictionRequest() {
    }

    public PredictionRequest(String location, double rating) {
        this.location = location;
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "PredictionRequest{" +
                "location='" + location + '\'' +
                ", rating=" + rating +
                '}';
    }
}
