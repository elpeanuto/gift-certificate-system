create table if not exists revinfo
(
    rev      integer not null
    primary key,
    revtstmp bigint
);

alter table revinfo
    owner to postgres;

create table if not exists gift_certificate_aud
(
    id               bigint  not null,
    rev              integer not null
    constraint fkhriym6x1m3uyap2l3lxfmitku
    references revinfo,
    revtype          smallint,
    create_date      timestamp,
    description      text,
    duration         integer,
    last_update_date timestamp,
    name             varchar(255),
    price            numeric(10, 2),
    primary key (rev, id)
    );

alter table gift_certificate_aud
    owner to postgres;

create table if not exists gift_certificate_tag_aud
(
    rev                 integer not null
    constraint fk8ja9vo8r28smftruecdwx9ir0
    references revinfo,
    gift_certificate_id bigint  not null,
    tag_id              bigint  not null,
    revtype             smallint,
    primary key (gift_certificate_id, rev, tag_id)
    );

alter table gift_certificate_tag_aud
    owner to postgres;

create table if not exists order_gift_certificate_aud
(
    rev                 integer not null
    constraint fk1awe3ngq48mkjfbjmfk4cjoyq
    references revinfo,
    order_id            bigint  not null,
    gift_certificate_id bigint  not null,
    revtype             smallint,
    primary key (order_id, rev, gift_certificate_id)
    );

alter table order_gift_certificate_aud
    owner to postgres;

create table if not exists orders_aud
(
    id          bigint  not null,
    rev         integer not null
    constraint fkinujab7ljkelflu16c9jjch19
    references revinfo,
    revtype     smallint,
    create_date timestamp(6),
    price       numeric(10, 2),
    user_id     bigint,
    primary key (rev, id)
    );

alter table orders_aud
    owner to postgres;

create table if not exists role_aud
(
    id      bigint  not null,
    rev     integer not null
    constraint fkrks7qtsmup3w81fdp0d6omfk7
    references revinfo,
    revtype smallint,
    name    varchar(255),
    primary key (rev, id)
    );

alter table role_aud
    owner to postgres;

create table if not exists tag_aud
(
    id      bigint  not null,
    rev     integer not null
    constraint fkep272jdrgxgmq608l5y3792jn
    references revinfo,
    revtype smallint,
    name    varchar(255),
    primary key (rev, id)
    );

alter table tag_aud
    owner to postgres;

create table if not exists users_aud
(
    id         bigint  not null,
    rev        integer not null
    constraint fkc4vk4tui2la36415jpgm9leoq
    references revinfo,
    revtype    smallint,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role_id    bigint,
    primary key (rev, id)
    );

alter table users_aud
    owner to postgres;

create table if not exists gift_certificate
(
    id               bigint         not null
    primary key,
    create_date      timestamp      not null,
    description      text           not null,
    duration         integer        not null,
    last_update_date timestamp      not null,
    name             varchar(255),
    price            numeric(10, 2) not null
    );

alter table gift_certificate
    owner to postgres;

create table if not exists role
(
    id   bigint       not null
    primary key,
    name varchar(255) not null
    constraint role_name
    unique
    );

alter table role
    owner to postgres;

create table if not exists tag
(
    id   bigint       not null
    primary key,
    name varchar(255) not null
    constraint tag_name
    unique
    );

alter table tag
    owner to postgres;

create table if not exists gift_certificate_tag
(
    gift_certificate_id bigint not null
    constraint fka9orffdp51dqmamwv59d01rf1
    references gift_certificate,
    tag_id              bigint not null
    constraint fk5tjjbkwfbad84jkeobe07owf9
    references tag,
    primary key (gift_certificate_id, tag_id)
    );

alter table gift_certificate_tag
    owner to postgres;

create table if not exists users
(
    id         bigint       not null
    primary key,
    email      varchar(255) not null
    constraint "user-email"
    unique,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255) not null,
    role_id    bigint       not null
    constraint fk4qu1gr772nnf6ve5af002rwya
    references role
    );

alter table users
    owner to postgres;

create table if not exists orders
(
    id          bigint         not null
    primary key,
    create_date timestamp(6)   not null,
    price       numeric(10, 2) not null,
    user_id     bigint         not null
    constraint fk32ql8ubntj5uh44ph9659tiih
    references users
    );

alter table orders
    owner to postgres;

create table if not exists order_gift_certificate
(
    order_id            bigint not null
    constraint fkceisvy50h6nvkyann4fkh38te
    references orders,
    gift_certificate_id bigint not null
    constraint fkjsut7sjrnmbdqeae9tqfvf3l6
    references gift_certificate,
    primary key (order_id, gift_certificate_id)
    );

alter table order_gift_certificate
    owner to postgres;

create table if not exists refresh_tokens
(
    id              integer not null
    primary key,
    expiration_date timestamp(6) with time zone,
                                     token           varchar(255),
    user_id         bigint
    constraint fk1lih5y2npsf8u5o3vhdb9y0os
    references users
    );

alter table refresh_tokens
    owner to postgres;

create sequence revinfo_seq
    increment by 50;

alter sequence revinfo_seq owner to postgres;

create sequence certificate_sequence;

alter sequence certificate_sequence owner to postgres;

create sequence order_sequence;

alter sequence order_sequence owner to postgres;

create sequence tag_sequence;

alter sequence tag_sequence owner to postgres;

create sequence token_sequence;

alter sequence token_sequence owner to postgres;

create sequence user_sequence;

alter sequence user_sequence owner to postgres;

INSERT INTO role (id, name)
VALUES (1, 'ADMIN_ROLE');

INSERT INTO users (id, first_name, last_name, email, password, role_id)
VALUES (1, 'John', 'Doe', 'admin@example.com', 'admin', 1);



