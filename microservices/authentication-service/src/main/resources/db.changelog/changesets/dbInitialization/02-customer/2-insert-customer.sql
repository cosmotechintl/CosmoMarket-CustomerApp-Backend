-- liquibase formatted sql
--changeset amritkthapa:1

--preconditions onFail:CONTINUE onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM customer where username='shiroethapa'
INSERT INTO `customer` (name, password, username, is_active, email, mobile_number,address, status_id, wrong_password_attempt_count, two_factor_enabled, wrong_oto_auth_attempt_count,version)
VALUES
    ('Shiroe Thapa', '$2a$10$h/Fm04H01xFqs1iZ8LEVPeg6YfEi/uRz1cLBI9i4KgoRKL0EHctsy', 'shiroethapa', true, 'shiroe@gmail.com', '9840140854', 'Jorpati', 1, 0, false, 0,0),
    ('Amrit Thapa', '$2a$10$h/Fm04H01xFqs1iZ8LEVPeg6YfEi/uRz1cLBI9i4KgoRKL0EHctsy', 'amritkthapa', true, 'amrit@gmail.com', '9840140855', 'Boudha', 1, 0, false, 0,0);
