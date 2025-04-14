package com.example.pidev.entity.GestionSouvenir;

import com.example.pidev.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(unique = true) // Ensure name is unique
    private String name;
    private String address;
    private String description;
    @Column(unique = true) // Ensure phone is unique
    private String phone;
    @Enumerated(EnumType.STRING)
    private StoreStatus status= StoreStatus.LOADING;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    @JsonIgnore
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

    public StoreStatus getStatus() {
        return status;
    }

    public void setStatus(StoreStatus status) {
        this.status = status;
    }
}
