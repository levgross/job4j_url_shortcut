create table site (
    id serial primary key not null,
    site varchar unique not null,
    login varchar unique not null,
    password varchar
);