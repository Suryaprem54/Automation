package com.Service;
import com.Entity.User.User;
import com.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

    @Service
    public class UserService {

        private final UserRepository userRepo;
        private final PasswordEncoder passwordEncoder;

        public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
            this.userRepo = userRepo;
            this.passwordEncoder = passwordEncoder;
        }

        public User register(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        }

        public Optional<User> findByUsername(String username) {
            return userRepo.findByUsername(username);
        }
    }


