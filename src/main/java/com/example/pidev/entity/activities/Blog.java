package com.example.pidev.entity.activities;

import com.example.pidev.entity.User.User;
import com.example.pidev.entity.activities.Region; // Import de l'énumération
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString

public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBlog;
    private String title;
    @Column(columnDefinition = "TEXT") // Use TEXT for larger content
    private String content;    private String publication;
    private LocalDateTime publicationDate;


    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
    private boolean published = false;

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }


    // Nouvel attribut region
    @Enumerated(EnumType.STRING) // Pour stocker la valeur sous forme de chaîne de caractères
    private Region region;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<Activity>();

    // Getter et Setter pour region
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    // Les autres getters et setters restent inchangés
    public Long getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(Long idBlog) {
        this.idBlog = idBlog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Activity> getActivities() {
        return new ArrayList<>(activities);
    }

    public void setActivities(List<Activity> activities) {
        // Remove this blog from old activities
        for (Activity activity : new ArrayList<>(this.activities)) {
            activity.setBlog(null);
        }

        this.activities.clear();

        if (activities != null) {
            // Add new activities and set this blog as their owner
            for (Activity activity : activities) {
                this.activities.add(activity);
                activity.setBlog(this);
            }
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "idBlog=" + idBlog +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publication='" + publication + '\'' +
                ", region=" + region +  // Ajout du champ region dans toString
                ", user=" + user +
                ", activities=" + activities +
                '}';
    }
}