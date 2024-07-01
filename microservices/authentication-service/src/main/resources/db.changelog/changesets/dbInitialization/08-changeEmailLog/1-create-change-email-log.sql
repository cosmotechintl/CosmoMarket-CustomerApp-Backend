-- liquibase formatted sql
--changeset kirankhanaleo:1
--preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS `change_email_log`
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    version     BIGINT                NOT NULL,
    email       VARCHAR(255)          NOT NULL,
    customer    BIGINT                NOT NULL,
    message     TEXT                  NOT NULL,
    is_sent     BOOLEAN,
    is_otp_verified BOOLEAN,
    otp         VARCHAR(255)          NOT NULL,
    timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_change_email_log PRIMARY KEY (id),
    CONSTRAINT fk_change_email_log FOREIGN KEY (customer) REFERENCES customer(id)
    );