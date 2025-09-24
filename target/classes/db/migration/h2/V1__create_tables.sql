-- V1: Criação das tabelas principais (H2 compatible)
CREATE TABLE patio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    localizacao VARCHAR(200) NOT NULL
);

CREATE TABLE moto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    placa VARCHAR(20) NOT NULL,
    patio_id BIGINT,
    CONSTRAINT fk_patio FOREIGN KEY (patio_id) REFERENCES patio(id)
);

-- Tabela de usuários para JWT
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);