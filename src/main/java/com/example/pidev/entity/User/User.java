package com.example.pidev.entity.User;

import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.GestionSouvenir.Store;
import com.example.pidev.entity.Transport.ReservationTransport;
import com.example.pidev.entity.activities.Blog;
import com.example.pidev.entity.hebergement.Hebergement;
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
    private Role role;

    @Column(name = "auth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider = AuthProvider.LOCAL;

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
    private Set<Gastronomy> Gastronomy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Store> Store;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<ReservationGuide> reservationguide;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Blog> Blog;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ReservationTransport> reservationTransports;
}
