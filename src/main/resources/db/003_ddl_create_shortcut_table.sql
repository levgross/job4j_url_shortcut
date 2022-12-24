create table shortcut (
    id serial primary key not null,
    url varchar(2000) unique not null,
    code varchar(255) unique not null,
    site_id int not null references site(id)
);