package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.repository.GUIDE.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideServiceImplement implements IGuideService {

    @Autowired
    GuideRepository GuideRepo ;


    @Override
    public Guide addGuide(Guide Guide) {
        return GuideRepo.save(Guide);


    }

    @Override
    public Guide updateGuide(Guide Guide) {
        return GuideRepo.save(Guide);
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