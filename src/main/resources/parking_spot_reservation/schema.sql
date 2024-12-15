CREATE TABLE parking_spot (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              is_available BOOLEAN NOT NULL,
                              has_ev_charging BOOLEAN NOT NULL
);

CREATE TABLE parking_reservation (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     reserved_by VARCHAR(255) NOT NULL,
                                     spot_id BIGINT NOT NULL,
                                     start_time TIMESTAMP NOT NULL,
                                     end_time TIMESTAMP NOT NULL,
                                     FOREIGN KEY (spot_id) REFERENCES parking_spot(id)
);
