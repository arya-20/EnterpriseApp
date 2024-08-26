INSERT INTO restaurant (restaurant_id, restaurant_name)
        VALUES('r1', 'Royal Balti');

INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
        VALUES(1, 'basmati rice', 2.5, 'r1');
INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
        VALUES(2, 'chicken korma', 9.5, 'r1');
INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
        VALUES(3, 'tarka dhaal', 4.0, 'r1');
INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
        VALUES(4, 'garlic naan', 3.5, 'r1');
--sequence for id's required for saves of new records in the application class (not when using sql here)
--check MenuItem (infrastructure) for use of sequence
create sequence menu_item_sequence_id start with (select max(menu_item_id) + 1 from menu_item);
