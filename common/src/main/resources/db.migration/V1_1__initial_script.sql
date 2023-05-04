create table public.users
(
    id                 bigint       default nextval('users_user_id_seq'::regclass) not null
        primary key
        constraint users_user_id_key
            unique,
    name               varchar(50)                                                 not null,
    surname            varchar(50)                                                 not null,
    birthday_date      date                                                        not null,
    created            timestamp(6) default now()                                  not null,
    changed            timestamp(6)                                                not null,
    is_active          boolean      default true                                   not null,
    address            varchar(30),
    passport_id        varchar(20)                                                 not null,
    driver_id          varchar(20)                                                 not null,
    driving_experience real                                                        not null,
    rating             real         default 5                                      not null,
    account_balance    real         default 0                                      not null,
    email              varchar(50),
    user_password      varchar(50)
);

alter table public.users
    owner to postgres;

create index users_name_surname_index
    on public.users (name, surname);

create index users_surname_index
    on public.users (surname);

create index users_user_id_index
    on public.users (id);

create table public.payment_cards
(
    id              bigint       default nextval('c_payment_data_id_seq'::regclass) not null
        constraint c_payment_data_pkey
            primary key
        constraint c_payment_data_id_key
            unique,
    card_number     varchar(20)                                                     not null,
    expiration_date varchar(10)                                                     not null,
    cvv             varchar(3)                                                      not null,
    created         timestamp(6) default now()                                      not null,
    changed         timestamp(6)                                                    not null,
    cardholder      varchar(20)
);

alter table public.payment_cards
    owner to postgres;

create table public.l_users_cards
(
    id      bigint       default nextval('bank_accounts_id_seq'::regclass) not null
        constraint bank_accounts_pkey
            primary key,
    user_id bigint                                                         not null
        constraint bank_accounts_pk
            unique
        constraint bank_accounts_fk
            references public.users
            on update cascade on delete cascade,
    card_id bigint                                                         not null
        constraint c_account_money_c_payment_data_id_fk
            references public.payment_cards,
    created timestamp(6) default now(),
    changed timestamp(6)
);

alter table public.l_users_cards
    owner to postgres;

create index bank_accounts_id_user_id_index
    on public.l_users_cards (id, user_id);

create index c_payment_data_changed_index
    on public.payment_cards (changed);

create index c_payment_data_id_index
    on public.payment_cards (id);

create index payment_cards_card_number_index
    on public.payment_cards (card_number);

create table public.c_cars_classes
(
    id             serial
        primary key,
    name           varchar(20)  not null
        unique,
    access_level   integer      not null,
    comfort_type   varchar(15)  not null,
    price_per_hour real         not null,
    created        timestamp(6) not null,
    changed        timestamp(6) not null
);

alter table public.c_cars_classes
    owner to postgres;

create table public.cars
(
    id                   bigserial
        primary key,
    brand                varchar(20)                not null,
    model                varchar(20)                not null,
    created              timestamp(6) default now() not null,
    changed              timestamp(6)               not null,
    max_speed            real,
    color                varchar(15),
    release_year         integer,
    gearbox_type         varchar(10),
    gas_consumption      real,
    amount_of_sits       integer,
    class_id             integer                    not null
        constraint cars_c_cars_classes_id_fk
            references public.c_cars_classes,
    license_plate_number varchar(10),
    is_visible           boolean      default true  not null
);

alter table public.cars
    owner to postgres;

create index cars_issue_year_max_km_h_index
    on public.cars (release_year desc, max_speed asc);

create index cars_model_index
    on public.cars (model);

create index cars_class_id_index
    on public.cars (class_id desc);

create index cars_gearbox_type_index
    on public.cars (gearbox_type);

create table public.cars_rent_info
(
    id               bigint  default nextval('rent_data_id_seq'::regclass) not null
        constraint rent_data_pkey
            primary key,
    car_id           bigint                                                not null
        constraint rent_data_car_id_key
            unique
        constraint rent_data_cars_id_fk
            references public.cars
            on update cascade on delete cascade,
    gas_remaining    real                                                  not null,
    is_repairing     boolean default false                                 not null,
    current_location varchar(20),
    is_available     boolean default true                                  not null,
    condition        real    default 5                                     not null,
    created          timestamp(6)                                          not null,
    changed          timestamp(6)                                          not null
);

alter table public.cars_rent_info
    owner to postgres;

create index c_cars_rent_data_is_repairing_index
    on public.cars_rent_info (is_repairing);

create index c_cars_rent_data_gas_remaining_index
    on public.cars_rent_info (gas_remaining desc);

create table public.sessions
(
    id              bigserial
        primary key,
    user_id         bigint       not null
        constraint sessions_users_id_fk
            references public.users,
    car_id          bigint       not null
        constraint sessions_cars_id_fk
            references public.cars,
    start_time      timestamp(6) not null,
    end_time        timestamp(6),
    total_price     real         not null,
    status          varchar(10)  not null,
    distance_passed real,
    created         timestamp(6) default now(),
    changed         timestamp(6)
);

alter table public.sessions
    owner to postgres;

create index sessions_status_index
    on public.sessions (status);

create index sessions_total_price_index
    on public.sessions (total_price desc);

create index sessions_km_passed_index
    on public.sessions (distance_passed);

create table public.accidents
(
    id                 bigint       default nextval('violations_id_seq'::regclass) not null
        constraint violations_pkey
            primary key,
    session_id         bigint                                                      not null
        constraint violations_sessions_id_fk
            references public.sessions
            on update cascade on delete cascade,
    name               varchar(20)                                                 not null,
    fine               real                                                        not null,
    time               timestamp(6)                                                not null,
    rating_subtraction real,
    damage_level       integer,
    is_critical        boolean      default false                                  not null,
    created            timestamp(6) default now()                                  not null,
    changed            timestamp(6)                                                not null
);

alter table public.accidents
    owner to postgres;

create index accidents_fine_index
    on public.accidents (fine desc);

create index accidents_time_index
    on public.accidents (time);

create index accidents_time_index_2
    on public.accidents (time);

create table public.c_subscriptions_levels
(
    id            serial
        primary key
        unique,
    access_level  integer      not null,
    price_per_day real         not null,
    name          varchar(15)  not null,
    created       timestamp(6) not null,
    changed       timestamp(6) not null
);

alter table public.c_subscriptions_levels
    owner to postgres;

create table public.subscriptions
(
    id              bigserial
        primary key
        unique,
    user_id         bigint                     not null
        unique
        constraint subscriptions_users_id_fk
            references public.users
            on update cascade on delete cascade,
    start_time      timestamp(6)               not null,
    end_time        timestamp(6)               not null,
    status          varchar(15)                not null,
    amount_of_trips integer,
    days_total      integer,
    created         timestamp(6) default now() not null,
    changed         timestamp(6),
    level_id        integer                    not null
        constraint subscriptions_c_subscriptions_levels_id_fk
            references public.c_subscriptions_levels
);

alter table public.subscriptions
    owner to postgres;

create index subscriptions_id_index
    on public.subscriptions (id);

create index subscriptions_id_user_id_index
    on public.subscriptions (id, user_id);

create index subscriptions_status_index
    on public.subscriptions (status);

create table public.roles
(
    id        bigserial
        primary key,
    role_name varchar(50)  not null,
    user_id   bigint       not null
        constraint roles_users_id_fk
            references public.users
            on update cascade on delete cascade,
    created   timestamp(6) not null,
    changed   timestamp(6) not null
);

alter table public.roles
    owner to postgres;

create unique index roles_role_name_user_id_uindex
    on public.roles (role_name, user_id);

