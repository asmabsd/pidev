package com.example.pidev.entity.GUIDE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "guide")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String language;
    private String speciality;
    private String experience;
    private Double averageRating;
    private String availability;
    private String phone ;
    private String contact;


    /*  @Lob
      @Column(columnDefinition = "LONGBLOB") // Pour MySQL / MariaDB
      private byte[] image;*/
   /* @ManyToMany(mappedBy = "guides")
    @JsonIgnore
    private Set<ReservationGuide> reservations = new HashSet<>();
*/
    @Column(name = "photo")
    private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getContact() {

      contact = contact.trim();
return  contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPhoto(String filename) {
        this.photo=filename;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


  /*  public Set<ReservationGuide> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationGuide> reservations) {
        this.reservations = reservations;
    }






}
*/
}