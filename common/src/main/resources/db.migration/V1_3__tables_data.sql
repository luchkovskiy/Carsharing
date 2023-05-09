INSERT INTO public.users (id, name, surname, birthday_date, created, changed, is_active, address, passport_id,
                          driver_id, driving_experience, rating, account_balance, email, user_password)
VALUES (1, 'Alexey', 'Luchkovksiy', '2002-01-24', '2023-04-15 14:37:10.000000', '2023-04-15 14:37:13.000000', true,
        'Mogilev, Neftyanoy 21', '232145151', '123125152', 3, 5, 200, 'luchkovskialexey@gmail.com', 'alexalex02');

INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (4, 'ROLE_ADMIN', 1, '2023-04-15 14:46:49.000000', '2023-04-15 14:46:50.000000');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (5, 'ROLE_USER', 1, '2023-04-15 14:47:22.000000', '2023-04-15 14:47:23.000000');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (6, 'ROLE_MODERATOR', 1, '2023-04-15 14:48:07.000000', '2023-04-15 14:48:07.000000');
