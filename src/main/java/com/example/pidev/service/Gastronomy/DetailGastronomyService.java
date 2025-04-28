package com.example.pidev.service.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IDetailGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.repository.Gastronomy.DetailGastronomyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DetailGastronomyService implements IDetailGastronomyService {
    @Autowired
    DetailGastronomyRepository detailGastronomyRepository;

    @Override
    public DetailGastronomy addDetailGastronomy(DetailGastronomy detailGastronomy) {
        return detailGastronomyRepository.save(detailGastronomy);
    }

    @Override
    public DetailGastronomy updateDetailGastronomy(DetailGastronomy detailGastronomy) {
        return detailGastronomyRepository.save(detailGastronomy);
    }

    @Override
    public void deleteDetailGastronomy(int id) {
        detailGastronomyRepository.deleteById(id);
    }

    @Override
    public List<DetailGastronomy> retrieveAllDetailGastronomies() {
        return detailGastronomyRepository.findAll();
    }

    @Override
    public DetailGastronomy retrieveDetailGastronomy(int id) {
        return detailGastronomyRepository.findById(id).orElse(null);
    }
    @Override
    public DetailGastronomy retrieveDetailGastronomyByGastronomyId(int gastronomyId) {
        return detailGastronomyRepository.findByGastronomyId(gastronomyId);
    }
    @Override
    public DetailGastronomy updateRating(int gastronomyId, double rating) {
        // Chercher la gastronomy via l'ID
        DetailGastronomy detailGastronomy = detailGastronomyRepository.findByGastronomyId(gastronomyId);

        if (detailGastronomy != null) {
            // Mettre à jour la note
            detailGastronomy.setRating(rating);

            // Sauvegarder l'entité mise à jour
            return detailGastronomyRepository.save(detailGastronomy);
        }

        return null;
    }
}
