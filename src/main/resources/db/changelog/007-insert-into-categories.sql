--liquibase formatted sql

--changeset Magdalenacze:001_39
insert into categories (id, name)
values (gen_random_uuid(), 'Music'),
       (gen_random_uuid(), 'Animals'),
       (gen_random_uuid(), 'Travels'),
       (gen_random_uuid(), 'Sport'),
       (gen_random_uuid(), 'Culinary'),
       (gen_random_uuid(), 'Movie'),
       (gen_random_uuid(), 'Computer games'),
       (gen_random_uuid(), 'Mathematics');