// File: src/main/java/com/Repository/UserRepository.java (CORRECTED)
package com.Repository;
import com.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Removed the incorrect <email> generic syntax
    Optional<User> findByEmail(String email);
}






