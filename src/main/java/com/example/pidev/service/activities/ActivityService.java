package com.example.pidev.service.activities;

import com.example.pidev.entity.User.User;
import com.example.pidev.entity.activities.*;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.repository.activities.ActivityReactionRepository;
import com.example.pidev.repository.activities.ActivityRepository;
import com.example.pidev.repository.activities.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service

public class ActivityService implements IActivity {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    ActivityReactionRepository activityReactionRepository;
    @Autowired
    UserRepository userRepository;

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

    @Override
    public Activity saveActivity(Activity activity, Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        // Propagation de la région de l'activité vers le blog
        if (activity.getRegion() != null) {
            blog.setRegion(activity.getRegion());
            blogRepository.save(blog);
        }

        activity.setBlog(blog);
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> getActivitiesByRegion(Region region) {
        return activityRepository.findByRegion(region);
    }

    @Scheduled(fixedRate = 60000) // Toutes les 60 secondes
    public void activateScheduledActivities() {
        List<Activity> scheduledActivities = activityRepository
                .findByStartDateBeforeAndActiveFalse(LocalDateTime.now());

        for (Activity activity : scheduledActivities) {
            activity.setActive(true);
            activity.setDisponibility(true); // ou autre logique métier
            activityRepository.save(activity);
            System.out.println("Activité activée : " + activity.getName());
        }
    }

    @Transactional
    @Override
    public Activity likeActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with ID: " + activityId));

        activity.setLikes(activity.getLikes() + 1);
        return activityRepository.save(activity);
    }

    @Transactional
    @Override
    public Activity dislikeActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with ID: " + activityId));

        activity.setDislikes(activity.getDislikes() + 1);
        return activityRepository.save(activity);
    }

    @Override
    public String getUserReaction(Long activityId) {
        // Puisque nous n'associons plus les réactions à un utilisateur,
        // nous pouvons retourner null ou une valeur par défaut
        return null;
    }


}
