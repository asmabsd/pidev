package com.example.pidev.entity.GestionSouvenir;

import com.example.pidev.entity.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String description;
    private String phone;

    @OneToMany (cascade = CascadeType.ALL,mappedBy = "store")
    private Set<Souvenir> souvenirs;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Souvenir> getSouvenirs() {
        return souvenirs;
    }

    public void setSouvenirs(Set<Souvenir> souvenirs) {
        this.souvenirs = souvenirs;
    }
    @ManyToOne
    User user;
}
