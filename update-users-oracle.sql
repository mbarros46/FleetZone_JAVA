-- Script alternativo: ATUALIZAR usuários existentes no Oracle
-- Execute este script se os usuários já existem no banco
-- RM556652

-- ======================================
-- ATUALIZAR SENHAS DOS USUÁRIOS
-- ======================================

-- Atualizar admin para senha "password"
UPDATE users 
SET password_hash = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi'
WHERE email = 'admin@fleetzone.com';

-- Atualizar user para senha "password"
UPDATE users 
SET password_hash = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi'
WHERE email = 'user@fleetzone.com';

-- ======================================
-- VERIFICAR SE ROLES ESTÃO CORRETAS
-- ======================================
UPDATE users 
SET role = 'ROLE_ADMIN'
WHERE email = 'admin@fleetzone.com';

UPDATE users 
SET role = 'ROLE_USER'
WHERE email = 'user@fleetzone.com';

-- ======================================
-- COMMIT
-- ======================================
COMMIT;

-- ======================================
-- VERIFICAR RESULTADO
-- ======================================
SELECT 
    'Usuários atualizados!' AS STATUS,
    COUNT(*) AS TOTAL_USERS
FROM users;

SELECT 
    email, 
    nome, 
    role,
    CASE 
        WHEN password_hash = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' 
        THEN 'SENHA OK (password)'
        ELSE 'SENHA DIFERENTE'
    END AS STATUS_SENHA
FROM users 
WHERE email IN ('admin@fleetzone.com', 'user@fleetzone.com')
ORDER BY email;
