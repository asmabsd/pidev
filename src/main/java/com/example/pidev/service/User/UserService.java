package com.example.pidev.service.User;

import com.example.pidev.Interface.User.IUser;
import com.example.pidev.dtos.ProfileDto;
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
    private RoleService roleService;

    @Override
    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }

        Role role = roleService.getRoleByName(user.getRole().getName());
        user.setRole(role);

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
        // Supprimé ou remplacé par getUser(Long id)
        throw new UnsupportedOperationException("Méthode getUser(int id) non utilisée.");
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur avec ID " + id + " non trouvé !"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());

                    if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                        user.setPassword(passwordService.encryptPassword(newUser.getPassword()));
                    }

                    user.setnTel(newUser.getnTel());
                    user.setNumPasseport(newUser.getNumPasseport());

                    Role role = roleService.getRoleByName(newUser.getRole().getName());
                    user.setRole(role);

                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("Utilisateur avec ID " + id + " non trouvé !"));
    }

    public User updateUserProfile(String email, ProfileDto profileDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setnTel(profileDto.getnTel());
        user.setNumPasseport(profileDto.getNumPasseport());

        if (profileDto.getPassword() != null && !profileDto.getPassword().isEmpty()) {
            user.setPassword(passwordService.encryptPassword(profileDto.getPassword()));
        }

        return userRepository.save(user);
    }
}
