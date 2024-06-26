-- liquibase formatted sql
--changeset kirankhanal:1

--preconditions onFail:CONTINUE onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM gender
INSERT INTO gender (name, description, version, created_at, uuid)
VALUES
    ('Male','MALE', 0, NOW(), UUID()),
    ('Female','FEMALE', 0, NOW(), UUID()),
    ('Others','OTHERS', 0, NOW(), UUID()),
    ('Prefer not to say','PREFER NOT TO SAY', 0, NOW(), UUID()
);
