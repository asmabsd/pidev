package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;

import java.util.List;

public interface IGuideService {

    Guide addGuide(Guide Guide);
    Guide updateGuide(Guide Guide  );

    void deleteGuide(int idGuide);
    List<Guide> getAllGuide();
    Guide getGuide(int idGuide);
}
