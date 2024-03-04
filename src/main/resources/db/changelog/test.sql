--liquibase formatted sql

--changeset k4t:002_1 context:"test"
create table if not exists users (
    id uuid not null primary key,
    nickname varchar(255),
    created_at timestamp,
    email varchar(120),
    password varchar(20),
    role varchar(255),
    status varchar(255)
);

alter table users
alter column password type varchar;

--changeset k4t:002_2 context:"test"
create table if not exists categories (
    id uuid not null primary key,
    name varchar(255)
);

--changeset k4t:002_3 context:"test"
create table if not exists quizzes (
    id uuid not null primary key,
    name varchar(255),
    category_id uuid,
    user_id uuid,
    created_at timestamp
);

--changeset k4t:002_4 context:"test"
create table if not exists questions (
    id uuid not null primary key,
    content varchar(255),
    quiz_id uuid
);

--changeset k4t:002_5 context:"test"
create table if not exists answers (
    id uuid not null primary key,
    content varchar(255),
    is_correct boolean,
    question_id uuid
);

--changeset k4t:002_6 context:"test"
create table if not exists game_user_answers (
    game_id uuid,
    user_answers uuid
);

--changeset k4t:002_7 context:"test"
create table if not exists games (
    id uuid not null,
    quiz_id uuid,
    created_at timestamp,
    primary key(id)
);

--changeset k4t:002_8 context:"test"
create table if not exists results (
    id uuid not null,
    score integer,
    game_id uuid,
    quiz_id uuid,
    primary key(id)
);

--changeset k4t:002_9 context:"test"
insert into users (id, nickname, created_at, email, password, role, status)
values
    (RANDOM_UUID(), 'TestUser1', CURRENT_TIMESTAMP, 'testuser1@gmail.com', '$2a$10$yUGAMyJTAfcJsvga0bf18esKYZw4pms4aMCxveyF8E6WXYj6EsMHe', 'ROLE_USER', 'VERIFIED'),
    (RANDOM_UUID(), 'TestUser2', CURRENT_TIMESTAMP, 'testuser2@gmail.com', '$2a$10$Ww13KQ2gy2w8IqmL2RKdfO4kST5rAraE1jIH0L8VYkhJSekHDX2.O', 'ROLE_ADMIN', 'VERIFIED');

--changeset k4t:002_10 context:"test"
insert into users (id, nickname, created_at, email, password, role, status)
values
    (RANDOM_UUID(), 'TestUser3', DATEADD('DAY', -2, CURRENT_DATE()), 'testuser2days@k4townia.com', 'password', 'ROLE_USER', 'VERIFIED'),
    (RANDOM_UUID(), 'TestUser4', DATEADD('DAY', -10, CURRENT_DATE()), 'testuser10days@k4townia.com', 'password', 'ROLE_USER', 'VERIFIED'),
    (RANDOM_UUID(), 'TestUser5', DATEADD('DAY', -20, CURRENT_DATE()), 'testuser20days@k4townia.com', 'password', 'ROLE_USER', 'VERIFIED'),
    (RANDOM_UUID(), 'TestUser6', DATEADD('DAY', -32, CURRENT_DATE()), 'testuser32days@k4townia.com', 'password', 'ROLE_USER', 'VERIFIED');

