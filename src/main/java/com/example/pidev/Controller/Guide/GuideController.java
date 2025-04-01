package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.service.GUIDE.IGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/Guide")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin("http://localhost:4200/dashbaord/addGuide")

public class GuideController {
    @Autowired
    IGuideService GuideService;
    @PostMapping("/addGuide")
        //@PreAuthorize("hasRole('ADMIN')")

    Guide addGuide(@RequestBody Guide Guide){
        return  GuideService.addGuide(Guide);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateGuide")
    Guide updateGuide(@RequestBody Guide Guide){
        return  GuideService.updateGuide(Guide);
    }
    @GetMapping("/viewGuide")
    List<Guide> afficherGuide(){
        return  GuideService.getAllGuide();


    }
    //  @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteGuide/{idGuide}")
    public void deleteGuide(@PathVariable int idGuide) {
        GuideService.deleteGuide(idGuide);
    }


    @GetMapping("/getOne/{idGuide}")

    public Guide getGuide(@PathVariable int idGuide) {
        return GuideService.getGuide(idGuide);
    }

}
