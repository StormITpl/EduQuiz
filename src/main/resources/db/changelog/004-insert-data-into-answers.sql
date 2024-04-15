--liquibase formatted sql

--changeset Bartek_:004_1
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'in Torun', true,
        (select id from questions where content = 'Where was Nicolaus Copernicus born?')),
       (gen_random_uuid(), 'in Konstantinov', false,
        (select id from questions where content = 'Where was Nicolaus Copernicus born?')),
       (gen_random_uuid(), 'in Warsaw', false,
        (select id from questions where content = 'Where was Nicolaus Copernicus born?')),
       (gen_random_uuid(), 'in Cracow', false,
        (select id from questions where content = 'Where was Nicolaus Copernicus born?'));

--changeset Bartek_:004_2
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'in 2003', false,
        (select id from questions where content = 'In what year did World War II begin?')),
       (gen_random_uuid(), 'in 1918', false,
        (select id from questions where content = 'In what year did World War II begin?')),
       (gen_random_uuid(), 'in 1914', false,
        (select id from questions where content = 'In what year did World War II begin?')),
       (gen_random_uuid(), 'in 1939', true,
        (select id from questions where content = 'In what year did World War II begin?'));

--changeset Bartek_:004_3
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'in 2003', false,
        (select id from questions where content = 'In what year did World War I begin?')),
       (gen_random_uuid(), 'in 1918', false,
        (select id from questions where content = 'In what year did World War I begin?')),
       (gen_random_uuid(), 'in 1914', true,
        (select id from questions where content = 'In what year did World War I begin?')),
       (gen_random_uuid(), 'in 1939', false,
        (select id from questions where content = 'In what year did World War I begin?'));

--changeset Bartek_:004_4
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'Prehistory', false,
        (select id from questions where content = 'What was the name of the First Historical Era?')),
       (gen_random_uuid(), 'Antiquity', true,
        (select id from questions where content = 'What was the name of the First Historical Era?')),
       (gen_random_uuid(), 'The Middle Ages', false,
        (select id from questions where content = 'What was the name of the First Historical Era?')),
       (gen_random_uuid(), 'The Modern Age', false,
        (select id from questions where content = 'What was the name of the First Historical Era?'));

--changeset Bartek_:004_5
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), '250 years', false, (select id from questions where content = 'How old is one century?')),
       (gen_random_uuid(), '50 years', false, (select id from questions where content = 'How old is one century?')),
       (gen_random_uuid(), '100 years', true, (select id from questions where content = 'How old is one century?')),
       (gen_random_uuid(), '125 years', false, (select id from questions where content = 'How old is one century?'));

--changeset Bartek_:004_6
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'Andrew Dude', false,
        (select id from questions where content = 'What was the name of the 1st king of Poland?')),
       (gen_random_uuid(), 'Pavel Peszko', false,
        (select id from questions where content = 'What was the name of the 1st king of Poland?')),
       (gen_random_uuid(), 'Boleslaw the Brave', true,
        (select id from questions where content = 'What was the name of the 1st king of Poland?')),
       (gen_random_uuid(), 'Elton John', false,
        (select id from questions where content = 'What was the name of the 1st king of Poland?'));

--changeset Bartek_:004_7
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'at the table', false,
        (select id from questions where content = 'Where were the famous Thursday dinners held?')),
       (gen_random_uuid(), 'at the Palace on the Water in the Royal Baths Park', true,
        (select id from questions where content = 'Where were the famous Thursday dinners held?')),
       (gen_random_uuid(), 'at KFC', false,
        (select id from questions where content = 'Where were the famous Thursday dinners held?')),
       (gen_random_uuid(), 'At Przemek''s friend, the gate next door', false,
        (select id from questions where content = 'Where were the famous Thursday dinners held?'));

--changeset Bartek_:004_8
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'in the hospital', false,
        (select id from questions where content = 'Where was Frederic Chopin born?')),
       (gen_random_uuid(), 'in Zelazowa Wola', true,
        (select id from questions where content = 'Where was Frederic Chopin born?')),
       (gen_random_uuid(), 'in Wadowice', false,
        (select id from questions where content = 'Where was Frederic Chopin born?')),
       (gen_random_uuid(), 'in Lodz', false,
        (select id from questions where content = 'Where was Frederic Chopin born?'));

--changeset Bartek_:004_9
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'Latin', false,
        (select id from questions where content = 'What is the name of the language of the ancient Romans?')),
       (gen_random_uuid(), 'Italian', true,
        (select id from questions where content = 'What is the name of the language of the ancient Romans?')),
       (gen_random_uuid(), 'Spanish', false,
        (select id from questions where content = 'What is the name of the language of the ancient Romans?')),
       (gen_random_uuid(), 'German', false,
        (select id from questions where content = 'What is the name of the language of the ancient Romans?'));

--changeset Bartek_:004_10
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'Mielno', false,
        (select id from questions where content = 'What was the name of the 1st capital of Poland?')),
       (gen_random_uuid(), 'Zakopane', true,
        (select id from questions where content = 'What was the name of the 1st capital of Poland?')),
       (gen_random_uuid(), 'Rzeszów', false,
        (select id from questions where content = 'What was the name of the 1st capital of Poland?')),
       (gen_random_uuid(), 'Gniezno', false,
        (select id from questions where content = 'What was the name of the 1st capital of Poland?'));