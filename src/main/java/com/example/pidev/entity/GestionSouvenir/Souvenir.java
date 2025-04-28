package com.example.pidev.entity.GestionSouvenir;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Souvenir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private CategorySouvenir category; // Ex: handicrafts, textiles, jewelry...
    private String image;

    @OneToMany(mappedBy = "souvenir", cascade = CascadeType.ALL)
    private List<CommandLine> commandLines;

    @ManyToOne
    private Store store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<CommandLine> getCommandLines() {
        return commandLines;
    }

    public void setCommandLines(List<CommandLine> commandLines) {
        this.commandLines = commandLines;
    }

    public CategorySouvenir getCategory() {
        return category;
    }

    public void setCategory(CategorySouvenir category) {
        this.category = category;
    }
}
