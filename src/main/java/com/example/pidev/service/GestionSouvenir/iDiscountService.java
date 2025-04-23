package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Discount;
import com.example.pidev.entity.GestionSouvenir.Panel;

import java.util.List;

public interface iDiscountService {
    double calculateDiscount(String code, Panel panel);
    void validateConditions(Discount discount, Panel panel);
    double calculatePercentage(Discount discount, Panel panel) ;
    double calculateFixed(Discount discount, Panel panel);
    double calculateBundle(Discount discount, Panel panel);

    Discount validateAndGetDiscount(String code, Panel panel);
    List<Discount> getActiveDiscounts();
    void removeDiscount(Panel panel);
}
