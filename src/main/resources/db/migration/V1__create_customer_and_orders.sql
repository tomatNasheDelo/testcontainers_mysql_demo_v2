CREATE TABLE customers (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(254) NOT NULL,
    city VARCHAR(120),
    created_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id),
    CONSTRAINT uk_customers_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE customer_orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    product VARCHAR(160) NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_customer_orders PRIMARY KEY (id),
    CONSTRAINT fk_customer_orders_customer FOREIGN KEY (customer_id) REFERENCES customers (id),
    CONSTRAINT chk_customer_orders_total CHECK (total > 0)
) ENGINE=InnoDB;

CREATE INDEX idx_customer_orders_customer_id ON customer_orders (customer_id);
