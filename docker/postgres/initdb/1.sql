create table "user"
(
    user_id serial
        constraint user_pk
            primary key,
    first_name varchar(32) not null,
    last_name varchar(32) not null
);
