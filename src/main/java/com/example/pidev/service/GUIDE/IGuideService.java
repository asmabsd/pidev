package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IGuideService {

    Guide addGuide(Guide Guide);
    Guide updateGuide(Guide Guide  );


        String getLanguageById(int idGuide);


    List<Guide> searchGuides(String name, String language, String experience, String speciality, int averageRating, String availability, String contact);

    Guide getGuidebycontact(String idGuide);

    void deleteGuide(int idGuide);
    List<Guide> getAllGuide();
    Guide getGuide(int idGuide);



}