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
    category_id uuid,
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

--changeset Bartek:001_11

delete from answers;
delete from questions;
delete from categories;


--changeset Bartek_:001_12
insert into categories (id, name) values
    (gen_random_uuid(), 'Historia');


--changeset Bartek_:001_13
insert into questions (id, content, category_id)
values (gen_random_uuid(), 'Gdzie urodził się M. Kopernik?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'W którym roku rozpoczęła się I wojna światowa?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'W którym roku rozpoczęła się II wojna światowa?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'Jak nazywała się I epoka historyczna?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'Ile lat liczy wiek?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'Jak nazywał się I król Polski?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'Gdzie odbywały się słynne obiady czwartkowe?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'Gdzie urodził się Fryderyk Chopin?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'Jak nazywa się język starożytnych Rzymian?', (select id from categories where name = 'Historia')),
       (gen_random_uuid(), 'Jak nazywała się I stolica Polski?', (select id from categories where name = 'Historia'));

--changeset Bartek_:001_14

insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'w Toruniu', true, (select id from questions where content = 'Gdzie urodził się M. Kopernik?')),
       (gen_random_uuid(), 'w Konstantynowie', false, (select id from questions where content = 'Gdzie urodził się M. Kopernik?')),
       (gen_random_uuid(), 'w Warszawie', false, (select id from questions where content = 'Gdzie urodził się M. Kopernik?')),
       (gen_random_uuid(), 'w Krakowie', false, (select id from questions where content = 'Gdzie urodził się M. Kopernik?'));

insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'w 2003r.', false, (select id from questions where content = 'W którym roku rozpoczęła się II wojna światowa?')),
       (gen_random_uuid(), 'w 1918r.', false, (select id from questions where content = 'W którym roku rozpoczęła się II wojna światowa?')),
       (gen_random_uuid(), 'w 1914r.', false, (select id from questions where content = 'W którym roku rozpoczęła się II wojna światowa?')),
       (gen_random_uuid(), 'w 1939r.', true, (select id from questions where content = 'W którym roku rozpoczęła się II wojna światowa?'));

insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'w 2003r.', false, (select id from questions where content = 'W którym roku rozpoczęła się I wojna światowa?')),
       (gen_random_uuid(), 'w 1918r.', false, (select id from questions where content = 'W którym roku rozpoczęła się I wojna światowa?')),
       (gen_random_uuid(), 'w 1914r.', true, (select id from questions where content = 'W którym roku rozpoczęła się I wojna światowa?')),
       (gen_random_uuid(), 'w 1939r.', false, (select id from questions where content = 'W którym roku rozpoczęła się I wojna światowa?'));


insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Prehistoria', false, (select id from questions where content = 'Jak nazywała się I epoka historyczna?')),
       (gen_random_uuid(), 'Starożytność', true, (select id from questions where content = 'Jak nazywała się I epoka historyczna?')),
       (gen_random_uuid(), 'Średniowiecze', false, (select id from questions where content = 'Jak nazywała się I epoka historyczna?')),
       (gen_random_uuid(), 'Nowożytność', false, (select id from questions where content = 'Jak nazywała się I epoka historyczna?'));


insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), '250 lat', false, (select id from questions where content = 'Ile lat liczy wiek?')),
       (gen_random_uuid(), '50 lat', false, (select id from questions where content = 'Ile lat liczy wiek?')),
       (gen_random_uuid(), '100 lat', true, (select id from questions where content = 'Ile lat liczy wiek?')),
       (gen_random_uuid(), '125 lat', false, (select id from questions where content = 'Ile lat liczy wiek?'));


insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Andrzej Duda', false, (select id from questions where content = 'Jak nazywał się I król Polski?')),
       (gen_random_uuid(), 'Paweł Peszko', false, (select id from questions where content = 'Jak nazywał się I król Polski?')),
       (gen_random_uuid(), 'Bolesław Chrobry', true, (select id from questions where content = 'Jak nazywał się I król Polski?')),
       (gen_random_uuid(), 'Elton John', false, (select id from questions where content = 'Jak nazywał się I król Polski?'));

insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'przy stole', false, (select id from questions where content = 'Gdzie odbywały się słynne obiady czwartkowe?')),
       (gen_random_uuid(), 'W pałacu na wodzie w Łazienkach Królewskich', true, (select id from questions where content = 'Gdzie odbywały się słynne obiady czwartkowe?')),
       (gen_random_uuid(), 'w KFC', false, (select id from questions where content = 'Gdzie odbywały się słynne obiady czwartkowe?')),
       (gen_random_uuid(), 'u kolegi Przemka, bramę obok', false, (select id from questions where content = 'Gdzie odbywały się słynne obiady czwartkowe?'));

insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'w szpitalu', false, (select id from questions where content = 'Gdzie urodził się Fryderyk Chopin?')),
       (gen_random_uuid(), 'w Żelazowej Woli', true, (select id from questions where content = 'Gdzie urodził się Fryderyk Chopin?')),
       (gen_random_uuid(), 'w Wadowicach', false, (select id from questions where content = 'Gdzie urodził się Fryderyk Chopin?')),
       (gen_random_uuid(), 'w Łodzi', false, (select id from questions where content = 'Gdzie urodził się Fryderyk Chopin?'));

insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Łacina', false, (select id from questions where content = 'Jak nazywa się język starożytnych Rzymian?')),
       (gen_random_uuid(), 'Włoski', true, (select id from questions where content = 'Jak nazywa się język starożytnych Rzymian?')),
       (gen_random_uuid(), 'Hiszpański', false, (select id from questions where content = 'Jak nazywa się język starożytnych Rzymian?')),
       (gen_random_uuid(), 'Niemiecki', false, (select id from questions where content = 'Jak nazywa się język starożytnych Rzymian?'));


insert into answers (id, content, is_correct,  question_id)
values (gen_random_uuid(), 'Mielno', false, (select id from questions where content = 'Jak nazywała się I stolica Polski?')),
       (gen_random_uuid(), 'Zakopane', true, (select id from questions where content = 'Jak nazywała się I stolica Polski?')),
       (gen_random_uuid(), 'Rzeszów', false, (select id from questions where content = 'Jak nazywała się I stolica Polski?')),
       (gen_random_uuid(), 'Gniezno', false, (select id from questions where content = 'Jak nazywała się I stolica Polski?'));

