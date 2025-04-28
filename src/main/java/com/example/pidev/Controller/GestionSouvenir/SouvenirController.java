package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.service.GestionSouvenir.iSouvenirService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/souvenir")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin("http://localhost:4200")
public class SouvenirController {
    @Autowired
    iSouvenirService souvenirService;

    @PostMapping("/addSouvenir")
    Souvenir addSouvenir(@RequestBody Souvenir souvenir) {
        souvenir.updateStatus();
        return souvenirService.addSouvenir(souvenir);
    }

    @PutMapping("/updateSouvenir")
    Souvenir souvenirUpdate(@RequestBody Souvenir souvenir) {
        souvenir.updateStatus();
        return souvenirService.updateSouvenir(souvenir);
    }

    @DeleteMapping("/deleteSouvenir")
    void souvenirDelete(@RequestParam long id) {
        souvenirService.deleteSouvenir(id);
    }

    @GetMapping("/retrieveAllSouvenir")
    List<Souvenir> retrieveAllSouvenir() {
        return souvenirService.retrieveAllSouvenir();
    }

    @GetMapping("/retrieveSouvenir/{id}")
    Souvenir retrieveSouvenir(@PathVariable Long id) {
        return souvenirService.retrieveSouvenir(id);
    }
    @GetMapping("/store/{storeId}")
    public List<Souvenir> getSouvenirsByStore(@PathVariable Long storeId) {
        return souvenirService.retrieveSouvenirsByStoreId(storeId);
    }
    private String uploadDir = "C:/Users/manso/pidev-final2/uploads";

    @GetMapping("/uploads/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            // Définir le chemin vers l'image
            Path imagePath = Paths.get(uploadDir, imageName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Utiliser MediaType dynamique selon le type de fichier
                MediaType mediaType = Files.probeContentType(imagePath) != null
                        ? MediaType.parseMediaType(Files.probeContentType(imagePath))
                        : MediaType.IMAGE_JPEG;  // Si le type MIME ne peut pas être détecté, utiliser par défaut JPEG

                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
            @PathVariable long id,
            @RequestParam("file") MultipartFile file) {
        try {
            // Vérifier si le fichier est bien présent
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier est vide.");
            }

            // Enregistrer le fichier sur le serveur avec un nom unique
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetLocation = this.rootLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation);

            // Récupérer le souvenir à mettre à jour
            Souvenir souvenir = souvenirService.retrieveSouvenir(id);
            if (souvenir == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Souvenir non trouvé.");
            }

            // Mettre à jour le souvenir avec le chemin du fichier
            souvenir.setPhoto(filename);
            souvenirService.updateSouvenir(souvenir);

            // Retourner le nom du fichier
            return ResponseEntity.ok(filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'upload de l'image : " + e.getMessage());
        }
    }










}


