package com.example.pidev.Controller.Gastronomy;

import com.example.pidev.dtos.RatingRequest;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.Interface.Gastronomy.IDetailGastronomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/gastronomy")
public class RatingController {

    @Autowired
    private IDetailGastronomyService detailGastronomyService;

    // Endpoint pour soumettre une note
    @PostMapping("/rating/{gastronomyId}")
    public ResponseEntity<DetailGastronomy> submitRating(
            @PathVariable int gastronomyId,
            @RequestBody RatingRequest ratingRequest) {

        DetailGastronomy updatedDetail = detailGastronomyService.updateRating(gastronomyId, ratingRequest.getRating());

        if (updatedDetail != null) {
            return ResponseEntity.ok(updatedDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
