CREATE DATABASE project_db;
\c project_db;

CREATE ROLE postgres LOGIN PASSWORD 'postgres';

CREATE SCHEMA IF NOT EXISTS public;
GRANT ALL PRIVILEGES ON SCHEMA public TO postgres;

create table if not exists public.users
(
    id                 bigint default nextval('users_user_id_seq'::regclass) not null
        primary key
        constraint users_user_id_key
            unique,
    name               varchar(100)                                          not null,
    surname            varchar(100)                                          not null,
    birthday_date      timestamp(6)                                          not null,
    created            timestamp(6)                                          not null,
    changed            timestamp(6)                                          not null,
    is_active          boolean                                               not null,
    address            varchar(100),
    passport_id        varchar(50)                                           not null
        unique,
    driver_id          varchar(50)                                           not null
        unique,
    driving_experience real                                                  not null,
    rating             real                                                  not null,
    account_balance    real                                                  not null,
    email              varchar(50)
        unique,
    user_password      varchar(200)
);

alter table public.users
    owner to postgres;

create index if not exists users_user_id_index
    on public.users (id);

create index if not exists users_created_index
    on public.users (created);

create index if not exists users_rating_index
    on public.users (rating);

create index if not exists users_surname_index
    on public.users (surname);

create index if not exists users_name_surname_index
    on public.users (name, surname);

create index if not exists users_name_surname_index_2
    on public.users (name, surname);

create table if not exists public.payment_cards
(
    id              bigint default nextval('c_payment_data_id_seq'::regclass) not null
        constraint c_payment_data_pkey
            primary key
        constraint c_payment_data_id_key
            unique,
    card_number     varchar(30)                                               not null,
    expiration_date varchar(15)                                               not null,
    cvv             varchar(10)                                               not null,
    created         timestamp(6)                                              not null,
    changed         timestamp(6)                                              not null,
    cardholder      varchar(50)
);

alter table public.payment_cards
    owner to postgres;

create table if not exists public.l_users_cards
(
    id      bigint default nextval('bank_accounts_id_seq'::regclass) not null
        constraint bank_accounts_pkey
            primary key,
    user_id bigint                                                   not null
        constraint bank_accounts_fk
            references public.users
            on update cascade on delete cascade,
    card_id bigint                                                   not null
        constraint c_account_money_c_payment_data_id_fk
            references public.payment_cards,
    created timestamp(6),
    changed timestamp(6)
);

alter table public.l_users_cards
    owner to postgres;

create index if not exists bank_accounts_id_user_id_index
    on public.l_users_cards (id, user_id);

create index if not exists l_users_cards_created_index
    on public.l_users_cards (created);

create index if not exists l_users_cards_user_id_card_id_index
    on public.l_users_cards (user_id, card_id);

create index if not exists c_payment_data_changed_index
    on public.payment_cards (changed);

create index if not exists c_payment_data_id_index
    on public.payment_cards (id);

create index if not exists payment_cards_card_number_index
    on public.payment_cards (card_number);

create index if not exists payment_cards_changed_index
    on public.payment_cards (changed);

create table if not exists public.c_cars_classes
(
    id             serial
        primary key,
    name           varchar(50)  not null
        unique,
    access_level   integer      not null,
    comfort_type   varchar(50)  not null,
    price_per_hour real         not null,
    created        timestamp(6) not null,
    changed        timestamp(6) not null
);

alter table public.c_cars_classes
    owner to postgres;

create table if not exists public.cars
(
    id                   bigserial
        primary key,
    brand                varchar(50)  not null,
    model                varchar(25)  not null,
    created              timestamp(6) not null,
    changed              timestamp(6) not null,
    max_speed            real,
    color                varchar(25),
    release_year         integer,
    gearbox_type         varchar(20),
    gas_consumption      real,
    amount_of_sits       integer,
    class_id             integer      not null
        constraint cars_c_cars_classes_id_fk
            references public.c_cars_classes,
    license_plate_number varchar(50)
        unique,
    is_visible           boolean      not null
);

alter table public.cars
    owner to postgres;

create index if not exists cars_issue_year_max_km_h_index
    on public.cars (release_year desc, max_speed asc);

create index if not exists cars_class_id_index
    on public.cars (class_id desc);

create index if not exists cars_model_index
    on public.cars (model);

create index if not exists cars_gearbox_type_index
    on public.cars (gearbox_type);

create index if not exists cars_amount_of_sits_index
    on public.cars (amount_of_sits);

create index if not exists cars_brand_model_index
    on public.cars (brand, model);

create index if not exists cars_class_id_index_2
    on public.cars (class_id);

create table if not exists public.cars_rent_info
(
    car_id           bigint       not null
        constraint rent_data_car_id_key
            unique
        constraint rent_data_cars_id_fk
            references public.cars
            on update cascade on delete cascade,
    gas_remaining    real         not null,
    is_repairing     boolean      not null,
    is_available     boolean      not null,
    condition        real         not null,
    created          timestamp(6) not null,
    changed          timestamp(6) not null,
    id               bigserial
        primary key
        unique,
    current_location varchar(50)
);

alter table public.cars_rent_info
    owner to postgres;

create index if not exists c_cars_rent_data_is_repairing_index
    on public.cars_rent_info (is_repairing);

create index if not exists c_cars_rent_data_gas_remaining_index
    on public.cars_rent_info (gas_remaining desc);

create index if not exists cars_rent_info_condition_is_repairing_index
    on public.cars_rent_info (condition, is_repairing);

create index if not exists cars_rent_info_is_available_index
    on public.cars_rent_info (is_available);

create table if not exists public.sessions
(
    id              bigserial
        primary key,
    user_id         bigint       not null
        constraint sessions_users_id_fk
            references public.users
            on update cascade on delete cascade,
    car_id          bigint       not null
        constraint sessions_cars_id_fk
            references public.cars,
    start_time      timestamp(6) not null,
    end_time        timestamp(6),
    total_price     real,
    status          varchar(25)  not null,
    distance_passed real,
    created         timestamp(6) not null,
    changed         timestamp(6) not null,
    start_location  varchar(50)  not null
);

alter table public.sessions
    owner to postgres;

create index if not exists sessions_total_price_index
    on public.sessions (total_price desc);

create index if not exists sessions_km_passed_index
    on public.sessions (distance_passed);

create index if not exists sessions_status_index
    on public.sessions (status);

create index if not exists sessions_car_id_index
    on public.sessions (car_id);

create index if not exists sessions_status_index_2
    on public.sessions (status);

create index if not exists sessions_user_id_index
    on public.sessions (user_id);

create table if not exists public.accidents
(
    id                 bigint default nextval('violations_id_seq'::regclass) not null
        constraint violations_pkey
            primary key,
    session_id         bigint                                                not null
        constraint accidents_sessions_id_fk
            references public.sessions
            on update cascade on delete cascade,
    name               varchar(50)                                           not null,
    fine               real                                                  not null,
    time               timestamp(6)                                          not null,
    rating_subtraction real,
    damage_level       integer                                               not null,
    is_critical        boolean                                               not null,
    created            timestamp(6)                                          not null,
    changed            timestamp(6)                                          not null
);

alter table public.accidents
    owner to postgres;

create index if not exists accidents_fine_index
    on public.accidents (fine desc);

create index if not exists accidents_time_index
    on public.accidents (time);

create index if not exists accidents_time_index_2
    on public.accidents (time);

create index if not exists accidents_is_critical_damage_level_index
    on public.accidents (is_critical, damage_level);

create index if not exists accidents_session_id_index
    on public.accidents (session_id);

create index if not exists accidents_time_index_3
    on public.accidents (time);

create index if not exists c_cars_classes_access_level_index
    on public.c_cars_classes (access_level);

create index if not exists c_cars_classes_price_per_hour_index
    on public.c_cars_classes (price_per_hour);

create table if not exists public.c_subscriptions_levels
(
    id            serial
        primary key
        unique,
    access_level  integer      not null,
    price_per_day real         not null,
    name          varchar(50)  not null,
    created       timestamp(6) not null,
    changed       timestamp(6) not null
);

alter table public.c_subscriptions_levels
    owner to postgres;

create table if not exists public.subscriptions
(
    id              bigserial
        primary key
        unique,
    user_id         bigint       not null
        unique
        constraint subscriptions_users_id_fk
            references public.users
            on update cascade on delete cascade,
    start_time      timestamp(6) not null,
    end_time        timestamp(6) not null,
    status          varchar(25)  not null,
    amount_of_trips integer,
    days_total      integer,
    created         timestamp(6) not null,
    changed         timestamp(6),
    level_id        integer      not null
        constraint subscriptions_c_subscriptions_levels_id_fk
            references public.c_subscriptions_levels
);

alter table public.subscriptions
    owner to postgres;

create index if not exists subscriptions_id_index
    on public.subscriptions (id);

create index if not exists subscriptions_id_user_id_index
    on public.subscriptions (id, user_id);

create index if not exists subscriptions_status_index
    on public.subscriptions (status);

create index if not exists subscriptions_amount_of_trips_index
    on public.subscriptions (amount_of_trips);

create index if not exists subscriptions_status_index_2
    on public.subscriptions (status);

create index if not exists c_subscriptions_levels_access_level_name_index
    on public.c_subscriptions_levels (access_level, name);

create index if not exists c_subscriptions_levels_price_per_day_index
    on public.c_subscriptions_levels (price_per_day);

create table if not exists public.roles
(
    id        bigserial
        primary key,
    role_name varchar(50)  not null,
    user_id   bigint
        constraint roles_users_id_fk
            references public.users
            on update cascade on delete cascade,
    created   timestamp(6) not null,
    changed   timestamp(6) not null
);

alter table public.roles
    owner to postgres;

create unique index if not exists roles_role_name_user_id_uindex
    on public.roles (role_name, user_id);

create index if not exists roles_role_name_index
    on public.roles (role_name);

create index if not exists roles_user_id_index
    on public.roles (user_id);

create table if not exists public.flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table public.flyway_schema_history
    owner to postgres;

create index if not exists flyway_schema_history_s_idx
    on public.flyway_schema_history (success);

create table if not exists public.verification_codes
(
    id      bigserial
        primary key,
    user_id bigint       not null
        constraint verification_codes_users_id_fk
            references public.users
            on update cascade on delete cascade,
    code    varchar(20)  not null,
    created timestamp(6) not null
);

alter table public.verification_codes
    owner to postgres;

