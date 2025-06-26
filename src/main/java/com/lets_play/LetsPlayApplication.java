package com.lets_play;

import com.lets_play.model.Role;
import com.lets_play.model.User;
import com.lets_play.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LetsPlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetsPlayApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("diouf@letsplay.com").isEmpty()) {
                User admin = new User();
                admin.setName("diouf");
                admin.setEmail("diouf@letsplay.com");
                admin.setPassword(passwordEncoder.encode("diouf123"));
                admin.setRole(Role.ROLE_ADMIN);
                userRepository.save(admin);
                System.out.println("✅ Super utilisateur créé : diouf@letsplay.com / diouf123");
            }
        };
    }
}
