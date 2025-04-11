package com.example.pidev.entity.activities;

import com.example.pidev.entity.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String location;

    private Boolean disponibility;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "id_user" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;




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

}
