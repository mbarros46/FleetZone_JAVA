-- V5: Dados iniciais do usuário admin (H2 compatible)
INSERT INTO users (nome, email, password_hash, created_at) VALUES 
('Admin Sistema', 'admin@fleetzone.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', CURRENT_TIMESTAMP);