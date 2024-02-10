
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

