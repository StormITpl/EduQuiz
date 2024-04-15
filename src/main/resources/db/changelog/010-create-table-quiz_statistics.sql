--liquibase formatted sql

--changeset Slawek84PL:003_1
create table if not exists quiz_statistics
(
    id         uuid not null primary key,
    game_id    uuid,
    user_id    uuid,
    score      integer,
    created_at timestamp,
    duration   integer
);