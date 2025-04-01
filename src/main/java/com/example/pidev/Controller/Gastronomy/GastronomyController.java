package com.example.pidev.Controller.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gastronomy")
public class GastronomyController {

    @Autowired
    IGastronomyService gastronomyService;
    @PostMapping("/addGastronomy")
    public Gastronomy addGastronomy(@RequestBody Gastronomy gastronomy) {
        return gastronomyService.addGastronomy(gastronomy);
    }

    @PutMapping("/updateGastronomy")
    public Gastronomy updateGastronomy(@RequestBody Gastronomy gastronomy) {
        return gastronomyService.updateGastronomy(gastronomy);
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
}
