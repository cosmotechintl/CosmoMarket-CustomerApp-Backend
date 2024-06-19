-- liquibase formatted sql
--changeset amritkthapa:1

--preconditions onFail:CONTINUE onError:HALT
INSERT INTO booking (version, vendor_id, customer_name, mobile_number, date, start_time, end_time, is_booked, amount)
VALUES (0, 1, 'Amrit Thapa', '9840140855', '2024-06-18', '06:00:00', '70:00:00', 0, '1000');