package com.fiap.fleetzone.dto;

public class AuthDTOs {

    public static class AuthRegisterRequest {
        public String nome;
        public String email;
        public String senha;
    }

    public static class AuthLoginRequest {
        public String email;
        public String senha;
    }

    public static class AuthResponse {
        public String token;
        public String tokenType;
        public Long expiresInMillis;
        public Long userId;
        public String nome;
        public String email;

        public AuthResponse(String token, String tokenType, Long expiresInMillis,
                            Long userId, String nome, String email) {
            this.token = token;
            this.tokenType = tokenType;
            this.expiresInMillis = expiresInMillis;
            this.userId = userId;
            this.nome = nome;
            this.email = email;
        }
    }

    public static class Usuario {
        public Long id;
        public String nome;
        public String email;

        public Usuario(Long id, String nome, String email) {
            this.id = id;
            this.nome = nome;
            this.email = email;
        }
    }
}