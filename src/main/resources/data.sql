INSERT INTO PARKING_SPOT (is_available) VALUES (true);
INSERT INTO PARKING_SPOT (is_available) VALUES (true);
INSERT INTO PARKING_SPOT (is_available) VALUES (false);

INSERT INTO PARKING_RESERVATION (reserved_by, spot_id, start_time, end_time)
VALUES ('user1', 3, '2024-12-15 08:00:00', '2024-12-15 10:00:00');
INSERT INTO PARKING_RESERVATION (reserved_by, spot_id, start_time, end_time)
VALUES ('user2', 3, '2024-12-15 10:00:00', '2024-12-15 12:00:00');
