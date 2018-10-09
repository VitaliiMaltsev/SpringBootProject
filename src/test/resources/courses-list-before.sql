delete from course;

insert into course(id, description, name, user_id, topic_id) values
(1,'Lorem ipsum','first',1,'java'),
(2,'Lorem ipsum','second',2,'java'),
(3,'Lorem ipsum','third',2,'java'),
(41,'Lorem ipsum','fourth',1,'java');

alter sequence hibernate_sequence restart with 10;