package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.AuthDTOs.Usuario;
import com.fiap.fleetzone.repository.UserRepository;
import com.fiap.fleetzone.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository users;
    private final TokenService tokens;

    public UserController(UserRepository users, TokenService tokens) {
        this.users = users;
        this.tokens = tokens;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token ausente");
        }
        String token = auth.substring(7);
        try {
            Long userId = Long.valueOf(tokens.getSubject(token));
            return users.findById(userId)
                    .<ResponseEntity<?>>map(u -> ResponseEntity.ok(new Usuario(u.getId(), u.getNome(), u.getEmail())))
                    .orElseGet(() -> ResponseEntity.status(404).body("Usuário não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido");
        }
    }
}