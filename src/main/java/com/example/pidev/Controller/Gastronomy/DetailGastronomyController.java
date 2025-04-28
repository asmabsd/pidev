package com.example.pidev.Controller.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IDetailGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detailGastronomy")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:4200",
})
public class DetailGastronomyController {

    @Autowired
    IDetailGastronomyService detailGastronomyService;
    @PostMapping("/addDetailGastronomy")
    public DetailGastronomy addDetailGastronomy(@RequestBody DetailGastronomy detailGastronomy) {
        return detailGastronomyService.addDetailGastronomy(detailGastronomy);
    }

    @PutMapping("/updateDetailGastronomy")
    public DetailGastronomy updateDetailGastronomy(@RequestBody DetailGastronomy detailGastronomy) {
        return detailGastronomyService.updateDetailGastronomy(detailGastronomy);
    }

    @GetMapping("/retrieveAllDetailGastronomies")
    public List<DetailGastronomy> retrieveAllDetailGastronomies() {
        return detailGastronomyService.retrieveAllDetailGastronomies();
    }

    @GetMapping("/retrieveDetailGastronomy/{id}")
    public DetailGastronomy retrieveDetailGastronomy(@PathVariable int id) {
        return detailGastronomyService.retrieveDetailGastronomy(id);
    }

    @DeleteMapping("/deleteDetailGastronomy/{id}")
    public void deleteDetailGastronomy(@PathVariable int id) {
        detailGastronomyService.deleteDetailGastronomy(id);
    }

    @GetMapping("/getDetailGastronomy/{gastronomyId}")
    public DetailGastronomy getDetailsByGastronomyId(@PathVariable int gastronomyId) {
        return detailGastronomyService.retrieveDetailGastronomyByGastronomyId(gastronomyId);
    }
}
