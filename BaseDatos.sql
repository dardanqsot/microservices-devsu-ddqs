create database ddqs_devsu_pt;

create table client
(
    age            integer      not null,
    enabled        boolean      not null,
    id_client      serial
        primary key,
    state          boolean      not null,
    address        varchar(255) not null,
    gender         varchar(255) not null,
    identification varchar(255) not null
        unique,
    name           varchar(255) not null,
    password       varchar(255) not null,
    telephone      varchar(255) not null
);
create table account_type
(
    id_account_type serial
        primary key,
    account_type    varchar(255) not null
);

create table account
(
    id_account      serial
        primary key,
    account_number  varchar(255)   not null
            unique,
    balance         numeric(38, 2) not null,
    enabled         boolean        not null,
    id_account_type integer        not null
        constraint fk_account_type
            references account_type,
    id_client       integer        not null,
    initial_balance numeric(38, 2) not null,
    status          boolean        not null
);

create table movement_type
(
    id_movement_type serial
        primary key,
    movement_type    varchar(255) not null
);

create table movement
(
    id_movement      serial
        primary key,
    balance          numeric(38, 2) not null,
    enabled          boolean        not null,
    id_account       integer
        constraint fk_account
            references account,
    id_movement_type integer        not null
        constraint fk_movement_type
            references movement_type,
    movement_date    date           not null,
    value            numeric(38, 2) not null
);

INSERT INTO account_type (id_account_type, account_type) VALUES (1, 'Ahorros');
INSERT INTO account_type (id_account_type, account_type) VALUES (2, 'Corriente');

INSERT INTO movement_type (id_movement_type, movement_type) VALUES (1, 'Retiro');
INSERT INTO movement_type (id_movement_type, movement_type) VALUES (2, 'Deposito');