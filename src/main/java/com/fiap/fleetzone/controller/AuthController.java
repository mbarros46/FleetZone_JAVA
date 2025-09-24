package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.AuthDTOs.AuthLoginRequest;
import com.fiap.fleetzone.dto.AuthDTOs.AuthRegisterRequest;
import com.fiap.fleetzone.dto.AuthDTOs.AuthResponse;
import com.fiap.fleetzone.model.User;
import com.fiap.fleetzone.repository.UserRepository;
import com.fiap.fleetzone.security.TokenService;
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
        String email = body.email.toLowerCase().trim();

        if (users.existsByEmail(email)) {
            return ResponseEntity.status(409).body("E-mail já cadastrado");
        }

        User u = new User();
        u.setNome(body.nome.trim());
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(body.senha));

        users.save(u);

        String jwt = tokens.generate(u);
        return ResponseEntity.status(201).body(
                new AuthResponse(jwt, "Bearer", tokens.getExpirationMillis(), u.getId(), u.getNome(), u.getEmail())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest body) {
        if (body == null || isBlank(body.email) || isBlank(body.senha)) {
            return ResponseEntity.badRequest().body("Campos obrigatórios: email, senha");
        }
        return users.findByEmail(body.email.toLowerCase().trim())
                .filter(u -> encoder.matches(body.senha, u.getPasswordHash()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(
                        new AuthResponse(tokens.generate(u), "Bearer", tokens.getExpirationMillis(),
                                u.getId(), u.getNome(), u.getEmail())
                ))
                .orElseGet(() -> ResponseEntity.status(401).body("Credenciais inválidas"));
    }

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}