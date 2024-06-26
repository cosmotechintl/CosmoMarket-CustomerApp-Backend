-- liquibase formatted sql
-- changeset kirankhanal:1

--preconditions onFail:CONTINUE onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM customer;
INSERT INTO `customer` (first_name, last_name, password, username,date_of_birth, is_active, email, mobile_number,address,gender, blood_group, status, wrong_password_attempt_count, two_factor_enabled, wrong_oto_auth_attempt_count,version)
VALUES
    ('Kiran', 'Khanal', '$2a$10$h/Fm04H01xFqs1iZ8LEVPeg6YfEi/uRz1cLBI9i4KgoRKL0EHctsy', 'kirankhanal', '2001-02-08', true, 'kirankhanal@gmail.com', '9824998432', 'Jhapa',1,1, 1, 0, false, 0,0);
