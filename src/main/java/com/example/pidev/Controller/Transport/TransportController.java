package com.example.pidev.Controller.Transport;





import com.example.pidev.entity.Transport.Transport;
import com.example.pidev.service.Transport.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transport")
public class TransportController {
    @Autowired
    TransportService transportService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Transport API is running!";
    }

    @PostMapping("/add")
    public Transport addTransport(@RequestBody Transport t) {
        return transportService.addTransport(t);
    }

    @PutMapping("/modify")
    public Transport modifyTransport(@RequestBody Transport t) {
        return transportService.updateTransport(t);
    }

    @DeleteMapping("/remove/{id}")
    public void removeTransport(@PathVariable("id") Integer id) {
        transportService.deleteTransport(id);
    }
    /*  @GetMapping("/viewReservationGuide")
        List<ReservationGuide> afficherReservationGuide(){
            return  ReservationGuideService.getAllReservationGuide();


        }*/
    @GetMapping("/getall")
    public List<Transport> getAllTransports() {
        return transportService.getTransports();
    }

    @GetMapping("/get/{id}")
    public Transport getTransport(@PathVariable Integer id) {
        return transportService.getTransport(id);
    }
}