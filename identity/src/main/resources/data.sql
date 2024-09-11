insert into role (type) values ('STAFF');
insert into role (type) values ('MANAGER');
-- insert into role (type) values ('ADMIN');

insert into app_user (id, username, password, first_name, surname, address, role_id)
values ('0000',
        'staff',
        'password',
        'first1',
        'surname1',
        '24, cheers drive, Bristol',
        1);

insert into app_user (id, username,password, first_name, surname, address, role_id)
values ('0001',
        'manager',
        'password',
        'first2',
        'surname2',
        '5, java road',
        2);

insert into app_user (id, username,password, first_name, surname, address, role_id)
values ('0002',
        'admin',
        'password',
        'first2',
        'surname2',
        '93, monaco',
        2);