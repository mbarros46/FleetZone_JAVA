package com.fiap.fleetzone.service;

import com.fiap.fleetzone.dto.AuthDTOs.Usuario;
import com.fiap.fleetzone.model.User;
import com.fiap.fleetzone.repository.UserRepository;
import com.fiap.fleetzone.security.TokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository users;
    private final TokenService tokens;

    public UserService(UserRepository users, TokenService tokens) {
        this.users = users;
        this.tokens = tokens;
    }

    public Usuario getUsuarioFromAuthHeader(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token ausente");
        }
        String token = auth.substring(7);
        try {
            Long userId = Long.valueOf(tokens.getSubject(token));
            Optional<User> userOpt = users.findById(userId);
            return userOpt.map(u -> new Usuario(u.getId(), u.getNome(), u.getEmail()))
                    .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Token inválido");
        }
    }
}
