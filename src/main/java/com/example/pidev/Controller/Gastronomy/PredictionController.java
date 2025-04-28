package com.example.pidev.Controller.Gastronomy;

import com.example.pidev.dtos.PredictionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes Angular
public class PredictionController {

    @PostMapping("/predict")
    public ResponseEntity<String> predict(@RequestBody PredictionRequest request) {
        try {
            // Lancer le script Python depuis l’environnement virtuel
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\halae\\Downloads\\angularpi\\gastronomy-ai\\env\\Scripts\\python.exe",
                    "C:\\Users\\halae\\Downloads\\angularpi\\gastronomy-ai\\predict.py",
                    request.getLocation(),
                    String.valueOf(request.getRating())
            );

            pb.redirectErrorStream(true); // redirige la sortie d'erreur vers la sortie standard
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String prediction = null;

            while ((line = reader.readLine()) != null) {
                System.out.println("🧠 Sortie Python: " + line);
                prediction = line.trim(); // On suppose que le script retourne uniquement le type, ex: FAST_FOOD
            }

            if (prediction == null || prediction.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("⚠️ Aucune prédiction trouvée dans la sortie du script.");
            }

            return ResponseEntity.ok(prediction);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur de prédiction : " + e.getMessage());
        }
    }
}
