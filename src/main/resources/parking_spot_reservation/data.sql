-- Insert parking spots
INSERT INTO parking_spot (is_available, has_ev_charging) VALUES (true, true);
INSERT INTO parking_spot (is_available, has_ev_charging) VALUES (true, false);
INSERT INTO parking_spot (is_available, has_ev_charging) VALUES (false, true);

-- Insert parking reservations
INSERT INTO parking_reservation (reserved_by, spot_id, start_time, end_time)
VALUES ('user1', 3, '2024-12-15 08:00:00', '2024-12-15 10:00:00');
INSERT INTO parking_reservation (reserved_by, spot_id, start_time, end_time)
VALUES ('user2', 3, '2024-12-15 10:00:00', '2024-12-15 12:00:00');
