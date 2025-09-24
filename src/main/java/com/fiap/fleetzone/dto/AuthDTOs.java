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
        public Usuario usuario;

        public AuthResponse(String token, Long id, String nome, String email) {
            this.token = token;
            this.usuario = new Usuario(id, nome, email);
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