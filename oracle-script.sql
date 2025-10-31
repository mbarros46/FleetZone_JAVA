-- Script de criação das tabelas para Oracle Database
-- FleetZone - Sistema de Gestão de Motos
-- RM556652

-- ======================================
-- LIMPEZA (opcional - descomente se precisar)
-- ======================================
-- DROP TABLE moto CASCADE CONSTRAINTS;
-- DROP TABLE patio CASCADE CONSTRAINTS;
-- DROP TABLE filial CASCADE CONSTRAINTS;
-- DROP TABLE users CASCADE CONSTRAINTS;
-- DROP SEQUENCE users_seq;
-- DROP SEQUENCE filial_seq;
-- DROP SEQUENCE patio_seq;
-- DROP SEQUENCE moto_seq;

-- ======================================
-- CRIAÇÃO DAS SEQUENCES
-- ======================================
CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE filial_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE patio_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE moto_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- ======================================
-- TABELA USERS
-- ======================================
CREATE TABLE users (
    id NUMBER(19) DEFAULT users_seq.NEXTVAL PRIMARY KEY,
    nome VARCHAR2(120) NOT NULL,
    email VARCHAR2(150) NOT NULL,
    password_hash VARCHAR2(100) NOT NULL,
    role VARCHAR2(20) DEFAULT 'ROLE_USER' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT ck_users_role CHECK (role IN ('ROLE_ADMIN', 'ROLE_USER'))
);

-- ======================================
-- TABELA FILIAL
-- ======================================
CREATE TABLE filial (
    id NUMBER(19) DEFAULT filial_seq.NEXTVAL PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    endereco VARCHAR2(200),
    cidade VARCHAR2(100),
    estado VARCHAR2(2),
    cep VARCHAR2(10),
    telefone VARCHAR2(20),
    email VARCHAR2(100),
    ativa NUMBER(1) DEFAULT 1 NOT NULL,
    codigo VARCHAR2(20),
    CONSTRAINT ck_filial_ativa CHECK (ativa IN (0, 1))
);

-- ======================================
-- TABELA PATIO
-- ======================================
CREATE TABLE patio (
    id NUMBER(19) DEFAULT patio_seq.NEXTVAL PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    localizacao VARCHAR2(200),
    filial_id NUMBER(19),
    CONSTRAINT fk_patio_filial FOREIGN KEY (filial_id) REFERENCES filial(id)
);

-- ======================================
-- TABELA MOTO
-- ======================================
CREATE TABLE moto (
    id NUMBER(19) DEFAULT moto_seq.NEXTVAL PRIMARY KEY,
    modelo VARCHAR2(100) NOT NULL,
    placa VARCHAR2(10) NOT NULL,
    status VARCHAR2(50),
    patio_id NUMBER(19),
    CONSTRAINT fk_moto_patio FOREIGN KEY (patio_id) REFERENCES patio(id),
    CONSTRAINT uk_moto_placa UNIQUE (placa)
);

-- ======================================
-- ÍNDICES PARA PERFORMANCE
-- ======================================
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_patio_filial ON patio(filial_id);
CREATE INDEX idx_moto_patio ON moto(patio_id);
CREATE INDEX idx_moto_placa ON moto(placa);
CREATE INDEX idx_moto_status ON moto(status);

-- ======================================
-- DADOS INICIAIS
-- ======================================

-- Usuário Administrador
INSERT INTO users (nome, email, password_hash, role) VALUES (
    'Administrador do Sistema',
    'admin@fleetzone.com',
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRdvuTdgNn0O/hHj8tz4ZpCNhq2', -- admin123
    'ROLE_ADMIN'
);

-- Usuário Comum
INSERT INTO users (nome, email, password_hash, role) VALUES (
    'Usuário do Sistema',
    'user@fleetzone.com',
    '$2a$10$h/ej9k8XEP0UO/fF3Y4jdOLgDSxXPHhhpLw3rnhzO5HZs8qQgUlHG', -- user123
    'ROLE_USER'
);

-- Filiais de exemplo
INSERT INTO filial (nome, endereco, cidade, estado, cep, telefone, email, codigo) VALUES (
    'FleetZone São Paulo',
    'Av. Paulista, 1000',
    'São Paulo',
    'SP',
    '01310-100',
    '(11) 3333-4444',
    'sp@fleetzone.com',
    'FZ-SP-001'
);

INSERT INTO filial (nome, endereco, cidade, estado, cep, telefone, email, codigo) VALUES (
    'FleetZone Rio de Janeiro',
    'Av. Copacabana, 500',
    'Rio de Janeiro',
    'RJ',
    '22070-000',
    '(21) 2222-3333',
    'rj@fleetzone.com',
    'FZ-RJ-001'
);

-- Pátios de exemplo
INSERT INTO patio (nome, localizacao, filial_id) VALUES (
    'Pátio Central SP',
    'Centro - São Paulo/SP',
    1
);

INSERT INTO patio (nome, localizacao, filial_id) VALUES (
    'Pátio Zona Sul SP',
    'Vila Olímpia - São Paulo/SP',
    1
);

INSERT INTO patio (nome, localizacao, filial_id) VALUES (
    'Pátio Copacabana',
    'Copacabana - Rio de Janeiro/RJ',
    2
);

-- Motos de exemplo
INSERT INTO moto (modelo, placa, status, patio_id) VALUES (
    'Honda CG 160 Start',
    'ABC-1234',
    'DISPONIVEL',
    1
);

INSERT INTO moto (modelo, placa, status, patio_id) VALUES (
    'Yamaha Factor 125',
    'DEF-5678',
    'DISPONIVEL',
    1
);

INSERT INTO moto (modelo, placa, status, patio_id) VALUES (
    'Honda PCX 150',
    'GHI-9012',
    'MANUTENCAO',
    2
);

INSERT INTO moto (modelo, placa, status, patio_id) VALUES (
    'Suzuki Burgman 125',
    'JKL-3456',
    'DISPONIVEL',
    3
);

-- ======================================
-- COMMIT DAS TRANSAÇÕES
-- ======================================
COMMIT;

-- ======================================
-- VERIFICAÇÃO DOS DADOS INSERIDOS
-- ======================================
SELECT 'USERS' AS TABELA, COUNT(*) AS TOTAL FROM users
UNION ALL
SELECT 'FILIAL' AS TABELA, COUNT(*) AS TOTAL FROM filial
UNION ALL
SELECT 'PATIO' AS TABELA, COUNT(*) AS TOTAL FROM patio
UNION ALL
SELECT 'MOTO' AS TABELA, COUNT(*) AS TOTAL FROM moto;

-- ======================================
-- SCRIPT CONCLUÍDO
-- ======================================
-- Execute este script no seu Oracle SQL Developer ou SQL*Plus
-- conectado com o usuário RM556652
-- 
-- Dados de acesso criados:
-- Admin: admin@fleetzone.com / admin123
-- User:  user@fleetzone.com / user123
