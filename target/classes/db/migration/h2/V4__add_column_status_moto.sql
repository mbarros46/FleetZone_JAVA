-- V4: Adiciona coluna de status na tabela moto (H2 compatible)
ALTER TABLE moto ADD status VARCHAR(20) DEFAULT 'DISPONIVEL';