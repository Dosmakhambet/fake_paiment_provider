CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) UNIQUE,
    password   VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    role       VARCHAR(255),
    enabled    BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);