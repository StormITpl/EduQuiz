--liquibase formatted sql

--changeset Manes79:002_1
insert into users (id, nickname)
values (gen_random_uuid(), 'Ananiasz');

--changeset Manes79:002_2
insert into categories (id, name)
values (gen_random_uuid(), 'Programming');

--changeset Manes79:002_3
insert into quizzes (id, name, category_id, user_id)
values (gen_random_uuid(), 'Data Structure',
        (select id from categories where name = 'Programming'),
        (select id from users where nickname = 'Ananiasz'));

--changeset Manes79:002_4
insert into questions (id, content, quiz_id)
values (gen_random_uuid(), 'The interface that implements the compareTo() method is:',
        (select id from quizzes where name = 'Data Structure'));

--changeset Manes79:002_5
insert into answers (id, content, is_correct, question_id)
values (gen_random_uuid(), 'Comparable', true,
        (select id from questions where content = 'The interface that implements the compareTo() method is:'));

--changeset Bartek_:002_6
insert into categories (id, name)
values (gen_random_uuid(), 'History');

--changeset Bartek_:002_7
insert into quizzes (id, name, category_id, user_id)
values (gen_random_uuid(), 'General knowledge of world history',
        (select id from categories where name = 'History'),
        (select id from users where nickname = 'Ananiasz'));