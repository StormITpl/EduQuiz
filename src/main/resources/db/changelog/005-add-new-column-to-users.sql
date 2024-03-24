--liquibase formatted sql

--changeset RobertoJavaDev:005_1
alter table users
    add column created_at timestamp;

alter table users
    add column email varchar(120);

alter table users
    add column password varchar(20);

alter table users
    add column role varchar(255);

alter table users
    add column status varchar(255);

--changeset RobertoJavaDev:005_2
alter table users
    alter column password type varchar;