-- V5: Inserir administrador e usuário demo (H2)
INSERT INTO users (nome, email, password_hash, role, created_at) VALUES 
('Admin Sistema', 'admin@fleetzone.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAp.6MweJODKlOoU4yrOCQj.E4OC', 'ROLE_ADMIN', CURRENT_TIMESTAMP);

INSERT INTO users (nome, email, password_hash, role, created_at) VALUES 
('Usuário Demo', 'user@fleetzone.com', '$2a$10$U.45CjLON/YmjEGI0GgPs.Uqe5iTcRO.Bn9TQkNPOGYYeQ7lKUEH2', 'ROLE_USER', CURRENT_TIMESTAMP);