CREATE TABLE restaurant(
       restaurant_id VARCHAR PRIMARY KEY,
       restaurant_name VARCHAR NOT NULL
);
CREATE TABLE menu_item(
       menu_item_id LONG PRIMARY KEY,
       menu_name VARCHAR NOT NULL,
       menu_price DECIMAL(6,2) NOT NULL,
       restaurant_id VARCHAR NOT NULL,
       FOREIGN KEY(restaurant_id) REFERENCES restaurant(restaurant_id)
);