package com.example.pidev.service;


import com.example.pidev.Interface.IUser;
import com.example.pidev.entity.User;
import com.example.pidev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUser { // Implémenter l'interface IUser

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Override
    public User saveUser(User user) {
        // Crypter le mot de passe avant de sauvegarder
        String encryptedPassword = passwordService.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        // Supprimer l'utilisateur directement
        userRepository.delete(user);
    }

    @Override
    public User getUser(int id) {
        // Récupérer l'utilisateur par son ID
        Optional<User> userOptional = userRepository.findById((long) id); // Convertir int en Long
        return userOptional.orElseThrow(() -> new IllegalArgumentException("Utilisateur avec ID " + id + " non trouvé !"));
    }

    @Override
    public List<User> getUsers() {
        // Récupérer tous les utilisateurs
        return userRepository.findAll();
    }

    /**
     * Met à jour un utilisateur existant.
     * @param id L'ID de l'utilisateur à mettre à jour.
     * @param newUser Les nouvelles données de l'utilisateur.
     * @return L'utilisateur mis à jour.
     * @throws IllegalArgumentException Si l'utilisateur n'est pas trouvé.
     */
    public User updateUser(int id, User newUser) {
        return userRepository.findById((long) id) // Convertir int en Long
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());

                    // Crypter le nouveau mot de passe s'il est fourni
                    if (newUser.getPassword() != null) {
                        user.setPassword(passwordService.encryptPassword(newUser.getPassword()));
                    }

                    user.setnTel(newUser.getnTel());
                    user.setNumPasseport(newUser.getNumPasseport());
                    user.setRole(newUser.getRole());
                    return userRepository.save(user);
                }).orElseThrow(() -> new IllegalArgumentException("Utilisateur avec ID " + id + " non trouvé !"));
    }
}