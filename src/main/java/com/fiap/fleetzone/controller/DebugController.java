package com.fiap.fleetzone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
      /**
     * Endpoint para gerar hash de senha para debug
     * Acesse: http://localhost:8085/debug/hash?senha=admin123
     */
    @GetMapping("/debug/hash")
    public String gerarHash(@RequestParam String senha) {
        String hash = passwordEncoder.encode(senha);
        return String.format(
            "<h3>Gerador de Hash BCrypt</h3>" +
            "Senha: <strong>%s</strong><br>" +
            "Hash: <code>%s</code><br><br>" +
            "<h4>SQL para Oracle:</h4>" +
            "<pre>UPDATE users SET password_hash = '%s' WHERE email = 'admin@fleetzone.com';<br>" +
            "UPDATE users SET password_hash = '%s' WHERE email = 'user@fleetzone.com';<br>" +
            "COMMIT;</pre>" +
            "<hr>" +
            "<h4>Hashes para admin123 e user123:</h4>" +
            "<code>admin123: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAp.6MweJODKlOoU4yrOCQj.E4OC</code><br>" +
            "<code>user123: $2a$10$U.45CjLON/YmjEGI0GgPs.Uqe5iTcRO.Bn9TQkNPOGYYeQ7lKUEH2</code>",
            senha, hash, hash, hash
        );
    }
    
    /**
     * Endpoint para testar se uma senha confere com um hash
     * Acesse: http://localhost:8085/debug/test?senha=password&hash=HASH_AQUI
     */
    @GetMapping("/debug/test")
    public String testarSenha(
            @RequestParam String senha, 
            @RequestParam String hash) {
        boolean matches = passwordEncoder.matches(senha, hash);
        return String.format(
            "Senha: %s<br>Hash: %s<br>Confere: %s",
            senha, hash, matches ? "✅ SIM" : "❌ NÃO"
        );
    }
    
    /**
     * Endpoint para testar as credenciais específicas
     * Acesse: http://localhost:8085/debug/test-credentials
     */
    @GetMapping("/debug/test-credentials")
    public String testarCredenciais() {
        String adminHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAp.6MweJODKlOoU4yrOCQj.E4OC";
        String userHash = "$2a$10$U.45CjLON/YmjEGI0GgPs.Uqe5iTcRO.Bn9TQkNPOGYYeQ7lKUEH2";
        
        boolean adminOk = passwordEncoder.matches("admin123", adminHash);
        boolean userOk = passwordEncoder.matches("user123", userHash);
        
        return String.format(
            "<h3>Teste de Credenciais FleetZone</h3>" +
            "<h4>Administrador:</h4>" +
            "Email: admin@fleetzone.com<br>" +
            "Senha: admin123<br>" +
            "Hash válido: %s<br><br>" +
            "<h4>Usuário:</h4>" +
            "Email: user@fleetzone.com<br>" +
            "Senha: user123<br>" +
            "Hash válido: %s<br><br>" +
            "<hr>" +
            "<h4>Scripts para Oracle:</h4>" +
            "<a href='/debug/sql-script'>Clique aqui para ver o script SQL completo</a>",
            adminOk ? "✅ SIM" : "❌ NÃO",
            userOk ? "✅ SIM" : "❌ NÃO"
        );
    }
    
    /**
     * Endpoint para mostrar script SQL pronto
     * Acesse: http://localhost:8085/debug/sql-script
     */
    @GetMapping("/debug/sql-script")
    public String sqlScript() {
        return String.format(
            "<h3>Script SQL para Oracle</h3>" +
            "<pre>" +
            "-- Limpar usuários existentes\n" +
            "DELETE FROM users WHERE email IN ('admin@fleetzone.com', 'user@fleetzone.com');\n\n" +
            "-- Administrador (admin@fleetzone.com / admin123)\n" +
            "INSERT INTO users (nome, email, password_hash, role, created_at) VALUES (\n" +
            "    'Administrador',\n" +
            "    'admin@fleetzone.com',\n" +
            "    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAp.6MweJODKlOoU4yrOCQj.E4OC',\n" +
            "    'ROLE_ADMIN',\n" +
            "    CURRENT_TIMESTAMP\n" +
            ");\n\n" +
            "-- Usuário (user@fleetzone.com / user123)\n" +
            "INSERT INTO users (nome, email, password_hash, role, created_at) VALUES (\n" +
            "    'Usuário',\n" +
            "    'user@fleetzone.com',\n" +
            "    '$2a$10$U.45CjLON/YmjEGI0GgPs.Uqe5iTcRO.Bn9TQkNPOGYYeQ7lKUEH2',\n" +
            "    'ROLE_USER',\n" +
            "    CURRENT_TIMESTAMP\n" +
            ");\n\n" +
            "COMMIT;\n" +
            "</pre>" +
            "<p><strong>Copie e cole este script no Oracle SQL Developer!</strong></p>"
        );
    }
}
