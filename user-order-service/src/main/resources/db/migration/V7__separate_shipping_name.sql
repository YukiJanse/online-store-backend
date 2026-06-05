ALTER TABLE orders
    ADD COLUMN shipping_first_name VARCHAR(100) NOT NULL DEFAULT 'firstname',
    ADD COLUMN shipping_last_name VARCHAR(100) NOT NULL DEFAULT 'lastname';
