-- liquibase formatted sql
--changeset amritkthapa:1
--preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS `customer_email_log`
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    version     BIGINT                NOT NULL,
    email       VARCHAR(255)          NOT NULL,
    customer    BIGINT                NOT NULL,
    message     TEXT                  NOT NULL,
    is_sent     BOOLEAN,
    is_expired  BOOLEAN DEFAULT FALSE,
    otp         VARCHAR(255)          NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_customer_email_log PRIMARY KEY (id)
    );

--changeset amritkthapa:2
--preconditions onFail:CONTINUE onError:HALT
CREATE EVENT IF NOT EXISTS update_is_expired
ON SCHEDULE EVERY 1 MINUTE
DO
BEGIN
UPDATE customer_email_log
SET is_expired = TRUE
WHERE created_at <= NOW() - INTERVAL 2 MINUTE AND is_expired = FALSE;
END;

--changeset amritkthapa:3
--preconditions onFail:CONTINUE onError:HALT
SET GLOBAL event_scheduler = ON;
