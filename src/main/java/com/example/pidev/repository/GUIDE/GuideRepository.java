package com.example.pidev.repository.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface GuideRepository extends JpaRepository<Guide,Integer> , JpaSpecificationExecutor<Guide> {
    Guide findByContact(String contact);
Guide findByPhone(String phone);

    @Query("SELECT g.language FROM Guide g WHERE g.id = :idGuide")
    String getLanguageById(@Param("idGuide") int idGuide);



}