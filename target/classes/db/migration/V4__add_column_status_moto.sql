-- V4: Adiciona coluna de status na tabela moto
ALTER TABLE moto ADD (status VARCHAR2(20) DEFAULT 'DISPONIVEL');
