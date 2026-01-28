
-- Add admin account with password as 12345
INSERT INTO appointment_user(uuid, username, password, full_name, is_super_admin) VALUES
    ('SYS-ADMIN','admin', '$2a$12$xqx/V5639uSRyiEuX1eowO1Lqa4vwOcKAOsut9A1zBp3LC05NN9Wq', 'Administrator', TRUE);