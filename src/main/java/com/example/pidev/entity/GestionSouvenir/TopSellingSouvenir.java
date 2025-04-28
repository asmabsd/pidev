package com.example.pidev.entity.GestionSouvenir;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopSellingSouvenir {
    private Souvenir souvenir;
    private long totalQuantity;
    private double totalRevenue;

    public TopSellingSouvenir(Souvenir souvenir, long totalQuantity, double totalRevenue) {
        this.souvenir = souvenir;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
    }

    public Souvenir getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(Souvenir souvenir) {
        this.souvenir = souvenir;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
