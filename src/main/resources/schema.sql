CREATE TABLE PARKING_SPOT (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              is_available BOOLEAN NOT NULL
);

CREATE TABLE PARKING_RESERVATION (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     reserved_by VARCHAR(255) NOT NULL,
                                     spot_id BIGINT NOT NULL,
                                     start_time TIMESTAMP NOT NULL,
                                     end_time TIMESTAMP NOT NULL,
                                     FOREIGN KEY (spot_id) REFERENCES PARKING_SPOT(id)
);
