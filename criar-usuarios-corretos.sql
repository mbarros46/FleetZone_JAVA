-- Script simples para criar usuários com as credenciais desejadas
-- Execute no Oracle SQL Developer
-- RM556652

-- Limpar usuários existentes
DELETE FROM users WHERE email IN ('admin@fleetzone.com', 'user@fleetzone.com');

-- Inserir Administrador (admin@fleetzone.com / admin123)
INSERT INTO users (nome, email, password_hash, role, created_at) VALUES (
    'Administrador',
    'admin@fleetzone.com',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAp.6MweJODKlOoU4yrOCQj.E4OC', -- admin123
    'ROLE_ADMIN',
    CURRENT_TIMESTAMP
);

-- Inserir Usuário (user@fleetzone.com / user123)
INSERT INTO users (nome, email, password_hash, role, created_at) VALUES (
    'Usuário',
    'user@fleetzone.com',
    '$2a$10$U.45CjLON/YmjEGI0GgPs.Uqe5iTcRO.Bn9TQkNPOGYYeQ7lKUEH2', -- user123
    'ROLE_USER',
    CURRENT_TIMESTAMP
);

COMMIT;

-- Verificar
SELECT 
    email, 
    nome, 
    role,
    'Senha: admin123' AS senha_info
FROM users 
WHERE email = 'admin@fleetzone.com'
UNION ALL
SELECT 
    email, 
    nome, 
    role,
    'Senha: user123' AS senha_info
FROM users 
WHERE email = 'user@fleetzone.com';

SELECT 'USUÁRIOS CRIADOS! Use admin@fleetzone.com/admin123 ou user@fleetzone.com/user123' AS STATUS FROM DUAL;
