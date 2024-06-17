-- liquibase formatted sql
--changeset amritkthapa:1

--preconditions onFail:CONTINUE onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM status
INSERT INTO status (description, icon, name, version)
VALUES
    ('ACTIVE', 'active', 'ACTIVE', 0),
    ('DELETED', 'deleted', 'DELETED', 0),
    ('PENDING', 'pending', 'PENDING', 0),
    ('BLOCKED', 'blocked', 'BLOCKED', 0);