create sequence category_seq start 1 increment 1;
create sequence product_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;

create table categories
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table product
(
    id          int8 not null,
    amount      int4 not null,
    description varchar(255),
    name        varchar(255),
    price       float8,
    category_id int8 not null,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table users
(
    id       int8 not null,
    email    varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table if exists product add constraint FKowomku74u72o6h8q0khj7id8q foreign key (category_id) references categories;
alter table if exists user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;