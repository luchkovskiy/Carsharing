create sequence public.users_user_id_seq;

alter sequence public.users_user_id_seq owner to postgres;

alter sequence public.users_user_id_seq owned by public.users.id;

create sequence public.cars_id_seq;

alter sequence public.cars_id_seq owner to postgres;

alter sequence public.cars_id_seq owned by public.cars.id;

create sequence public.bank_accounts_id_seq;

alter sequence public.bank_accounts_id_seq owner to postgres;

alter sequence public.bank_accounts_id_seq owned by public.l_users_cards.id;

create sequence public.subscriptions_id_seq;

alter sequence public.subscriptions_id_seq owner to postgres;

alter sequence public.subscriptions_id_seq owned by public.subscriptions.id;

create sequence public.sessions_id_seq;

alter sequence public.sessions_id_seq owner to postgres;

alter sequence public.sessions_id_seq owned by public.sessions.id;

create sequence public.violations_id_seq;

alter sequence public.violations_id_seq owner to postgres;

alter sequence public.violations_id_seq owned by public.accidents.id;

create sequence public.c_payment_data_id_seq;

alter sequence public.c_payment_data_id_seq owner to postgres;

alter sequence public.c_payment_data_id_seq owned by public.payment_cards.id;

create sequence public.c_cars_classes_id_seq
    as integer;

alter sequence public.c_cars_classes_id_seq owner to postgres;

alter sequence public.c_cars_classes_id_seq owned by public.c_cars_classes.id;

create sequence public.c_subscriptions_levels_id_seq
    as integer;

alter sequence public.c_subscriptions_levels_id_seq owner to postgres;

alter sequence public.c_subscriptions_levels_id_seq owned by public.c_subscriptions_levels.id;

create sequence public.roles_id_seq;

alter sequence public.roles_id_seq owner to postgres;

alter sequence public.roles_id_seq owned by public.roles.id;

create sequence public.cars_rent_info_id_seq;

alter sequence public.cars_rent_info_id_seq owner to postgres;

alter sequence public.cars_rent_info_id_seq owned by public.cars_rent_info.id;

create sequence public.verification_codes_id_seq;

alter sequence public.verification_codes_id_seq owner to postgres;

alter sequence public.verification_codes_id_seq owned by public.verification_codes.id;

