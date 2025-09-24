package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.AuthDTOs.*;
import com.fiap.fleetzone.model.User;
import com.fiap.fleetzone.repository.UserRepository;
import com.fiap.fleetzone.security.TokenService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    private final BCryptPasswordEncoder encoder;
    private final TokenService tokens;

    public AuthController(UserRepository users, BCryptPasswordEncoder encoder, TokenService tokens) {
        this.users = users;
        this.encoder = encoder;
        this.tokens = tokens;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegisterRequest body) {
        if (body == null || isBlank(body.nome) || isBlank(body.email) || isBlank(body.senha)) {
            return ResponseEntity.badRequest().body("Campos obrigatórios: nome, email, senha");
        }
        String email = body.email.toLowerCase();
        if (users.existsByEmail(email)) {
            return ResponseEntity.status(409).body("E-mail já está em uso");
        }
        User u = new User();
        u.setNome(body.nome);
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(body.senha));
        users.save(u);

        String token = tokens.generate(u);
        return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getNome(), u.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest body) {
        if (body == null || isBlank(body.email) || isBlank(body.senha)) {
            return ResponseEntity.badRequest().body("Campos obrigatórios: email, senha");
        }
        return users.findByEmail(body.email.toLowerCase())
                .filter(u -> encoder.matches(body.senha, u.getPasswordHash()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(new AuthResponse(tokens.generate(u), u.getId(), u.getNome(), u.getEmail())))
                .orElseGet(() -> ResponseEntity.status(401).body("Credenciais inválidas"));
    }

    private static boolean isBlank(String s){ return s == null || s.trim().isEmpty(); }
}