package com.Repository;
import com.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Long> {


        Optional<User> findByUsername(String username);


        <email> Optional<User> findByEmail(String email);
    }







