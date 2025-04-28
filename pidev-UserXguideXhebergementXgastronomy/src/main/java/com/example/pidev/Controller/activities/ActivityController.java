package com.example.pidev.Controller.activities;

import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.CategoryA;
import com.example.pidev.service.activities.IActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    IActivity activityService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return new ResponseEntity<>(activityService.getAllActivities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id)
                .map(activity -> new ResponseEntity<>(activity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        return new ResponseEntity<>(activityService.saveActivity(activity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        return activityService.getActivityById(id)
                .map(existingActivity -> {
                    activity.setIdActivity(id);
                    return new ResponseEntity<>(activityService.updateActivity(activity), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public void  deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
    }

    @GetMapping("/partner/{partnerId}")
    public ResponseEntity<List<Activity>> getActivitiesByPartnerId(@PathVariable Long partnerId) {
        return new ResponseEntity<>(activityService.getActivitiesByPartnerId(partnerId), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Activity>> getActivitiesByCategory(@PathVariable CategoryA category) {
        return new ResponseEntity<>(activityService.getActivitiesByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/available/{disponibility}")
    public ResponseEntity<List<Activity>> getAvailableActivities(@PathVariable Boolean disponibility) {
        return new ResponseEntity<>(activityService.getAvailableActivities(disponibility), HttpStatus.OK);
    }

    @GetMapping("/price/{maxPrice}")
    public ResponseEntity<List<Activity>> getActivitiesByMaxPrice(@PathVariable Integer maxPrice) {
        return new ResponseEntity<>(activityService.getActivitiesByMaxPrice(maxPrice), HttpStatus.OK);
    }
}
