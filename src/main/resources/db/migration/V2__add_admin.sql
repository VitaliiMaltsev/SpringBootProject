insert into users(id, name, password, active, registration_date, email) values
 (1,'admin', '123',true, '2018-07-01', 'admin@test.com'),
 (2,'user', '123',true, '2018-07-04', 'user@test.com'),
 (3,'111', '111',true, '2018-09-18', 'dbdfbfd@pay-mon.com'),
 (4,'222', '222',true, '2018-09-17', 'bdb@pay-mon.com');

insert into user_roles(user_id, roles) VALUES
 (1,'USER'),
 (2,'USER'),
 (3,'USER'),
 (4,'USER'),
 (1,'ADMIN');

insert into topic (id, name, description, filename) VALUES
  ('java','Java', 'Курсы Java', 'java1.jpg'),
  ('javaee','Java EE', 'Курсы Java EE', 'javaEE.jpg'),
  ('javascript','JavaScript', 'Курсы по JavaScript', 'javascript2.jpg'),
  ('html-css','HTML+CSS', 'Курсы HTML+CSS', 'html.jpg'),
  ('spring','Spring', 'Курсы Spring', 'spring.jpg'),
  ('links','Useful links', 'Useful links', 'Useful-Links-B.jpg');