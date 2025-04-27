package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.Planning;
import com.example.pidev.repository.GUIDE.GuideRepository;
import com.example.pidev.repository.GUIDE.PlanningRepository;
import com.example.pidev.service.GUIDE.IPlanningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Planning")
@CrossOrigin(origins = "http://localhost:4200/")

public class PlanningController {

    @Autowired
    IPlanningService PlanningService;
    @PostMapping("/addPlanning")
    Planning addPlanning(@RequestBody Planning Planning){
        return  PlanningService.addPlanning(Planning);
    }


    @PutMapping("/updateGuide")
    Planning updatePlanning(@RequestBody Planning Planning){
        return  PlanningService.updatePlanning(Planning);
    }
    @GetMapping("/viewPlanning")
    List<Planning> afficherPlanning(){
        return  PlanningService.getAllPlanning();


    }
    @DeleteMapping("/deletePlanning/{idPlanning}")
    public void deletePlanning(@PathVariable int idPlanning) {
        PlanningService.deletePlanning(idPlanning);
    }

    @GetMapping("/getAllByguide/{idGuide}")
    public List<Planning> getPlanningByGuide(@PathVariable int idGuide) {
        return  PlanningService.getPlanningByGuide(idGuide);
    }

    @GetMapping("/getByguide/{id}")
    public ResponseEntity<List<Planning>> getAllByGuide(@PathVariable int id) {
        List<Planning> plannings = planningRepository.findAllByGuideId(id);
        return ResponseEntity.ok(plannings);
    }




        private static final Logger logger = LoggerFactory.getLogger(GuideController.class);



      /*  @GetMapping("/isReserved")
        public ResponseEntity<Boolean> isGuideReserved(
                @RequestParam int guideId,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
            try {
                // Validate guideId
                if (guideId <= 0) {
                    logger.warn("Invalid guideId: {}", guideId);
                    return ResponseEntity.badRequest().body(false);
                }

                // Check if guide exists
                Optional<Guide> guide = guideRepository.findById(guideId);
                if (guide.isEmpty()) {
                    logger.warn("Guide not found for ID: {}", guideId);
                    return ResponseEntity.badRequest().body(false);
                }

                // Query planning for guide and date
                Optional<Planning> planning = planningRepository.findByGuideAndDate(guide.get(), date);
                boolean isReserved = planning.map(Planning::isReserved).orElse(false);

                return ResponseEntity.ok(isReserved);

            } catch (IllegalArgumentException e) {
                // Handle invalid input (e.g., date parsing issues)
                logger.error("Invalid input for guideId: {} or date: {}", guideId, date, e);
                return ResponseEntity.badRequest().body(false);
            } catch (Exception e) {
                // Log unexpected errors with details
                logger.error("Error checking reservation for guideId: {} and date: {}", guideId, date, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(false);
            }
        }
*/


    @GetMapping("/check-reservation")
    public ResponseEntity<Boolean> checkReservation(
            @RequestParam int guideId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        boolean isReserved = PlanningService.isPlanningReserved(guideId, date);
        return ResponseEntity.ok(isReserved);
    }


   /* @GetMapping("/isReserved")
    public ResponseEntity<Boolean> isGuideReserved(
            @RequestParam int guideId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        Optional<Guide> guideOptional = guideRepository.findById(guideId);
        if (guideOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Guide guide = guideOptional.get();

        // Find a planning entry with matching guide and date
        Optional<Planning> planningOptional = planningRepository.findByGuideAndDate(guide, date);

        // Return true only if planning exists and isReserved is true
        boolean isReserved = planningOptional.map(Planning::isReserved).orElse(false);

        return ResponseEntity.ok(isReserved);
    }

*/
  /*  @GetMapping("/isReserved")
    public ResponseEntity<Boolean> isGuideReserved(
            @RequestParam int guideId,
            @RequestParam String date) {

        try {
            System.out.println("🔍 Checking reservation for guideId: " + guideId + ", date: " + date);

            Optional<Guide> guideOptional = guideRepository.findById(guideId);
            if (guideOptional.isEmpty()) {
                System.out.println("❌ Guide non trouvé");
                return ResponseEntity.badRequest().body(false);
            }

            Guide guide = guideOptional.get();

            LocalDateTime parsedDate = LocalDateTime.parse(date);


            // Plage d'une minute autour de la date
            LocalDateTime start = parsedDate.withSecond(0).withNano(0);
            LocalDateTime end = start.plusMinutes(1);

            System.out.println("🕓 Searching between: " + start + " and " + end);

            // Recherche via la nouvelle méthode du repository
            Optional<Planning> planningOptional = planningRepository.findPlanningInDateRange(guide, start, end);

            boolean isReserved = planningOptional.map(Planning::isReserved).orElse(false);

            System.out.println("📦 Planning trouvé : " + planningOptional.isPresent() + ", réservé ? " + isReserved);

            return ResponseEntity.ok(isReserved);

        } catch (Exception e) {
            e.printStackTrace(); // pour debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

*/



    @GetMapping("/getOne/{idPlanning}")
    public Planning getPlanning(@PathVariable int idPlanning) {
        return PlanningService.getPlanning(idPlanning);
    }


   /* @GetMapping("/isReserved")
    public ResponseEntity<Boolean> isGuideReserved(
            @RequestParam int guideId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        Optional<Guide> guide = guideRepository.findById(guideId);
        if (guide.isEmpty()) return ResponseEntity.badRequest().body(false);

        LocalDateTime start = date.minusMinutes(30);
        LocalDateTime end = date.plusMinutes(30); // Intervalle de tolérance

        Optional<Planning> planning = planningRepository.findByGuideAndDate(guide.get(), start);

        return ResponseEntity.ok(planning.map(Planning::isReserved).orElse(false));
    }*/


    @Autowired
    GuideRepository guideRepository;
    @Autowired
    PlanningRepository planningRepository;
    @PostMapping(value = "/addguidetoProject/{idGuide}")
    public Planning addguidetoPlanning(@RequestBody Planning planning, @PathVariable int idGuide) {
        // Vérifier si le guide existe
        Guide guide = guideRepository.findById(idGuide)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        // Associer le guide au planning
        planning.setGuide(guide);

        // Sauvegarder le planning mis à jour
        return planningRepository.save(planning);
    }


    @PostMapping("/addReservedPlanning")
    public ResponseEntity<String> addReservedPlanning(@RequestParam int guideId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        Optional<Guide> guideOpt = guideRepository.findById(guideId);
        if (guideOpt.isEmpty()) return ResponseEntity.badRequest().body("Guide non trouvé");

        Guide guide = guideOpt.get();

        // Vérifie si planning existe déjà
        Optional<Planning> existing = planningRepository.findByGuideAndDate(guide, date);
        if (existing.isPresent()) {
            Planning p = existing.get();
            p.setReserved(true);
            planningRepository.save(p);
            return ResponseEntity.ok("Planning mis à jour comme réservé");
        }

        // Crée un nouveau planning
        Planning planning = new Planning();
        planning.setGuide(guide);
        planning.setDate(date); // Tu peux adapter Planning à LocalDateTime si tu veux l'heure aussi
        planning.setReserved(true);
        planningRepository.save(planning);

        return ResponseEntity.ok("Planning ajouté comme réservé");
    }




}