package com.example.pidev.service.User;

import com.example.pidev.Interface.User.IUser;
import com.example.pidev.entity.User.Role;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private RoleService roleService; // Ajout du service pour gérer les rôles

    @Override
    public User saveUser(User user) {
        // Vérifier si l'email est déjà utilisé
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }

        // Vérifier que le rôle existe en base
        Role role = roleService.getRoleByName(user.getRole().getName());
        user.setRole(role);

        // Crypter le mot de passe avant de sauvegarder
        user.setPassword(passwordService.encryptPassword(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("Utilisateur non trouvé !");
        }
        userRepository.delete(user);
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User getUser(Long id) { // Changement de int -> Long pour correspondre à la base de données
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur avec ID " + id + " non trouvé !"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Met à jour un utilisateur existant.
     * @param id L'ID de l'utilisateur à mettre à jour.
     * @param newUser Les nouvelles données de l'utilisateur.
     * @return L'utilisateur mis à jour.
     * @throws RuntimeException Si l'utilisateur n'est pas trouvé.
     */
    public User updateUser(Long id, User newUser) { // Changement de int -> Long
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());

                    // Crypter le nouveau mot de passe s'il est fourni
                    if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                        user.setPassword(passwordService.encryptPassword(newUser.getPassword()));
                    }

                    user.setnTel(newUser.getnTel());
                    user.setNumPasseport(newUser.getNumPasseport());

                    // Mise à jour du rôle en base de données
                    Role role = roleService.getRoleByName(newUser.getRole().getName());
                    user.setRole(role);

                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("Utilisateur avec ID " + id + " non trouvé !"));
    }
}
