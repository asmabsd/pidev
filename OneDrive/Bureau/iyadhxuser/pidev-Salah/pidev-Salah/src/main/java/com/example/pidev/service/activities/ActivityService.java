package com.example.pidev.service.activities;

import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.CategoryA;
import com.example.pidev.repository.activities.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class ActivityService implements IActivity {
    @Autowired
    ActivityRepository activityRepository;

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> getActivityById(Long idActivity) {
        return activityRepository.findById(idActivity);
    }

    @Override
    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivity(Long idActivity) {
        activityRepository.deleteById(idActivity);


    }

    @Override
    public List<Activity> getActivitiesByPartnerId(Long partnerId) {
        return activityRepository.findByUserId(partnerId);
    }

    @Override
    public List<Activity> getActivitiesByCategory(CategoryA categoryA) {
        return activityRepository.findByCategoryA(categoryA);
    }

    @Override
    public List<Activity> getAvailableActivities(Boolean disponibility) {
        return activityRepository.findByDisponibility(disponibility);
    }

    @Override
    public List<Activity> getActivitiesByMaxPrice(Integer maxPrice) {
        return activityRepository.findByPriceLessThanEqual(maxPrice);
    }
}
