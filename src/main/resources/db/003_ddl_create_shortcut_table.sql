create table shortcut (
    id serial primary key not null,
    url varchar unique not null,
    code varchar unique not null,
    site_id int not null references site(id)
);