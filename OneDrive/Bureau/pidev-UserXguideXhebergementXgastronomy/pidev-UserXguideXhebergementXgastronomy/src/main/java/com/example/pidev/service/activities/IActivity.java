package com.example.pidev.service.activities;

import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.CategoryA;

import java.util.List;
import java.util.Optional;

public interface IActivity {
    List<Activity> getAllActivities();

    Optional<Activity> getActivityById(Long idActivity);

    Activity saveActivity(Activity activity);
    Activity updateActivity(Activity activity);

    void deleteActivity(Long idActivity);


    List<Activity> getActivitiesByPartnerId(Long partnerId);

    List<Activity> getActivitiesByCategory(CategoryA categoryA);
    List<Activity> getAvailableActivities(Boolean disponibility);
    List<Activity> getActivitiesByMaxPrice(Integer maxPrice);
}
