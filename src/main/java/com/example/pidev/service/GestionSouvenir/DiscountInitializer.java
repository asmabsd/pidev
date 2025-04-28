package com.example.pidev.service.GestionSouvenir;

//import com.example.pidev.entity.GestionSouvenir.CategorySouvenir;
//import com.example.pidev.entity.GestionSouvenir.Discount;
//import com.example.pidev.entity.GestionSouvenir.DiscountType;
//import com.example.pidev.repository.GestionSouvenir.DiscountRepository;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DiscountInitializer  {

//    private final DiscountRepository discountRepository;
//
//    public DiscountInitializer(DiscountRepository discountRepository) {
//        this.discountRepository = discountRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        createDiscounts();
//    }
//
//    private void createDiscounts() {
//        createDiscount(
//                "ETE20",
//                DiscountType.PERCENTAGE,
//                20,
//                100.0,
//                null,
//                "HANDICRAFTS",
//                LocalDate.of(2024, 8, 31)
//        );
//
//        createDiscount(
//                "BIENVENUE10",
//                DiscountType.FIXED,
//                10,
//                null,
//                1,
//                "HANDICRAFTS",
//                null
//        );
//
//        createDiscount(
//                "3HANDICRAFTS",
//                DiscountType.BUNDLE,
//                15,
//                null,
//                3,
//                "HANDICRAFTS",
//                null
//        );
//
//        createDiscount(
//                "SOLDE30",
//                DiscountType.PERCENTAGE,
//                30,
//                null,
//                null,
//                "HANDICRAFTS",
//                LocalDate.now().plusDays(7)
//        );
//
//        createDiscount(
//                "OLDCODE",
//                DiscountType.FIXED,
//                5,
//                null,
//                null,
//                "HANDICRAFTS",
//                LocalDate.of(2023, 12, 31)
//        );
//    }
//
//    private void createDiscount(String code, DiscountType type, double value,
//                                Double minAmount, Integer minItems,
//                                String category, LocalDate expires) {
//
//            Discount discount = new Discount();
//            discount.setCode(code);
//            discount.setType(type);
//            discount.setValue(value);
//            discount.setMinAmount(minAmount);
//            discount.setMinItems(minItems);
//            discount.setApplicableCategory(category);
//            discount.setActive(true);
//
//            if (expires != null) {
//                discount.setExpiresAt(Date.from(expires.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//            }
//
//            discountRepository.save(discount);
//
//    }
}