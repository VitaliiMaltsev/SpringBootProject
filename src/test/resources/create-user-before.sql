delete from user_roles;
delete from users;

insert into users (id, active, password, name) values
(1, true , '$2a$08$0IiTz9wk3Ij0mWhl6ZoGcessyKaKM9Rfz9nV9jUeD9NDeciE0oBsW', '111' ),
(2, true, '$2a$08$0IiTz9wk3Ij0mWhl6ZoGcessyKaKM9Rfz9nV9jUeD9NDeciE0oBsW' , 'mike');

insert into user_roles(user_id, roles) values
(1,'USER'), (1, 'ADMIN'),(2,'USER');