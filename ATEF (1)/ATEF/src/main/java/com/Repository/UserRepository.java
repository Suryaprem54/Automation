package com.Repository;

import com.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
public class UserRepository  {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    // Constructor Injection
    public void UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRepository(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Register or Save a new user
    public User save(User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    //Find user by username
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    // (Optional) Find user by email
    public Optional<User> findByEmail(String email) {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }





    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);
    }

}
