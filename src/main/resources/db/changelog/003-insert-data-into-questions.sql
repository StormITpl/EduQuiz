--liquibase formatted sql

--changeset Bartek_:003_1
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'Where was Nicolaus Copernicus born?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_2
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'In what year did World War I begin?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_3
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'In what year did World War II begin?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_4
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What was the name of the First Historical Era?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_5
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'How old is one century?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_6
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What was the name of the 1st king of Poland?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_7
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'Where were the famous Thursday dinners held?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_8
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'Where was Frederic Chopin born?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_9
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What is the name of the language of the ancient Romans?',
        (select id from quizzes where name = 'General knowledge of world history'));

--changeset Bartek_:003_10
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'What was the name of the 1st capital of Poland?',
        (select id from quizzes where name = 'General knowledge of world history'));