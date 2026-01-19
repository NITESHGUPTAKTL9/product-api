CREATE TABLE products (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    base_price NUMERIC(10,2) NOT NULL,
    country TEXT NOT NULL
);

CREATE TABLE discounts (
    discount_id TEXT PRIMARY KEY,
    percent NUMERIC(5,2) NOT NULL
        CHECK (percent > 0 AND percent < 100)
);

CREATE TABLE product_discounts (
    product_id TEXT NOT NULL,
    discount_id TEXT NOT NULL,
    applied_at TIMESTAMP DEFAULT now(),
    PRIMARY KEY (product_id, discount_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (discount_id) REFERENCES discounts(discount_id)
);