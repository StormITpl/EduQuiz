--liquibase formatted sql

--changeset Manes79:001_1
create table if not exists users (
        id       uuid not null primary key,
        nickname varchar(255)
);

--changeset Manes79:001_2
create table if not exists categories (
        id   uuid not null primary key,
        name varchar(255)
);

--changeset Manes79:001_3
create table if not exists quizzes (
        id          uuid not null primary key,
        name        varchar(255),
        category_id uuid,
        user_id     uuid
);

--changeset Manes79:001_4
create table if not exists questions (
        id      uuid not null primary key,
        content varchar(255),
        quiz_id uuid
);

--changeset Manes79:001_5
create table if not exists answers (
        id          uuid not null primary key,
        content     varchar(255),
        is_correct  boolean,
        question_id uuid
);

--changeset Manes79:001_6
create table if not exists game_user_answers (
        game_id uuid,
        user_answers uuid
);

--changeset Manes79:001_7
create table if not exists games (
        id uuid,
        quiz_id uuid
);

--changeset Manes79:001_8
create table if not exists results (
        id uuid,
        score integer,
        game_id uuid,
        quiz_id uuid
);