-- liquibase formatted sql
--changeset amritkthapa:1
--preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS `email_template`
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    version     BIGINT                NOT NULL,
    name        VARCHAR(255)          NOT NULL,
    template    TEXT        NOT NULL,
    created_date DATETIME              NOT NULL,
    CONSTRAINT pk_email_template PRIMARY KEY (id)
);

