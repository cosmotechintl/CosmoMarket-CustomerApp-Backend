-- liquibase formatted sql
--changeset kirankhanal:1
--preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS gender
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    version       BIGINT                NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NOT NULL,
    uuid           VARCHAR(255)          NOT NULL,
    created_at    DATETIME             NOT NULL,
    updated_at    DATETIME             NULL,
    CONSTRAINT pk_status PRIMARY KEY (id)
);
--changeset kirankhanal:2
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0  SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_schema = (SELECT DATABASE()) AND table_name = 'gender' AND constraint_name = 'uc_gender_name'
ALTER TABLE status
    ADD CONSTRAINT uc_gender_name UNIQUE (name);
