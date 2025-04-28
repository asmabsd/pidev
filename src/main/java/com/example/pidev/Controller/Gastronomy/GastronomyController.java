package com.example.pidev.Controller.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.Gastronomy.GastronomyType;
import com.example.pidev.entity.Gastronomy.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/gastronomy")
@CrossOrigin(origins = "http://localhost:4200")
//atteend juste
public class GastronomyController {

    @Autowired
    IGastronomyService gastronomyService;
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
                String fileName = imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("C:/uploads/" + fileName);

                Files.createDirectories(uploadPath.getParent());
                imageFile.transferTo(uploadPath);

                gastronomy.setImage(fileName);
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



}