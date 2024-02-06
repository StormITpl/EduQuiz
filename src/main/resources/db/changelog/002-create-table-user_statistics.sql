--liquibase formatted sql

--changeset RobertoJavaDev:002_1
create table if not exists user_statistics (
    id                      uuid not null primary key,
    user_id                 uuid,
    last_login_date         timestamp,
    login_count             integer,
    created_quizzes_count   integer,
    solved_quizzes_count    integer
);