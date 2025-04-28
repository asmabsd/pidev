package com.example.pidev.entity.GestionSouvenir;

public enum CategorySouvenir {
    HANDICRAFTS,
    TEXTILES,
    JEWERLY;

    public boolean equalsIgnoreCase(String applicableCategory) {
        return this.name().equalsIgnoreCase(applicableCategory);
    }
}
