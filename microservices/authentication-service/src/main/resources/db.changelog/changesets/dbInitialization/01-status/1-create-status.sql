-- liquibase formatted sql
--changeset amritkthapa:1
--preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS status
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    version       BIGINT                NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NOT NULL,
    icon          VARCHAR(255)          NULL,
    CONSTRAINT pk_status PRIMARY KEY (id)
    );

--changeset amritkthapa:2
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0  SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_schema = (SELECT DATABASE()) AND table_name = 'status' AND constraint_name = 'uc_status_name'
ALTER TABLE status
    ADD CONSTRAINT uc_status_name UNIQUE (name);


