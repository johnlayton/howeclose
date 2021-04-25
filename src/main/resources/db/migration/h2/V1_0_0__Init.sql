CREATE TABLE trade
(
    id       UUID           NOT NULL DEFAULT random_uuid(),
    buyer    TEXT           NOT NULL,
    seller   TEXT           NOT NULL,
    amount   DECIMAL(20, 2) NOT NULL,
    currency TEXT           NOT NULL,
    PRIMARY KEY (id)
);

