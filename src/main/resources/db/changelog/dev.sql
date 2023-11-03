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

--changeset Manes79:001_9
insert into users (id, nickname)
values (gen_random_uuid(), 'Ananiasz');

--changeset Manes79:001_10
insert into categories (id, name)
values (gen_random_uuid(), 'Programming');

--changeset Manes79:001_11
insert into quizzes (id, name, category_id, user_id)
values (gen_random_uuid(), 'Data Structure',
        (select id from categories where name = 'Programming'),
        (select id from users where nickname = 'Ananiasz'));

--changeset Manes79:001_12
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'The interface that implements the compareTo() method is:',
        (select id from quizzes where name = 'Data Structure'));

--changeset Manes79:001_13
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'Comparable', true,
        (select id from questions where content = 'The interface that implements the compareTo() method is:'));

--changeset Bartek_:001_14
insert into categories (id, name)
values (gen_random_uuid(), 'History');

--changeset Bartek_:001_15
insert into quizzes (id, name, category_id, user_id)
values (gen_random_uuid(), 'General knowledge of world history',
        (select id from categories where name = 'History'),
        (select id from users where nickname = 'Ananiasz'));

--changeset Bartek_:001_16
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'Where was Nicolaus Copernicus born?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_17
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'In what year did World War I begin?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_18
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'In what year did World War II begin?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_19
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What was the name of the First Historical Era?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_20
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'How old is one century?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_21
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What was the name of the 1st king of Poland?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_22
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'Where were the famous Thursday dinners held?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_23
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'Where was Frederic Chopin born?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_24
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What is the name of the language of the ancient Romans?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_25
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What was the name of the 1st capital of Poland?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:001_26
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'in Torun', true, (select id from questions where content = 'Where was Nicolaus Copernicus born?')),
       (gen_random_uuid(), 'in Konstantinov', false, (select id from questions where content = 'Where was Nicolaus Copernicus born?')),
       (gen_random_uuid(), 'in Warsaw', false, (select id from questions where content = 'Where was Nicolaus Copernicus born?')),
       (gen_random_uuid(), 'in Cracow', false, (select id from questions where content = 'Where was Nicolaus Copernicus born?'));

--changeset Bartek_:001_27
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'in 2003', false, (select id from questions where content = 'In what year did World War II begin?')),
       (gen_random_uuid(), 'in 1918', false, (select id from questions where content = 'In what year did World War II begin?')),
       (gen_random_uuid(), 'in 1914', false, (select id from questions where content = 'In what year did World War II begin?')),
       (gen_random_uuid(), 'in 1939', true, (select id from questions where content = 'In what year did World War II begin?'));

--changeset Bartek_:001_28
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'in 2003', false, (select id from questions where content = 'In what year did World War I begin?')),
       (gen_random_uuid(), 'in 1918', false, (select id from questions where content = 'In what year did World War I begin?')),
       (gen_random_uuid(), 'in 1914', true, (select id from questions where content = 'In what year did World War I begin?')),
       (gen_random_uuid(), 'in 1939', false, (select id from questions where content = 'In what year did World War I begin?'));

--changeset Bartek_:001_29
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Prehistory', false, (select id from questions where content = 'What was the name of the First Historical Era?')),
       (gen_random_uuid(), 'Antiquity', true, (select id from questions where content = 'What was the name of the First Historical Era?')),
       (gen_random_uuid(), 'The Middle Ages', false, (select id from questions where content = 'What was the name of the First Historical Era?')),
       (gen_random_uuid(), 'The Modern Age', false, (select id from questions where content = 'What was the name of the First Historical Era?'));

--changeset Bartek_:001_30
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), '250 years', false, (select id from questions where content = 'How old is one century?')),
       (gen_random_uuid(), '50 years', false, (select id from questions where content = 'How old is one century?')),
       (gen_random_uuid(), '100 years', true, (select id from questions where content = 'How old is one century?')),
       (gen_random_uuid(), '125 years', false, (select id from questions where content = 'How old is one century?'));

--changeset Bartek_:001_31
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Andrew Dude', false, (select id from questions where content = 'What was the name of the 1st king of Poland?')),
       (gen_random_uuid(), 'Pavel Peszko', false, (select id from questions where content = 'What was the name of the 1st king of Poland?')),
       (gen_random_uuid(), 'Boleslaw the Brave', true, (select id from questions where content = 'What was the name of the 1st king of Poland?')),
       (gen_random_uuid(), 'Elton John', false, (select id from questions where content = 'What was the name of the 1st king of Poland?'));

--changeset Bartek_:001_32
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'at the table', false, (select id from questions where content = 'Where were the famous Thursday dinners held?')),
       (gen_random_uuid(), 'at the Palace on the Water in the Royal Baths Park', true, (select id from questions where content = 'Where were the famous Thursday dinners held?')),
       (gen_random_uuid(), 'at KFC', false, (select id from questions where content = 'Where were the famous Thursday dinners held?')),
       (gen_random_uuid(), 'At Przemek''s friend, the gate next door', false, (select id from questions where content = 'Where were the famous Thursday dinners held?'));

--changeset Bartek_:001_33
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'in the hospital', false, (select id from questions where content = 'Where was Frederic Chopin born?')),
       (gen_random_uuid(), 'in Zelazowa Wola', true, (select id from questions where content = 'Where was Frederic Chopin born?')),
       (gen_random_uuid(), 'in Wadowice', false, (select id from questions where content = 'Where was Frederic Chopin born?')),
       (gen_random_uuid(), 'in Lodz', false, (select id from questions where content = 'Where was Frederic Chopin born?'));

--changeset Bartek_:001_34
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Latin', false, (select id from questions where content = 'What is the name of the language of the ancient Romans?')),
       (gen_random_uuid(), 'Italian', true, (select id from questions where content = 'What is the name of the language of the ancient Romans?')),
       (gen_random_uuid(), 'Spanish', false, (select id from questions where content = 'What is the name of the language of the ancient Romans?')),
       (gen_random_uuid(), 'German', false, (select id from questions where content = 'What is the name of the language of the ancient Romans?'));

--changeset Bartek_:001_35
insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Mielno', false, (select id from questions where content = 'What was the name of the 1st capital of Poland?')),
       (gen_random_uuid(), 'Zakopane', true, (select id from questions where content = 'What was the name of the 1st capital of Poland?')),
       (gen_random_uuid(), 'Rzeszów', false, (select id from questions where content = 'What was the name of the 1st capital of Poland?')),
       (gen_random_uuid(), 'Gniezno', false, (select id from questions where content = 'What was the name of the 1st capital of Poland?'));

--changeset RobertoJavaDev:001_36
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

--changeset RobertoJavaDev:001_37
alter table users
alter column password type varchar;

--changeset Slawek84PL:001_38
insert into users
    (id, nickname, created_at, email, password, role, status)
    values (
        gen_random_uuid(), 'admin', CURRENT_TIMESTAMP, 'admin@storm.it',
        '$2a$10$yUGAMyJTAfcJsvga0bf18esKYZw4pms4aMCxveyF8E6WXYj6EsMHe',
        'ROLE_ADMIN', 'VERIFIED'),
        (gen_random_uuid(), 'user', CURRENT_TIMESTAMP, 'user@storm.it',
        '$2a$10$Ww13KQ2gy2w8IqmL2RKdfO4kST5rAraE1jIH0L8VYkhJSekHDX2.O',
        'ROLE_USER', 'VERIFIED');

--changeset Magdalenacze:001_39
insert into categories (id, name) values
                                      (gen_random_uuid(), 'Music'),
                                      (gen_random_uuid(), 'Animals'),
                                      (gen_random_uuid(), 'Travels'),
                                      (gen_random_uuid(), 'Sport'),
                                      (gen_random_uuid(), 'Culinary'),
                                      (gen_random_uuid(), 'Movie'),
                                      (gen_random_uuid(), 'Computer games'),
                                      (gen_random_uuid(), 'Mathematics');




