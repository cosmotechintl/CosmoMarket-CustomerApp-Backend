-- liquibase formatted sql
--changeset kirankhanal:1

--preconditions onFail:CONTINUE onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM blood_group
INSERT INTO blood_group (name, description, version, uuid)
VALUES
    ('A+', 'A Positive', 0, UUID()),
    ('A-', 'A Negative', 0, UUID()),
    ('B+', 'B Positive', 0, UUID()),
    ('B-', 'B Negative', 0, UUID()),
    ('AB+', 'AB Positive', 0, UUID()),
    ('AB-', 'AB Negative', 0, UUID()),
    ('O+', 'O Positive', 0, UUID()),
    ('O-', 'O Negative', 0, UUID());
