package com.example.pidev.Controller.Guide;


import com.example.pidev.repository.GUIDE.GuideRepository;
import com.example.pidev.repository.Gastronomy.GastronomyRepository;
import com.example.pidev.repository.GestionSouvenir.StoreRepository;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.repository.activities.ActivityRepository;
import com.example.pidev.repository.activities.BlogRepository;
import com.example.pidev.repository.hebergement.HebergementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class statsall {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private HebergementRepository hebergementRepository;
    @Autowired
    private GastronomyRepository restaurantRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BlogRepository  blogRepository;





    //@Autowired
    //private TransportRepository transportRepository;

    @Autowired
    private GuideRepository guideRepository;

    @GetMapping("/users/count")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok(userRepository.count());
    }

    @GetMapping("/activities/count")
    public ResponseEntity<Long> getActivityCount() {
        return ResponseEntity.ok(activityRepository.count());
    }

    @GetMapping("/blogs/count")
    public ResponseEntity<Long> getBlogCount() {
        return ResponseEntity.ok(blogRepository.count());
    }

    @GetMapping("/restaurants/count")
    public ResponseEntity<Long> getRestaurantCount() {
        return ResponseEntity.ok(restaurantRepository.count());
    }


    /*@GetMapping("/transports/count")
    public ResponseEntity<Long> getRestaurantCount() {
        return ResponseEntity.ok(transportsRepository.count());
    }*/

    @GetMapping("/stores/count")
    public ResponseEntity<Long> getStoreCount() {
        return ResponseEntity.ok(storeRepository.count());
    }

    @GetMapping("/guides/count")
    public ResponseEntity<Long> getGuideCount() {
        return ResponseEntity.ok(guideRepository.count());
    }
}
