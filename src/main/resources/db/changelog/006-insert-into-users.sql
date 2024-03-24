--liquibase formatted sql

--changeset Slawek84PL:006_1
insert into users
    (id, nickname, created_at, email, password, role, status)
values (gen_random_uuid(), 'admin', CURRENT_TIMESTAMP, 'admin@storm.it',
        '$2a$10$yUGAMyJTAfcJsvga0bf18esKYZw4pms4aMCxveyF8E6WXYj6EsMHe',
        'ROLE_ADMIN', 'VERIFIED'),
       (gen_random_uuid(), 'user', CURRENT_TIMESTAMP, 'user@storm.it',
        '$2a$10$Ww13KQ2gy2w8IqmL2RKdfO4kST5rAraE1jIH0L8VYkhJSekHDX2.O',
        'ROLE_USER', 'VERIFIED');