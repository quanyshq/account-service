create table user_account
(
    id            int auto_increment,
    email         varchar(255) not null,
    password_hash varchar(255) not null,
    constraint pk_user_account primary key (id),
    constraint uq_user_account_email unique ((lower(trim(email))))
);