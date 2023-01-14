
create table if not exists quizUser
(
    id          uuid not null primary key,
    name        varchar(100) not null,
    secondName  varchar(100) not null
)