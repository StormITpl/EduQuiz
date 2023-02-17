--liquibase formatted sql

--changeset Manes79:001_1
create table if not exists users
(
    id       uuid not null primary key,
    nickname varchar(255)
);

--changeset Manes79:001_2
create table if not exists categories
(
    id   uuid not null primary key,
    name varchar(255)
);

--changeset Manes79:001_3
create table if not exists quizzes
(
    id          uuid not null primary key,
    name        varchar(255),
    category_id uuid,
    user_id     uuid
);

--changeset Manes79:001_4
create table if not exists questions
(
    id      uuid not null primary key,
    content varchar(255),
    quiz_id uuid
);

--changeset Manes79:001_5
create table if not exists answers
(
    id          uuid not null primary key,
    content     varchar(255),
    is_correct  boolean,
    question_id uuid
);

--changeset Manes79:001_6
insert into users (id, nickname)
values (gen_random_uuid(), 'Ananiasz');

--changeset Manes79:001_7
insert into categories (id, name)
values (gen_random_uuid(), 'Programming');

--changeset Manes79:001_8
insert into quizzes (id, name, category_id, user_id)
values (gen_random_uuid(), 'Data Structure',
        (select id from categories where name = 'Programming'),
        (select id from users where nickname = 'Ananiasz'));

--changeset Manes79:001_9
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'The interface that implements the compareTo() method is:',
        (select id from quizzes where name = 'Data Structure'));

--changeset Manes79:001_10
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'Comparable', true,
        (select id from questions where content = 'The interface that implements the compareTo() method is:'));
