package com.example.pidev.Controller.Guide;

import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;



import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.service.GUIDE.IGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Guide")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin(origins = "http://localhost:4200/")
public class GuideController {
    @Autowired
    IGuideService GuideService;

    /*@PostMapping("/addGuide")
        //@PreAuthorize("hasRole('ADMIN')")

    Guide addGuide(@RequestBody Guide Guide) {
if (Guide.getImage()!= null){

        String base64Image = Guide.getImage().toString();  // Get the image from the Guide entity

        // Retirer le préfixe
        if (base64Image.startsWith("data:image/png;base64,")) {
            base64Image = base64Image.split(",")[1];  // Remove the prefix
        }

        // Decode the Base64 data into a byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Store the byte array in the entity
        Guide.setImage(imageBytes);}


        return GuideService.addGuide(Guide); // Save the guide
        // Save the guide
    }
    // @PreAuthorize("hasRole('ADMIN')")
    /*@PutMapping("/updateGuide")
    Guide updateGuide(@RequestBody Guide Guide){
        return  GuideService.updateGuide(Guide);
    }*/

    private String uploadDir = "C:/Users/asmab/Downloads/angular-userxguide-master/angular-userxguide-master/src/assets/frontend/images";

    @PostMapping("/addGuide")
    //@PreAuthorize("hasRole('ADMIN')")
    public Guide addGuide(@RequestBody Guide guide) {


        return GuideService.addGuide(guide);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/updateGuide/{id}")
    public Guide updateGuide(@PathVariable long id, @RequestBody Guide guide) {
        return GuideService.updateGuide(guide);
    }

    @GetMapping("/viewGuide")
    List<Guide> afficherGuide() {
        return GuideService.getAllGuide();


    }

    @GetMapping("/uploads/{imageName}")
    public ResponseEntity<ConfigurationSource.Resource> getImage(@PathVariable String imageName) {
        try {
            // Define the path to the image
            Path imagePath = Paths.get(uploadDir, imageName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Adjust this based on your image format (JPEG, PNG, etc.)
                        .body((ConfigurationSource.Resource) resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    //  @PreAuthorize("hasRole('ADMIN')")
    @CrossOrigin(origins = "http://localhost:4200")

   /* @DeleteMapping("/deleteGuide/{idGuide}")
    public void deleteGuide(@PathVariable int idGuide) {
        GuideService.deleteGuide(idGuide);
    }*/

    @DeleteMapping("/deleteGuide/{idGuide}")
    public ResponseEntity<String> deleteGuide(@PathVariable int idGuide) {
        try {
            GuideService.deleteGuide(idGuide);  // Try to delete the guide
            return ResponseEntity.ok("Guide deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete guide: " + e.getMessage());
        }
    }


    @GetMapping("/getOne/{idGuide}")

    public Guide getGuide(@PathVariable int idGuide) {
        return GuideService.getGuide(idGuide);
    }



    private final Path rootLocation = Paths.get("uploads");
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get("uploads").resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Ajustez selon le type
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(
            @PathVariable int id,
            @RequestParam("file") MultipartFile file) {
        try {
            // Save file to server
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));

            // Update guide with image path
            Guide guide = GuideService.getGuide(id);
            guide.setPhoto(filename);
            GuideService.updateGuide(guide);

            return ResponseEntity.ok(filename);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }

    /*@PostMapping("/{guideId}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable int guideId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
        }

        try {
            String imagePath = saveImageToServer(file, guideId); // Save image to server logic
            return ResponseEntity.ok(imagePath); // Return image path or URL
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }*/

    /*private String saveImageToServer(MultipartFile file, int guideId) throws IOException {
        // Save the image as a byte array
        byte[] imageBytes = file.getBytes();

        // Here, you would save the image bytes to the Guide entity in your database
        Guide guide = GuideService.getGuide(guideId);
        guide.setImage(imageBytes); // Save the image as a byte array

        GuideService.updateGuide(guide); // Update the guide in the database

        return "Image uploaded successfully!";
    }*/

   /* @GetMapping("/{idGuide}/image")
    public ResponseEntity<String> getImage(@PathVariable int idGuide) {
        Guide guide = GuideService.getGuide(idGuide);
        if (guide == null || guide.getImage() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No image found.");
        }
        String base64Image = Base64.getEncoder().encodeToString(guide.getImage()); // Convert image to Base64
        return ResponseEntity.ok(base64Image); // Return the image as Base64 string
    }*/



}