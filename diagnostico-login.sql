-- Script de diagnóstico para verificar o problema de login
-- Execute este script no Oracle SQL Developer
-- RM556652

-- ======================================
-- 1. VERIFICAR CONEXÃO
-- ======================================
SELECT 'Conexão Oracle OK!' AS STATUS, SYSDATE AS DATA_ATUAL FROM DUAL;

-- ======================================
-- 2. VERIFICAR SE TABELA USERS EXISTE
-- ======================================
SELECT 'Tabela users existe!' AS STATUS FROM user_tables WHERE table_name = 'USERS';

-- ======================================
-- 3. VERIFICAR ESTRUTURA DA TABELA USERS
-- ======================================
DESC users;

-- ======================================
-- 4. VERIFICAR USUÁRIOS EXISTENTES
-- ======================================
SELECT 
    'USUÁRIOS ENCONTRADOS:' AS STATUS,
    COUNT(*) AS TOTAL
FROM users;

SELECT 
    id,
    email, 
    nome, 
    role,
    SUBSTR(password_hash, 1, 30) || '...' AS hash_preview,
    created_at
FROM users 
ORDER BY email;

-- ======================================
-- 5. TESTAR HASH DE SENHA ESPECÍFICO
-- ======================================
SELECT 
    email,
    CASE 
        WHEN email = 'admin@fleetzone.com' AND password_hash = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAp.6MweJODKlOoU4yrOCQj.E4OC'
        THEN 'HASH CORRETO (admin123)'
        WHEN email = 'user@fleetzone.com' AND password_hash = '$2a$10$U.45CjLON/YmjEGI0GgPs.Uqe5iTcRO.Bn9TQkNPOGYYeQ7lKUEH2'
        THEN 'HASH CORRETO (user123)'
        ELSE 'HASH DIFERENTE - PRECISA ATUALIZAR'
    END AS STATUS_HASH
FROM users 
WHERE email IN ('admin@fleetzone.com', 'user@fleetzone.com');

-- ======================================
-- 6. INSERIR/ATUALIZAR USUÁRIOS COM CREDENCIAIS CORRETAS
-- ======================================

-- Deletar usuários existentes se necessário
DELETE FROM users WHERE email IN ('admin@fleetzone.com', 'user@fleetzone.com');

-- Inserir admin (admin@fleetzone.com / admin123)
INSERT INTO users (nome, email, password_hash, role, created_at) VALUES (
    'Administrador',
    'admin@fleetzone.com',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAp.6MweJODKlOoU4yrOCQj.E4OC', -- admin123
    'ROLE_ADMIN',
    CURRENT_TIMESTAMP
);

-- Inserir user (user@fleetzone.com / user123)
INSERT INTO users (nome, email, password_hash, role, created_at) VALUES (
    'Usuário',
    'user@fleetzone.com',
    '$2a$10$U.45CjLON/YmjEGI0GgPs.Uqe5iTcRO.Bn9TQkNPOGYYeQ7lKUEH2', -- user123
    'ROLE_USER',
    CURRENT_TIMESTAMP
);

COMMIT;

-- ======================================
-- 7. VERIFICAÇÃO FINAL
-- ======================================
SELECT 'USUÁRIOS CRIADOS COM SUCESSO!' AS STATUS FROM DUAL;

SELECT 
    id,
    email, 
    nome, 
    role,
    created_at
FROM users 
WHERE email IN ('admin@fleetzone.com', 'user@fleetzone.com')
ORDER BY email;

-- ======================================
-- INSTRUÇÕES FINAIS
-- ======================================
SELECT 'Agora tente fazer login com:' AS INSTRUCAO FROM DUAL
UNION ALL
SELECT 'Email: admin@fleetzone.com | Senha: admin123' FROM DUAL
UNION ALL  
SELECT 'Email: user@fleetzone.com | Senha: user123' FROM DUAL;
