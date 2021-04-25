CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE trade
(
    id       UUID           NOT NULL DEFAULT uuid_generate_v4(),
    buyer    TEXT           NOT NULL,
    seller   TEXT           NOT NULL,
    amount   DECIMAL(20, 2) NOT NULL,
    currency TEXT           NOT NULL,
    PRIMARY KEY (id)
);

