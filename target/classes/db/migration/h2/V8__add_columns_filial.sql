-- Adicionar colunas ativa e codigo na tabela filial

ALTER TABLE filial 
ADD COLUMN ativa BOOLEAN DEFAULT TRUE;

ALTER TABLE filial 
ADD COLUMN codigo VARCHAR(20);

-- Atualizar registros existentes com códigos automáticos
UPDATE filial SET codigo = 'FIL' || LPAD(CAST(id AS VARCHAR), 3, '0') WHERE codigo IS NULL;