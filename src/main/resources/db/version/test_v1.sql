create table quizUser
(
    id       uuid         not null primary key,
    name     varchar(100) not null,
    sureName varchar(100) not null,
    nick     varchar(100) not null
);

insert into quizUser (id, name, sureName, nick)
values (random_uuid(), 'Manes', 'Dude', 'BigDaddy');


