CREATE TABLE IF NOT EXISTS person (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      account VARCHAR(255) NOT NULL UNIQUE,
                                      name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS asset (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     symbol VARCHAR(255) NOT NULL,
                                     currency VARCHAR(255) NOT NULL,
                                     amount INT NOT NULL,
                                     last_price DECIMAL(10, 2) NOT NULL,
                                     person_id BIGINT,
                                     FOREIGN KEY (person_id) REFERENCES person(id)
);
