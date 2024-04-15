CREATE TABLE country
(
    id   BIGINT  NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE logo
(
    id        BIGINT NOT NULL PRIMARY KEY,
    name      VARCHAR,
    logo_data BYTEA
        CONSTRAINT fk_city_id FOREIGN KEY (id) REFERENCES city(id) ON DELETE CASCADE

);

CREATE TABLE city
(
    id         BIGINT  NOT NULL PRIMARY KEY,
    name       VARCHAR NOT NULL,
    country_id BIGINT,
        FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE users
(
    id       BIGINT  NOT NULL PRIMARY KEY,
    name     VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    roles    VARCHAR
);
