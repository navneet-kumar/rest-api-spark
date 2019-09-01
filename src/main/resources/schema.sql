create table products (
product_id serial PRIMARY KEY,
product_name varchar(255) UNIQUE,
product_created timestamp DEFAULT now()
);

insert into products(product_name,product_created) values('Birthday cake','2019-05-10 10:11:23');
insert into products(product_name,product_created) values('Trumpet','2019-05-10 10:11:23');
insert into products(product_name,product_created) values('Tomato','2019-05-10 10:11:23');
insert into products(product_name,product_created) values('Diamond','2019-05-10 10:11:23');

create table purchaser (
purchaser_id serial PRIMARY KEY,
purchaser_name varchar(255) UNIQUE,
purchaser_created timestamp DEFAULT now()
);

insert into purchaser(purchaser_name) values('navneet');

create table transactions (
txn_id serial PRIMARY KEY,
purchaser_id INTEGER REFERENCES purchaser(purchaser_id) ON DELETE SET NULL,
product_id INTEGER REFERENCES products(product_id) ON DELETE SET NULL,
txn_created timestamp DEFAULT now()
);

insert into transactions(purchaser_id,product_id,txn_created) values(1,1,'2019-05-10');
insert into transactions(purchaser_id,product_id,txn_created) values(1,2,'2019-05-10');
insert into transactions(purchaser_id,product_id,txn_created) values(1,3,'2019-04-01');
insert into transactions(purchaser_id,product_id,txn_created) values(1,2,'2019-03-21');
insert into transactions(purchaser_id,product_id,txn_created) values(1,4,'2019-03-21');