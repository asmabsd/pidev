package com.example.pidev.entity.activities;

import com.example.pidev.entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idActivity;
    private  String name;

    @Enumerated(EnumType.STRING)
    private CategoryA categoryA;

    private String imagePath;

    private String location;

    private Boolean disponibility;

    private Integer price;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    private LocalDateTime startDate;


    private Region region;
    private int likes = 0;
    private int dislikes = 0;

    private boolean active = false; // Faux au départ, deviendra vrai au moment voulu


    @ManyToOne

    @JoinColumn(name = "id_user" , nullable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference

    private List<Reservation> reservations;



    public Long getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(Long idActivity) {
        this.idActivity = idActivity;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for categoryA
    public CategoryA getCategoryA() {
        return categoryA;
    }

    public void setCategoryA(CategoryA categoryA) {
        this.categoryA = categoryA;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Getter and Setter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and Setter for disponibility
    public Boolean getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(Boolean disponibility) {
        this.disponibility = disponibility;
    }

    // Getter and Setter for price
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    // Getter et Setter pour user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter et Setter pour blog
    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    // New getter and setter for imagePath
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
