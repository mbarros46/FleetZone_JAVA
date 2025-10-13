package com.fiap.fleetzone.service;

import com.fiap.fleetzone.dto.AuthDTOs.AuthLoginRequest;
import com.fiap.fleetzone.dto.AuthDTOs.AuthRegisterRequest;
import com.fiap.fleetzone.dto.AuthDTOs.AuthResponse;
import com.fiap.fleetzone.model.User;
import com.fiap.fleetzone.repository.UserRepository;
import com.fiap.fleetzone.security.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final TokenService tokens;

    public AuthService(UserRepository users, PasswordEncoder encoder, TokenService tokens) {
        this.users = users;
        this.encoder = encoder;
        this.tokens = tokens;
    }

    public AuthResponse register(AuthRegisterRequest body) {
        if (body == null || isBlank(body.nome) || isBlank(body.email) || isBlank(body.senha)) {
            throw new IllegalArgumentException("Campos obrigatórios: nome, email, senha");
        }

        String email = body.email.toLowerCase().trim();
        if (users.existsByEmail(email)) throw new IllegalStateException("E-mail já cadastrado");

        User u = new User();
        u.setNome(body.nome.trim());
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(body.senha));
        users.save(u);

        String jwt = tokens.generate(u);
        return new AuthResponse(jwt, "Bearer", tokens.getExpirationMillis(), u.getId(), u.getNome(), u.getEmail());
    }

    public AuthResponse login(AuthLoginRequest body) {
        if (body == null || isBlank(body.email) || isBlank(body.senha)) {
            throw new IllegalArgumentException("Campos obrigatórios: email, senha");
        }

        Optional<User> userOpt = users.findByEmail(body.email.toLowerCase().trim())
                .filter(u -> encoder.matches(body.senha, u.getPasswordHash()));

        return userOpt.map(u -> new AuthResponse(tokens.generate(u), "Bearer", tokens.getExpirationMillis(), u.getId(), u.getNome(), u.getEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));
    }

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
