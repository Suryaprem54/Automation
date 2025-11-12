package com.Entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "users")
    @Data
    @NoArgsConstructor
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String username;

        @Column(unique = true, nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String role;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        @PrePersist
        protected void onCreate() {
            this.createdAt = LocalDateTime.now();
            if (this.role == null) {
                this.role = "USER";
 }
        }

        public User() {
        }

        //  Custom Setter for Password (optional customization)
        public void setPassword(String password) {
            this.password = password;
        }

        //  Getter for Password
        public String getPassword() {
            return password;
        }

        // Getter for Email
        public String getEmail() {
            return email;
        }

        //  Setter for Email
        public void setEmail(String email) {
            this.email = email;
        }

        //  Getter and Setter for Username
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }



