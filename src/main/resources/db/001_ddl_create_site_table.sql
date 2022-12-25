create table site (
    id serial primary key not null,
    site varchar(2000) unique not null,
    login varchar(2000) unique not null,
    password varchar(2000)
);