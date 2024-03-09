CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS cart_data
(
    id         BIGSERIAL PRIMARY KEY,
    cart_number VARCHAR(255),
    exp_date    VARCHAR(255),
    cvv         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS customer
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    country    VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transaction
(
    transaction_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_method   VARCHAR(255),
    amount           DOUBLE PRECISION,
    currency         VARCHAR(255),
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    language         VARCHAR(255),
    notification_url VARCHAR(255),
    status           VARCHAR(255),
    message          VARCHAR(255),
    customer_id      INT,
    cart_data_id     INT,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (cart_data_id) REFERENCES cart_data (id)
);

CREATE TABLE IF NOT EXISTS payout
(
    payout_id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_method   VARCHAR(255),
    amount           DOUBLE PRECISION,
    currency         VARCHAR(255),
    language         VARCHAR(255),
    notification_url VARCHAR(255),
    status           VARCHAR(255),
    message          VARCHAR(255),
    customer_id      BIGINT,
    cart_data_id     BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (cart_data_id) REFERENCES cart_data (id)
);

CREATE TABLE IF NOT EXISTS merchant
(
    merchant_id VARCHAR(36) PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    secret_key VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS wallet
(
    id BIGSERIAL PRIMARY KEY,
    merchant_id VARCHAR(36) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0,
    currency VARCHAR(5) NOT NULL,
    FOREIGN KEY (merchant_id) REFERENCES merchant(merchant_id)
    );

CREATE TABLE IF NOT EXISTS call_back
(
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payload_id uuid NOT NULL,
    FOREIGN KEY (payload_id) REFERENCES payout(payout_id)
    );

ALTER TABLE transaction
    ADD COLUMN IF NOT EXISTS wallet_id BIGINT,
    ADD FOREIGN KEY (wallet_id) REFERENCES wallet(id);

ALTER TABLE payout
    ADD COLUMN IF NOT EXISTS wallet_id BIGINT,
    ADD FOREIGN KEY (wallet_id) REFERENCES wallet(id);
