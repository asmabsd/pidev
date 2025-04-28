package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.repository.GUIDE.GuideRepository;
import com.example.pidev.repository.GUIDE.GuideReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideServiceImplement implements IGuideService {

    @Autowired
    GuideRepository GuideRepo ;
    @Autowired
    GuideReservationRepository guideReservationRepository;


    @Override
    public Guide addGuide(Guide Guide) {
        return GuideRepo.save(Guide);


    }



    public float calculateAverageRating(Guide guide) {
        List<ReservationGuide> reservations =guideReservationRepository.findByGuideAndStatus(guide, "accepted");

        if (reservations.isEmpty()) {
            return 0; // Aucune réservation acceptée, donc pas de note.
        }

        Double totalRating = 0.0;
        for (ReservationGuide reservation : reservations) {
            if (reservation.getGuide().getAverageRating() != null) {
                totalRating += reservation.getGuide().getAverageRating();
            }
        }

        return (float) (totalRating / reservations.size());
    }

    public void updateGuideRating(Guide guide) {
        double avgRating = calculateAverageRating(guide);
        guide.setAverageRating(avgRating);
        GuideRepo.save(guide);
    }


    @Override
    public String getLanguageById(int idGuide) {
        return GuideRepo.getLanguageById(idGuide);
    }

    @Override
    public Guide updateGuide(Guide Guide) {
        return GuideRepo.save(Guide);
    }




    @Override
    public List<Guide> searchGuides(String name, String language, String experience, String speciality, int averageRating, String availability, String contact  ) {
        // Build query dynamically based on the provided search parameters
        Specification<Guide> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (language != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("language"), "%" + language + "%"));
        }
        if (experience != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("experience"), experience));
        }
        if (speciality != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("speciality"), speciality));
        }
        if (averageRating != 0) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"), averageRating));
        }
        if (availability != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("availability"), "%" + availability + "%"));
        }
        if (contact!= null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("contact"), "%" + contact + "%"));
        }

        return GuideRepo.findAll(spec);  // Use the JPA repository to find all matching guides
    }



    @Override
    public Guide getGuidebycontact(String idGuide) {
        return GuideRepo.findByContact(idGuide) ;
    }

    @Override
    public void deleteGuide(int idGuide) {
        GuideRepo.deleteById(idGuide);
    }

    @Override
    public List<Guide> getAllGuide() {
        return GuideRepo.findAll();
    }

    @Override
    public Guide getGuide(int idGuide) {
        return GuideRepo.findById(idGuide).get();
    }
}