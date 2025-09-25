package com.fiap.fleetzone.config;

import com.fiap.fleetzone.model.Role;
import com.fiap.fleetzone.model.User;
import com.fiap.fleetzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(" DataLoader executando...");
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        System.out.println(" PasswordEncoder obtido: " + passwordEncoder.getClass().getSimpleName());
        
        // Criar usuário administrador se não existir
        System.out.println(" Verificando se admin@fleetzone.com existe...");
        boolean adminExists = userRepository.existsByEmail("admin@fleetzone.com");
        System.out.println(" Admin existe? " + adminExists);
        
        if (!adminExists) {
            User admin = new User();
            admin.setNome("Administrador do Sistema");
            admin.setEmail("admin@fleetzone.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
            System.out.println("✅ Usuário administrador criado: admin@fleetzone.com / admin123");
        }

        // Criar usuário comum se não existir
        System.out.println(" Verificando se user@fleetzone.com existe...");
        boolean userExists = userRepository.existsByEmail("user@fleetzone.com");
        System.out.println(" User existe? " + userExists);
        
        if (!userExists) {
            User user = new User();
            user.setNome("Usuário do Sistema");
            user.setEmail("user@fleetzone.com");
            user.setPasswordHash(passwordEncoder.encode("user123"));
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
            System.out.println(" Usuário comum criado: user@fleetzone.com / user123");
        }

        System.out.println(" Sistema de autenticação configurado com sucesso!");
    }
}