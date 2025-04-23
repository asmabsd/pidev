package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Discount;
import com.example.pidev.entity.GestionSouvenir.DiscountType;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.repository.GestionSouvenir.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class discountServiceImplement implements iDiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public double calculateDiscount(String code, Panel panel) {
        // Vérifier si une réduction existe déjà
        if (panel.getAppliedDiscountCode() != null) {
            throw new IllegalArgumentException("Une réduction est déjà appliquée (" + panel.getAppliedDiscountCode() + ")");
        }

        Discount discount = discountRepository.findByCodeAndActiveTrue(code)
                .orElseThrow(() -> new IllegalArgumentException("Code promo invalide"));

        // Validation de l'expiration
        if (discount.getExpiresAt() != null && discount.getExpiresAt().before(new Date())) {
            throw new IllegalArgumentException("Code expiré");
        }

        // Validation des conditions
        validateConditions(discount, panel);

        // Calcul selon le type
        return switch (discount.getType()) {
            case PERCENTAGE -> calculatePercentage(discount, panel);
            case FIXED -> calculateFixed(discount, panel);
            case BUNDLE -> calculateBundle(discount, panel);
        };
    }

    @Override
    public void validateConditions(Discount discount, Panel panel) {
        if (discount.getMinAmount() != null && panel.getTotal() < discount.getMinAmount()) {
            throw new IllegalArgumentException("Montant minimum non atteint");
        }

        if (discount.getMinItems() != null && panel.getTotalItems() < discount.getMinItems()) {
            throw new IllegalArgumentException("Nombre d'articles insuffisant");
        }

        if (discount.getType() == DiscountType.BUNDLE) {
            long count = panel.getCommandLines().stream()
                    .filter(cl -> cl.getSouvenir().getCategory()
                            .equalsIgnoreCase(discount.getApplicableCategory()))
                    .count();
            if (count < discount.getMinItems()) {
                throw new IllegalArgumentException("Pas assez d'articles dans la catégorie");
            }
        }
    }

    @Override
    public Discount validateAndGetDiscount(String code, Panel panel) {
        if (panel.getAppliedDiscountCode() != null) {
            throw new IllegalArgumentException("Une réduction est déjà appliquée");
        }

        Discount discount = discountRepository.findByCodeAndActiveTrue(code)
                .orElseThrow(() -> new IllegalArgumentException("Code promo invalide"));

        // Validation de l'expiration et des conditions...
        validateConditions(discount, panel);

        return discount;
    }
    @Override
    public double calculatePercentage(Discount discount, Panel panel) {
        double baseAmount = discount.getApplicableCategory() == null ?
                panel.getTotal() :
                panel.getCommandLines().stream()
                        .filter(cl -> cl.getSouvenir().getCategory()
                                .equalsIgnoreCase(discount.getApplicableCategory()))
                        .mapToDouble(cl -> cl.getUnitPrice() * cl.getQuantity())
                        .sum();

        return baseAmount * (discount.getValue() / 100);
    }
    public void removeDiscount(Panel panel) {
        panel.removeDiscount();
    }

    @Override
    public double calculateFixed(Discount discount, Panel panel) {
        return Math.min(discount.getValue(), panel.getTotal());
    }

    @Override
    public double calculateBundle(Discount discount, Panel panel) {
        long itemCount = panel.getCommandLines().stream()
                .filter(cl -> cl.getSouvenir().getCategory()
                        .equalsIgnoreCase(discount.getApplicableCategory()))
                .count();

        return (itemCount / discount.getMinItems()) * discount.getValue();
    }


    @Override
    public List<Discount> getActiveDiscounts() {
        return discountRepository.findActiveDiscounts();
    }
}
