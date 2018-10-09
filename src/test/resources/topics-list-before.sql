delete from topic;

insert into topic(id, description, name) values
('html','Lorem ipsum','my-name'),
('javaee','Lorem ipsum','second'),
('java','Lorem ipsum','third'),
('javascript','Lorem ipsum','my-name');

alter sequence hibernate_sequence restart with 10;