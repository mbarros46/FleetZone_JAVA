package com.fiap.fleetzone.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String adminPassword = "admin123";
        String userPassword = "user123";
        
        String adminHash = encoder.encode(adminPassword);
        String userHash = encoder.encode(userPassword);
        
        System.out.println("=== HASHES DE SENHA PARA ORACLE ===");
        System.out.println();
        System.out.println("Admin (admin123): " + adminHash);
        System.out.println("User (user123): " + userHash);
        System.out.println();
        System.out.println("=== SQL PARA ATUALIZAR NO ORACLE ===");
        System.out.println();
        System.out.println("-- Atualizar senha admin");
        System.out.println("UPDATE users SET password_hash = '" + adminHash + "' WHERE email = 'admin@fleetzone.com';");
        System.out.println();
        System.out.println("-- Atualizar senha user");
        System.out.println("UPDATE users SET password_hash = '" + userHash + "' WHERE email = 'user@fleetzone.com';");
        System.out.println();
        System.out.println("COMMIT;");
    }
}
