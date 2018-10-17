create table course_likes (
user_id bigint not null references users,
course_id bigint not null references course,
primary key(user_id, course_id)
);