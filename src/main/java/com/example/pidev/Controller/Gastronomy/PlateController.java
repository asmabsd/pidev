package com.example.pidev.Controller.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IPlateService;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.Gastronomy.Plate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/plate")
@CrossOrigin(origins = {
        "http://localhost:4200",
})
public class PlateController {

    @Autowired
    IPlateService plateService;


    @PostMapping(value = "/addPlate", consumes = "multipart/form-data")
    public ResponseEntity<Plate> addPlate(
            @RequestPart("plate") Plate plate,
            @RequestPart("image") MultipartFile imageFile) {

        try {
            // Vérifier si le fichier image est bien reçu
            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Sauvegarde de l'image
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("C:/uploads/plates/" + fileName);
            Files.createDirectories(uploadPath.getParent()); // créer dossier si nécessaire
            imageFile.transferTo(uploadPath.toFile());

            // Associer le nom de l'image au Plate
            plate.setImagePlate(fileName);

            // Sauvegarder le Plate avec le service
            Plate savedPlate = plateService.addPlate(plate);
            return ResponseEntity.ok(savedPlate);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/updatePlate")
    public Plate updatePlate(@RequestBody Plate plate) {
        return plateService.updatePlate(plate);
    }

    @GetMapping("/getAllPlates")
    public List<Plate> getAllPlates() {
        return plateService.getAllPlates();
    }

    @GetMapping("/getPlate/{id}")
    public Plate getPlate(@PathVariable int id) {
        return plateService.getPlate(id);
    }

    @DeleteMapping("/deletePlate/{id}")
    public void deletePlate(@PathVariable int id) {
        plateService.deletePlate(id);
    }
    @GetMapping("/getPlatesByMenu/{menuId}")
    public List<Plate> getPlatesByMenu(@PathVariable int menuId) {
        return plateService.getPlatesByMenuId(menuId);
    }

}
