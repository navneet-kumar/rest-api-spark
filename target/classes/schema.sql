create table products (
product_id serial PRIMARY KEY,
product_name varchar(255) UNIQUE,
product_created timestamp DEFAULT now()
);

create table purchaser (
purchaser_id serial PRIMARY KEY,
purchaser_name varchar(255) UNIQUE,
purchaser_created timestamp DEFAULT now()
);

create table transactions (
txn_id serial PRIMARY KEY,
purchaser_id INTEGER REFERENCES purchaser(purchaser_id) ON DELETE SET NULL,
product_id INTEGER REFERENCES products(product_id) ON DELETE SET NULL,
txn_created timestamp DEFAULT now()
);
