package com.example.pidev.entity.User;

import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.GestionSouvenir.Store;
import com.example.pidev.entity.activities.Blog;
import com.example.pidev.entity.hebergement.Hebergement;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{8}$", message = "Le numéro de téléphone doit contenir exactement 8 chiffres")
    @Column(nullable = true)
    private String nTel;

    @NotBlank(message = "Le numéro de passeport est obligatoire")
    @Pattern(regexp = "^[A-Za-z0-9]{9}$", message = "Le numéro de passeport doit contenir exactement 9 caractères alphanumériques")
    @Column(nullable = true)
    private String numPasseport;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    private Role role;

    @Column(name = "auth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider = AuthProvider.LOCAL;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getnTel() {
        return nTel;
    }

    public String getNumPasseport() {
        return numPasseport;
    }

    public Role getRole() {
        return role;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public Set<Hebergement> getHebergements() {
        return Hebergements;
    }

    public Set<Gastronomy> getGastronomy() {
        return Gastronomy;
    }

    public Set<Store> getStore() {
        return Store;
    }

    public Set<ReservationGuide> getReservationguide() {
        return reservationguide;
    }

    public Set<Blog> getBlog() {
        return Blog;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBlog(Set<Blog> blog) {
        Blog = blog;
    }

    public void setReservationguide(Set<ReservationGuide> reservationguide) {
        this.reservationguide = reservationguide;
    }

    public void setStore(Set<Store> store) {
        Store = store;
    }

    public void setGastronomy(Set<Gastronomy> gastronomy) {
        Gastronomy = gastronomy;
    }

    public void setHebergements(Set<Hebergement> hebergements) {
        Hebergements = hebergements;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setNumPasseport(String numPasseport) {
        this.numPasseport = numPasseport;
    }

    public void setnTel(String nTel) {
        this.nTel = nTel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public enum AuthProvider {
        LOCAL,
        GOOGLE
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> "ROLE_" + role.getName());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User() {}

    public User(String firstName, String lastName, String email, String password, Role role, String nTel, String numPasseport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nTel = nTel;
        this.numPasseport = numPasseport;
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Hebergement> Hebergements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    @JsonManagedReference  // Evite la boucle infinie lors de la sérialisation de la gastronomy
    private Set<Gastronomy> Gastronomy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Store> Store;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<ReservationGuide> reservationguide;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Blog> Blog;
}
