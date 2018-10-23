    alter table if exists course
       drop constraint if exists course_users_fk;

    alter table if exists course
       drop constraint if exists course_topic_fk;

    alter table if exists lesson
       drop constraint if exists lesson_course_fk;

    alter table if exists user_roles
       drop constraint if exists user_roles_users_fk;

    drop table if exists course cascade;

    drop table if exists lesson cascade;

    drop table if exists topic cascade;

    drop table if exists user_roles cascade;

    drop table if exists users cascade;

    drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start 1 increment 1;

    create table course (
       id int8 not null,
        description varchar(2048) not null,
        name varchar(255) not null,
        filename varchar(255) not null,
        fileUrl varchar(2048) not null,
        link varchar(255),
        added_date date,
        user_id int8,
        topic_id varchar(255),
        primary key (id)
);
     create table lesson (
      id int8 not null,
        description varchar(2048) not null,
        name varchar(255),
        link varchar(255),
        course_id int8,
        user_id int8,
        added_date date,
        primary key (id)
);


    create table topic (
       id varchar(255) not null,
        description varchar(2048) not null,
        filename varchar(255),
        name varchar(255) not null,
        primary key (id)
    );

    create table user_roles (
       user_id int8 not null,
        roles varchar(255)
    );

    create table users (
       id int8 not null,
        activation_code varchar(255),
        active boolean,
        birthday date,
        email varchar(255),
        name varchar(255)not null,
        password varchar(255)not null,
        registration_date date,
        surname varchar(255),
        primary key (id)
    );

    alter table if exists course
       add constraint course_users_fk
       foreign key (user_id)
       references users;

    alter table if exists lesson
       add constraint lesson_users_fk
       foreign key (user_id)
       references users;

    alter table if exists course
       add constraint course_topic_fk
       foreign key (topic_id)
       references topic
       on delete cascade;

    alter table if exists lesson
       add constraint lesson_course_fk
       foreign key (course_id)
       references course
       on delete cascade;

    alter table if exists user_roles
       add constraint user_roles_users_fk
       foreign key (user_id)
       references users;
