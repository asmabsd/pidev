package com.example.pidev.Controller.activities;

import com.example.pidev.Interface.User.IUser;
import com.example.pidev.entity.User.User;
import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.Blog;
import com.example.pidev.entity.activities.CategoryA;
import com.example.pidev.entity.activities.Region;
import com.example.pidev.service.activities.IActivity;
import com.example.pidev.service.activities.IBlog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity")
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityController {

    @Autowired
    IActivity activityService;

    @Autowired
    IUser userService;

    @Autowired
    IBlog blogService;

    // Define the upload directory
    private final String UPLOAD_DIR = "uploads/";

    // Constructor to ensure upload directory exists
    public ActivityController() {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    @GetMapping("/getAllActivitiesss")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/getActivity/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id)
                .map(activity -> new ResponseEntity<>(activity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addactivity")
    public ResponseEntity<?> addActivity(@RequestBody Activity activity) {
        try {
            // Get user ID from the request
            Long userId = activity.getUser().getId();


            // Fetch the complete user with all fields including role
            User completeUser = userService.getUser(userId);
            if (completeUser == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            // Set the complete user on the activity
            activity.setUser(completeUser);

            // Save the activity with the complete user
            Activity savedActivity = activityService.saveActivity(activity);

            // Return a simplified response without full entity serialization
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedActivity.getIdActivity());
            response.put("name", savedActivity.getName());
            response.put("category", savedActivity.getCategoryA().toString());
            response.put("location", savedActivity.getLocation());
            response.put("price", savedActivity.getPrice());
            response.put("imagePath", savedActivity.getImagePath());
            response.put("userId", completeUser.getId());
            response.put("userEmail", completeUser.getEmail());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // New endpoint for uploading an image
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Generate a unique filename
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + filename);

            // Save the file
            Files.write(filePath, file.getBytes());

            // Return the path that should be saved to the database
            Map<String, String> response = new HashMap<>();
            response.put("imagePath", filename);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }

    // New endpoint for adding activity with image in one request
    @PostMapping(value = "/addActivityWithImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addActivityWithImage(
            @RequestParam("activityJson") String activityJson,
            @RequestParam("image") MultipartFile image) {
        try {
            // Convert JSON string to Activity object
            ObjectMapper mapper = new ObjectMapper();
            Activity activity = mapper.readValue(activityJson, Activity.class);

            // Get user ID from the request
            Long userId = activity.getUser().getId();

            // Fetch the complete user with all fields including role
            User completeUser = userService.getUser(userId);
            if (completeUser == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            // Set the complete user on the activity
            activity.setUser(completeUser);

            // Process and save the image
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.write(filePath, image.getBytes());

            // Set image path in activity
            activity.setImagePath(filename);

            // Save the activity with the complete user and image
            Activity savedActivity = activityService.saveActivity(activity);

            // Return a simplified response
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedActivity.getIdActivity());
            response.put("name", savedActivity.getName());
            response.put("category", savedActivity.getCategoryA().toString());
            response.put("location", savedActivity.getLocation());
            response.put("price", savedActivity.getPrice());
            response.put("imagePath", savedActivity.getImagePath());
            response.put("userId", completeUser.getId());

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process request: " + e.getMessage()));
        }
    }

    // Add activity with image and blog in one request
    @PostMapping(value = "/addActivityWithImageBlog", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addActivityWithImageBlog(
            @RequestParam("activityJson") String activityJson,
            @RequestParam("image") MultipartFile image,
            @RequestParam("blogId") Long blogId) {
        try {
            // Convert JSON string to Activity object
            ObjectMapper mapper = new ObjectMapper();
            Activity activity = mapper.readValue(activityJson, Activity.class);

            // Get user ID from the request
            Long userId = activity.getUser().getId();

            // Fetch the complete user with all fields including role
            User completeUser = userService.getUser(userId);
            if (completeUser == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            // Set the complete user on the activity
            activity.setUser(completeUser);

            // Process and save the image
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.write(filePath, image.getBytes());

            // Set image path in activity
            activity.setImagePath(filename);

            // Save the activity with blog association
            Activity savedActivity = activityService.saveActivity(activity, blogId);

            // Return a simplified response
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedActivity.getIdActivity());
            response.put("name", savedActivity.getName());
            response.put("category", savedActivity.getCategoryA().toString());
            response.put("location", savedActivity.getLocation());
            response.put("price", savedActivity.getPrice());
            response.put("imagePath", savedActivity.getImagePath());
            response.put("userId", completeUser.getId());
            response.put("blogId", blogId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process request: " + e.getMessage()));
        }
    }

    // Update activity with image
    @PutMapping(value = "/updateActivityWithImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateActivityWithImage(
            @RequestParam("activityJson") String activityJson,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Convert JSON string to Activity object
            ObjectMapper mapper = new ObjectMapper();
            Activity activity = mapper.readValue(activityJson, Activity.class);

            // Get user ID from the request
            Long userId = activity.getUser().getId();

            // Fetch the complete user with all fields including role
            User completeUser = userService.getUser(userId);
            if (completeUser == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            // Set the complete user on the activity
            activity.setUser(completeUser);

            // Process image if provided
            if (image != null && !image.isEmpty()) {
                // Delete old image if it exists
                if (activity.getImagePath() != null && !activity.getImagePath().isEmpty()) {
                    Path oldImagePath = Paths.get(UPLOAD_DIR + activity.getImagePath());
                    Files.deleteIfExists(oldImagePath);
                }

                // Save new image
                String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + filename);
                Files.write(filePath, image.getBytes());

                // Update image path
                activity.setImagePath(filename);
            }

            // Update activity
            Activity updatedActivity = activityService.updateActivity(activity);

            // Return a simplified response
            Map<String, Object> response = new HashMap<>();
            response.put("id", updatedActivity.getIdActivity());
            response.put("name", updatedActivity.getName());
            response.put("category", updatedActivity.getCategoryA().toString());
            response.put("location", updatedActivity.getLocation());
            response.put("price", updatedActivity.getPrice());
            response.put("imagePath", updatedActivity.getImagePath());
            response.put("userId", completeUser.getId());

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process request: " + e.getMessage()));
        }
    }

    @PutMapping("/updateactivity")
    public ResponseEntity<?> updateActivity(@RequestBody Activity activity) {
        try {
            // Get user ID from the request
            Long userId = activity.getUser().getId();

            // Fetch the complete user with all fields including role
            User completeUser = userService.getUser(userId);
            if (completeUser == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            // Set the complete user on the activity
            activity.setUser(completeUser);

            // Update the activity
            Activity updatedActivity = activityService.updateActivity(activity);

            // Return a simplified response
            Map<String, Object> response = new HashMap<>();
            response.put("id", updatedActivity.getIdActivity());
            response.put("name", updatedActivity.getName());
            response.put("category", updatedActivity.getCategoryA().toString());
            response.put("location", updatedActivity.getLocation());
            response.put("price", updatedActivity.getPrice());
            response.put("imagePath", updatedActivity.getImagePath());
            response.put("userId", completeUser.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
        try {
            // Get the activity first to retrieve its image path
            Activity activity = activityService.getActivityById(id).orElse(null);
            if (activity == null) {
                return ResponseEntity.notFound().build();
            }

            // Delete image file if it exists
            if (activity.getImagePath() != null && !activity.getImagePath().isEmpty()) {
                Path imagePath = Paths.get(UPLOAD_DIR + activity.getImagePath());
                Files.deleteIfExists(imagePath);
            }

            // Delete the activity
            activityService.deleteActivity(id);

            return ResponseEntity.ok(Map.of("message", "Activity deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete activity: " + e.getMessage()));
        }
    }

    // Endpoint to serve image files
    @GetMapping("/image/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR + filename);
            if (!Files.exists(imagePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageData = Files.readAllBytes(imagePath);
            return ResponseEntity.ok()
                    .contentType(determineMediaType(filename))
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve image: " + e.getMessage()));
        }
    }

    // Helper method to determine media type based on file extension
    private MediaType determineMediaType(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
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

    @PostMapping("/create/{blogId}")
    public ResponseEntity<?> createActivity(@RequestBody Activity activity, @PathVariable Long blogId) {
        try {
            // Vérifier d'abord si le blog existe
            Blog blog = blogService.getBlogById(blogId).get();
            if (blog == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Blog not found with ID: " + blogId));
            }

            // Récupérer la région du blog
            Region blogRegion = blog.getRegion();

            // Assigner la région du blog à l'activité
            activity.setRegion(blogRegion);

            // Get user ID from the request
            Long userId = activity.getUser().getId();

            // Fetch the complete user with all fields including role
            User completeUser = userService.getUser(userId);
            if (completeUser == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User not found with ID: " + userId));
            }

            // Set the complete user on the activity
            activity.setUser(completeUser);

            // Create activity with blog association
            Activity createdActivity = activityService.saveActivity(activity, blogId);

            // Return a simplified response
            Map<String, Object> response = new HashMap<>();
            response.put("id", createdActivity.getIdActivity());
            response.put("name", createdActivity.getName());
            response.put("category", createdActivity.getCategoryA().toString());
            response.put("location", createdActivity.getLocation());
            response.put("price", createdActivity.getPrice());
            response.put("imagePath", createdActivity.getImagePath());
            response.put("userId", completeUser.getId());
            response.put("blogId", blogId);
            response.put("region", createdActivity.getRegion().toString()); // Ajout de la région dans la réponse

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/regions")
    public ResponseEntity<List<Region>> getAllRegions() {
        return ResponseEntity.ok(Arrays.asList(Region.values()));
    }

    @GetMapping("/byRegionWithBlogs")
    public ResponseEntity<Map<String, Object>> getActivitiesAndBlogsByRegion(
            @RequestParam(required = false) Region region) {

        List<Activity> activities = region != null ?
                activityService.getActivitiesByRegion(region) :
                activityService.getAllActivities();

        List<Blog> blogs = region != null ?
                blogService.getBlogsByRegion(region) :
                blogService.getAllBlogs();

        Map<String, Object> response = new HashMap<>();
        response.put("activities", activities);
        response.put("blogs", blogs);

        return ResponseEntity.ok(response);
    }
    // Like/Dislike Endpoints

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeActivity(@PathVariable Long id) {
        try {
            System.out.println("Like activity ID: " + id);
            Activity updatedActivity = activityService.likeActivity(id);
            return ResponseEntity.ok(updatedActivity);
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFoundException in likeActivity: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Erreur dans likeActivity pour ID: " + id + ", Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Échec du like : " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<?> dislikeActivity(@PathVariable Long id) {
        try {
            System.out.println("Dislike activity ID: " + id);
            Activity updatedActivity = activityService.dislikeActivity(id);
            return ResponseEntity.ok(updatedActivity);
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFoundException in dislikeActivity: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Erreur dans dislikeActivity pour ID: " + id + ", Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Échec du dislike : " + e.getMessage()));
        }
    }

    @GetMapping("/{id}/reaction")
    public ResponseEntity<?> getUserReaction(@PathVariable Long id) {
        try {
            System.out.println("Get reaction for activity ID: " + id);
            String reaction = activityService.getUserReaction(id);
            return ResponseEntity.ok(Collections.singletonMap("reaction", reaction));
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFoundException in getUserReaction: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Erreur dans getUserReaction pour ID: " + id + ", Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors de la récupération de la réaction : " + e.getMessage()));
        }
    }
    @GetMapping("/recommended")
    public Activity getRecommendedActivity() {
        // Récupérer toutes les activités
        List<Activity> activities = activityService.getAllActivities();

        if (activities.isEmpty()) {
            return null;
        }

        // Calculer les statistiques par région
        Map<Region, Long> regionCounts = activities.stream()
                .collect(Collectors.groupingBy(Activity::getRegion, Collectors.counting()));

        // Trouver la région avec le moins d'activités
        Region minRegion = regionCounts.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (minRegion == null) {
            return null;
        }

        // Sélectionner une activité aléatoire dans cette région
        List<Activity> activitiesInRegion = activities.stream()
                .filter(activity -> activity.getRegion() == minRegion)
                .collect(Collectors.toList());

        if (activitiesInRegion.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return activitiesInRegion.get(random.nextInt(activitiesInRegion.size()));
    }
}