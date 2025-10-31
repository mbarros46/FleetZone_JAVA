-- V7: Adicionar coluna FILIAL_ID na tabela PATIO
-- Adicionando foreign key para relacionar pátio com filial

ALTER TABLE patio ADD COLUMN filial_id BIGINT;

-- Adicionar constraint de foreign key
ALTER TABLE patio ADD CONSTRAINT fk_patio_filial 
    FOREIGN KEY (filial_id) REFERENCES filial(id);

-- Comentário da tabela
COMMENT ON COLUMN patio.filial_id IS 'ID da filial à qual o pátio pertence';