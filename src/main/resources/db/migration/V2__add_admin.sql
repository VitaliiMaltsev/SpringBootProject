insert into users(id, name, password, active)
values (1,'user', '123',true);

insert into user_roles(user_id, roles) VALUES (1,'USER'),(1,'ADMIN');

insert into topic (id, name, description, filename) VALUES ('java','Java', 'Lorem ipsum ...', 'java-bg.jpg');