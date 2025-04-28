package com.example.pidev.entity.GUIDE;

import com.example.pidev.entity.User.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_guide")
public class ReservationGuide {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservation;



    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


   /* @ManyToMany
    @JoinTable(
            name = "reservation_guide_mapping",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "guide_id")
    )
  /*  @JsonIgnore
    private Set<Guide> guides = new HashSet<>();
*/


    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    private LocalDateTime dateHour;

    private String duration;
    private String status;
    private int price;
    private String comment;

    // Getters and Setters
    public Integer getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /* public Set<Guide> getGuides() {
         return guides;
     }

     public void setGuides(Set<Guide> guides) {
         this.guides = guides;
     }
 */
    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }




}