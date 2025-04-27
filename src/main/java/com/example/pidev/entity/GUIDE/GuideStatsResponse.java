package com.example.pidev.entity.GUIDE;


import java.util.Map;

public class GuideStatsResponse {
    private int totalGuides;
    private Map<String, Long> guidesByLanguage;
    private Map<String, Long> guidesBySpeciality;
    private Map<String, Double> averageRatingsByGuide;
    private Map<String, Long> reservationsByGuide;

    public int getTotalGuides() {
        return totalGuides;
    }

    public void setTotalGuides(int totalGuides) {
        this.totalGuides = totalGuides;
    }

    public Map<String, Long> getGuidesByLanguage() {
        return guidesByLanguage;
    }

    public void setGuidesByLanguage(Map<String, Long> guidesByLanguage) {
        this.guidesByLanguage = guidesByLanguage;
    }

    public Map<String, Long> getGuidesBySpeciality() {
        return guidesBySpeciality;
    }

    public void setGuidesBySpeciality(Map<String, Long> guidesBySpeciality) {
        this.guidesBySpeciality = guidesBySpeciality;
    }

    public Map<String, Double> getAverageRatingsByGuide() {
        return averageRatingsByGuide;
    }

    public void setAverageRatingsByGuide(Map<String, Double> averageRatingsByGuide) {
        this.averageRatingsByGuide = averageRatingsByGuide;
    }

    public Map<String, Long> getReservationsByGuide() {
        return reservationsByGuide;
    }

    public void setReservationsByGuide(Map<String, Long> reservationsByGuide) {
        this.reservationsByGuide = reservationsByGuide;
    }


    // Getters & Setters
}