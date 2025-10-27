package com.management.hms.hms;

import com.management.hms.hms.entity.User;
import com.management.hms.hms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HmsApplication {

    public static void main(String[] args) {

        SpringApplication.run(HmsApplication.class, args);
    }


    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setName("admin");            // username
                user.setPassword(passwordEncoder.encode("admin123")); // password
                user.setEmail("admin@example.com");
                user.setRole("ADMIN");
                userRepository.save(user);
                System.out.println("Default admin user created: admin / admin123");
            }
        };
    }
}