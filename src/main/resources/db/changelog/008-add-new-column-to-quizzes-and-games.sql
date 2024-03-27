--liquibase formatted sql

--changeset Slawek84PL:008_1
alter table quizzes
    add column created_at timestamp;

--changeset RobertoJavaDev:008_2
UPDATE users
SET created_at = CURRENT_TIMESTAMP
WHERE created_at IS NULL;

--changeset RobertoJavaDev:008_3
alter table games
    add column created_at timestamp;