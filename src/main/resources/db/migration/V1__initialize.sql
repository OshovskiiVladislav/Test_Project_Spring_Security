create table users (
    id                      bigserial primary key,
    email                   varchar(50) unique,
    password                varchar(80) not null,
    username                varchar(30) not null unique
);

create table roles (
    id                      bigserial primary key,
    name                    varchar(50) not null unique
);

CREATE TABLE users_roles (
    user_id                 bigint not null references users (id),
    role_id                 bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values
('ROLE_USER'),
('ROLE_MODERATOR'),
('ROLE_ADMIN');

create table messages (
    id                      bigserial primary key,
    user_id                 bigint not null references users (id),
    text                    varchar(404),
    created_at              timestamp default current_timestamp
);