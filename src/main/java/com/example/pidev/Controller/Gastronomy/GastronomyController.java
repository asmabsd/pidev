package com.example.pidev.Controller.Gastronomy;
import okhttp3.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pidev.dtos.GastronomyDTO;
import com.example.pidev.Interface.Gastronomy.IGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.Gastronomy.GastronomyType;
import com.example.pidev.entity.Gastronomy.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import com.example.pidev.Interface.Gastronomy.IDetailGastronomyService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/gastronomy")
@CrossOrigin(origins = {
        "http://localhost:4200",
})
public class GastronomyController {

    @Autowired
    IGastronomyService gastronomyService;
    @Autowired
    IDetailGastronomyService detailGastronomyService;


    private final OkHttpClient client = new OkHttpClient();

    @PostMapping(value = "/addGastronomy", consumes = "multipart/form-data")
    public ResponseEntity<Gastronomy> addGastronomy(
            @RequestPart("gastronomy") Gastronomy gastronomy,
            @RequestPart("image") MultipartFile imageFile) {

        try {
            // Vérifier si le fichier image est bien reçu
            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Sauvegarde de l'image
            String fileName = imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("C:/uploads/" + fileName);

            // Créer le répertoire si nécessaire
            Files.createDirectories(uploadPath.getParent());

            // Transférer le fichier image
            imageFile.transferTo(uploadPath);

            // Lier le nom de l'image à l'objet gastronomy
            gastronomy.setImage(fileName);

            // Sauvegarder la gastronomie
            Gastronomy savedGastronomy = gastronomyService.addGastronomy(gastronomy);
            return ResponseEntity.ok(savedGastronomy);

        } catch (IOException e) {
            // Log l'erreur pour mieux diagnostiquer le problème
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            // Gérer d'autres types d'erreurs
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/addMenu")
    public ResponseEntity<Menu> addMenu(@RequestBody Menu menu) {
        Menu savedMenu = gastronomyService.addMenu(menu);
        return ResponseEntity.ok(savedMenu);
    }

    @PutMapping(value = "/updateGastronomy", consumes = "multipart/form-data")
    public ResponseEntity<Gastronomy> updateGastronomy(
            @RequestPart("gastronomy") Gastronomy gastronomy,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                // Check if the file is a valid image (basic check for .jpg/.png)
                if (!imageFile.getContentType().startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }

                String fileName = imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("C:/uploads/" + fileName);  // Consider changing to a dynamic path

                Files.createDirectories(uploadPath.getParent());
                imageFile.transferTo(uploadPath);

                gastronomy.setImage(fileName); // Store image file name
            }

            Gastronomy updatedGastronomy = gastronomyService.updateGastronomy(gastronomy);
            return ResponseEntity.ok(updatedGastronomy);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/retrieveAllGastronomies")
    public List<Gastronomy> retrieveAllGastronomies() {
        return gastronomyService.retrieveAllGastronomies();
    }

    @GetMapping("/retrieveGastronomy/{id}")
    public Gastronomy retrieveGastronomy(@PathVariable int id) {
        return gastronomyService.retrieveGastronomy(id);
    }

    @DeleteMapping("/deleteGastronomy/{id}")
    public void deleteGastronomy(@PathVariable int id) {
        gastronomyService.deleteGastronomy(id);
    }


    @PutMapping("/affectMenuToGastronomy/{idGastronomy}")
    public Gastronomy affectMenuToGastronomy(@PathVariable int idGastronomy, @RequestBody List<Integer> idMenus) {
        return gastronomyService.affectMenuToGastronomy(idGastronomy, idMenus);
    }


    @PostMapping("/addDetailGastronomyAndAffectGastronomy/{idGastronomy}")
    public ResponseEntity<DetailGastronomy> addDetailGastronomyAndAffectGastronomy(
            @PathVariable int idGastronomy, @RequestBody DetailGastronomy detailGastronomy) {

        DetailGastronomy savedDetail = gastronomyService.addDetailGastronomyAndAffectGastronomy(detailGastronomy, idGastronomy);

        if (savedDetail != null) {
            return ResponseEntity.ok(savedDetail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Gastronomy>> searchGastronomies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) GastronomyType type,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String plateKeyword
    ) {
        List<Gastronomy> result = gastronomyService.searchGastronomies(name, type, location, minRating, plateKeyword);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/generateAdvice")
    public ResponseEntity<String> generateAdvice(@RequestBody GastronomyDTO gastronomyDTO) {
        String description = "";
        if (gastronomyDTO.getDetailGastronomy() != null) {
            description = gastronomyDTO.getDetailGastronomy().getDescription();
        }

        // Construire le prompt pour Groq
        String userPrompt = "Donne-moi des conseils pour améliorer cette gastronomie afin d'obtenir la meilleure note possible : " + description;

        String requestBodyJson = "{\n" +
                "  \"model\": \"mistral-saba-24b\", \n" +   // Remplacer avec le modèle recommandé
                "  \"messages\": [\n" +
                "    {\"role\": \"system\", \"content\": \"Tu es un expert en gastronomie et en notation de restaurants.\"},\n" +
                "    {\"role\": \"user\", \"content\": \"" + userPrompt + "\"}\n" +
                "  ],\n" +
                "  \"max_tokens\": 300\n" +
                "}";


        // Remplacer l'URL et la clé API
        String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";
        String GROQ_API_KEY = "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"; // <-- remplace avec ta clé Groq !!

        okhttp3.RequestBody body = okhttp3.RequestBody.create(requestBodyJson, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(GROQ_API_URL)
                .header("Authorization", "Bearer " + GROQ_API_KEY)
                .post(body)
                .build();

        try {
            // Petite pause (optionnelle pour être safe)
            Thread.sleep(500);

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Response headers:");
                System.out.println(response.headers());

                if (!response.isSuccessful()) {
                    String errorBody = (response.body() != null) ? response.body().string() : "No error body";
                    System.err.println("Erreur API Groq: " + response.code() + " - " + errorBody);

                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Erreur API Groq: " + response.code() + " - " + errorBody);
                }

                String responseBody = response.body().string();
                return ResponseEntity.ok(responseBody);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur : interruption.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de la communication avec l'API Groq : " + e.getMessage());
        }
    }



}
