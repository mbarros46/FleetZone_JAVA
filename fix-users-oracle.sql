-- Script para corrigir usuários no Oracle
-- Execute este script no Oracle SQL Developer
-- RM556652

-- ======================================
-- VERIFICAR SE USUÁRIOS EXISTEM
-- ======================================
SELECT 'Verificando usuários existentes...' AS STATUS FROM DUAL;

SELECT 
    email, 
    nome, 
    role,
    SUBSTR(password_hash, 1, 20) || '...' AS hash_preview
FROM users 
ORDER BY email;

-- ======================================
-- DELETAR USUÁRIOS EXISTENTES (se necessário)
-- ======================================
-- DELETE FROM users WHERE email IN ('admin@fleetzone.com', 'user@fleetzone.com');

-- ======================================
-- INSERIR USUÁRIOS COM SENHAS CORRETAS
-- ======================================

-- Usuário Administrador (admin@fleetzone.com / admin123)
INSERT INTO users (nome, email, password_hash, role) VALUES (
    'Administrador do Sistema',
    'admin@fleetzone.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- password
    'ROLE_ADMIN'
);

-- Usuário Comum (user@fleetzone.com / user123)  
INSERT INTO users (nome, email, password_hash, role) VALUES (
    'Usuário do Sistema',
    'user@fleetzone.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- password
    'ROLE_USER'
);

-- ======================================
-- COMMIT
-- ======================================
COMMIT;

-- ======================================
-- VERIFICAR INSERÇÃO
-- ======================================
SELECT 'Usuários inseridos com sucesso!' AS STATUS FROM DUAL;

SELECT 
    id,
    email, 
    nome, 
    role,
    created_at
FROM users 
ORDER BY email;

-- ======================================
-- INSTRUÇÕES
-- ======================================
-- Agora você pode fazer login com:
-- Email: admin@fleetzone.com | Senha: password
-- Email: user@fleetzone.com  | Senha: password
--
-- Nota: O hash $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi
-- corresponde à senha "password" em BCrypt
