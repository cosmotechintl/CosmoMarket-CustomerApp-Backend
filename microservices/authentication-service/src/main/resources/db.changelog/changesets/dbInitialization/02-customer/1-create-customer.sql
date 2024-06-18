-- liquibase formatted sql
--changeset amritkthapa:1
--preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS `customer`
(
    id                           BIGINT AUTO_INCREMENT NOT NULL,
    version                      BIGINT                NOT NULL,
    name                         VARCHAR(255)          NOT NULL,
    password                     VARCHAR(255)          NULL,
    username                     VARCHAR(255)          NOT NULL,
    is_active                    BIT(1)                NULL,
    email                        VARCHAR(255)          NOT NULL,
    mobile_number                VARCHAR(255)          NULL,
    address                      VARCHAR(255)          NULL,
    status                       BIGINT                NOT NULL,
    registered_date              datetime              NULL,
    password_changed_date        datetime              NULL,
    last_logged_in_time          datetime              NULL,
    wrong_password_attempt_count INT                   NULL,
    profile_picture_name         VARCHAR(255)          NULL,
    otp_auth_secret              VARCHAR(255)          NULL,
    two_factor_enabled           BIT(1)                NOT NULL,
    wrong_oto_auth_attempt_count INT                   NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
    );

