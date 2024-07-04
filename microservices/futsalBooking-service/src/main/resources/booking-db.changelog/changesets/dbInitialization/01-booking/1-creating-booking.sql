-- liquibase formatted sql
-- changeset amritkthapa:1
-- preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS futsal_booking (
    id              BIGINT AUTO_INCREMENT   NOT NULL,
    version         BIGINT                  NOT NULL,
    vendor_code     VARCHAR(255)            NOT NULL,
    customer_name   VARCHAR(255),
    mobile_number   VARCHAR(255),
    date            DATE                    NOT NULL,
    start_time      TIME                    NOT NULL,
    end_time        TIME                    NOT NULL,
    is_booked       BIT(1)                  NOT NULL,
    amount          VARCHAR(255)            NOT NULL,
    PRIMARY KEY (id)
);