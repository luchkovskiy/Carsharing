INSERT INTO public.c_cars_classes (id, name, access_level, comfort_type, price_per_hour, created, changed)
VALUES (1, 'Standart', 1, 'BASIC', 20, '2023-05-22 22:58:59.846294', '2023-05-22 22:58:59.846294');
INSERT INTO public.c_cars_classes (id, name, access_level, comfort_type, price_per_hour, created, changed)
VALUES (2, 'Comfort', 2, 'COMFORT', 30, '2023-05-22 22:59:17.210701', '2023-05-22 22:59:17.210701');
INSERT INTO public.c_cars_classes (id, name, access_level, comfort_type, price_per_hour, created, changed)
VALUES (3, 'Business', 3, 'HIGH', 40, '2023-05-22 22:59:36.092548', '2023-05-22 22:59:36.092548');

INSERT INTO public.c_subscriptions_levels (id, access_level, price_per_day, name, created, changed)
VALUES (1, 1, 120, 'Standard', '2023-05-22 23:00:28.703662', '2023-05-22 23:00:28.703662');
INSERT INTO public.c_subscriptions_levels (id, access_level, price_per_day, name, created, changed)
VALUES (2, 2, 180, 'Comfort', '2023-05-22 23:00:42.470699', '2023-05-22 23:00:42.470699');
INSERT INTO public.c_subscriptions_levels (id, access_level, price_per_day, name, created, changed)
VALUES (3, 3, 250, 'Business', '2023-05-22 23:00:51.288875', '2023-05-22 23:00:51.288875');

INSERT INTO public.users (id, name, surname, birthday_date, created, changed, is_active, address, passport_id,
                          driver_id, driving_experience, rating, account_balance, email, user_password)
VALUES (3, 'User', 'User', '1998-05-13 16:30:00.000000', '2023-05-22 22:55:39.441510', '2023-05-22 22:55:39.441510',
        true, 'Mogilev, Terehina 29', '2134667A001PB6', 'AA 21335678', 5, 5, 1500, 'user@user.com',
        '$2a$06$6FJKmgIF.nKwkz.tSgSZSecdox9WvWBj6h0pBnAcNHcuQo7GEZjYq');
INSERT INTO public.users (id, name, surname, birthday_date, created, changed, is_active, address, passport_id,
                          driver_id, driving_experience, rating, account_balance, email, user_password)
VALUES (5, 'Andei', 'Vilchickiy', '1985-02-26 00:00:00.000000', '2023-05-22 23:25:23.197675',
        '2023-05-22 23:25:23.197675', true, 'Mogilev, Prospekt Mira 21', '1233367A001PB6', 'AA 12343378', 12, 5, 960,
        'luchkovskialex@gmail.com', '$2a$06$3vZv9lbtec8jyQS7IzkdQu1CYPw/csuk3LlRCPwb.2IAdvzNkgD3a');
INSERT INTO public.users (id, name, surname, birthday_date, created, changed, is_active, address, passport_id,
                          driver_id, driving_experience, rating, account_balance, email, user_password)
VALUES (4, 'Aleksei', 'Luchkovskiy', '2002-01-24 18:30:00.000000', '2023-05-22 23:18:30.064898',
        '2023-05-22 23:18:30.064898', true, 'Mogilev, Tramvaynaya 5', '2134127A001PB6', 'AA 21335123', 4, 4.5, 980,
        'luchkovskialexey@gmail.com', '$2a$06$sxHT4JGY.KuTHEAXv7PXmeqCZkQTzwKbtj7zo6gyGP6n2p0cWWDdK');
INSERT INTO public.users (id, name, surname, birthday_date, created, changed, is_active, address, passport_id,
                          driver_id, driving_experience, rating, account_balance, email, user_password)
VALUES (1, 'Admin', 'Admin', '1998-05-13 16:30:00.000000', '2023-05-22 22:47:35.410306', '2023-05-22 22:47:35.410306',
        true, 'Mogilev, Transportnaya 21', '4321567A001PB6', 'BB 43215678', 4, 5, 10000, 'admin@admin.com',
        '$2a$06$ST7xswrgG7QLc9y/Gnw4mOoeCWLscMaLMKxvuTGhg45kQmu2AFSsq');
INSERT INTO public.users (id, name, surname, birthday_date, created, changed, is_active, address, passport_id,
                          driver_id, driving_experience, rating, account_balance, email, user_password)
VALUES (2, 'Manager', 'Manager', '1998-05-13 16:30:00.000000', '2023-05-22 22:53:28.750417',
        '2023-05-22 22:53:28.750417', true, 'Mogilev, Neftyanoy 18', '1234667A001PB6', 'AA 12335678', 3, 5, 2000,
        'manager@manager.com', '$2a$06$bYevQVVQgfL5vftj4dkRs.rz7iHBB.pV4Ffi8nfnq3QbZjNgwyG3.');

INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (1, 'Audi', 'RS7', '2023-05-22 23:03:05.990641', '2023-05-22 23:03:05.990641', 320, 'Blue', 2019, 'AUTO', 15.5,
        5, 3, '1234AX-3', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (2, 'BMW', 'iX', '2023-05-22 23:04:30.981290', '2023-05-22 23:04:30.981290', 280, 'Black', 2020, 'ROBOT', 14, 7,
        3, '4321AX-3', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (3, 'Chevrolet', 'CruzeJ300', '2023-05-22 23:05:20.586445', '2023-05-22 23:05:20.586445', 250, 'Brown', 2015,
        'AUTO', 14, 5, 2, '3298AX-3', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (4, 'Audi', 'A4 B9', '2023-05-22 23:06:41.331045', '2023-05-22 23:06:41.331045', 260, 'White', 2016,
        'MECHANICAL', 8, 5, 2, '3123AX-7', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (5, 'Citroen', 'C4 Picasso', '2023-05-22 23:07:22.932082', '2023-05-22 23:07:22.932082', 205, 'Blue', 2011,
        'MECHANICAL', 7, 7, 1, '6456AX-7', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (6, 'Nissan', 'Quashkai II', '2023-05-22 23:08:27.624333', '2023-05-22 23:08:27.624333', 200, 'Black blue', 2013,
        'AUTO', 7.5, 5, 1, '5334AX-7', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (7, 'BMW', 'X6', '2023-05-22 23:10:15.676295', '2023-05-22 23:10:15.676295', 310, 'Black', 2020, 'AUTO', 17.5, 5,
        3, '1111AX-7', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (8, 'Volkswagen', 'Tiguan', '2023-05-22 23:11:10.330939', '2023-05-22 23:11:10.330939', 260, 'White', 2017,
        'ROBOT', 12.4, 5, 2, '2121AX-7', true);
INSERT INTO public.cars (id, brand, model, created, changed, max_speed, color, release_year, gearbox_type,
                         gas_consumption, amount_of_sits, class_id, license_plate_number, is_visible)
VALUES (9, 'Volkswagen', 'Polo', '2023-05-22 23:12:17.333368', '2023-05-22 23:12:17.333368', 210, 'Green', 2018,
        'MECHANICAL', 8.2, 5, 1, '9834AX-7', true);

INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (1, 50, false, true, 5, '2023-05-22 23:03:06.020916', '2023-05-22 23:03:06.020916', 1, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (2, 50, false, true, 5, '2023-05-22 23:04:30.995551', '2023-05-22 23:04:30.995551', 2, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (3, 50, false, true, 5, '2023-05-22 23:05:20.597981', '2023-05-22 23:05:20.597981', 3, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (4, 50, false, true, 5, '2023-05-22 23:06:41.341032', '2023-05-22 23:06:41.341032', 4, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (5, 50, false, true, 5, '2023-05-22 23:07:22.940732', '2023-05-22 23:07:22.940732', 5, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (6, 50, false, true, 5, '2023-05-22 23:08:27.630852', '2023-05-22 23:08:27.630852', 6, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (7, 50, false, true, 5, '2023-05-22 23:10:15.680825', '2023-05-22 23:10:15.680825', 7, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (8, 50, false, true, 5, '2023-05-22 23:11:10.331458', '2023-05-22 23:11:10.331458', 8, '1st Ring 11, Minsk');
INSERT INTO public.cars_rent_info (car_id, gas_remaining, is_repairing, is_available, condition, created, changed, id,
                                   current_location)
VALUES (9, 50, false, true, 5, '2023-05-22 23:12:17.343304', '2023-05-22 23:12:17.343304', 9, '1st Ring 11, Minsk');

INSERT INTO public.payment_cards (id, card_number, expiration_date, cvv, created, changed, cardholder)
VALUES (1, '1234123412341234', '03/24', 'fi4m1LNsqm3bduULBIxhjA==', '2023-05-22 23:13:44.461337',
        '2023-05-22 23:13:44.461337', 'ALEKSEY LUCHKOVSKIY');
INSERT INTO public.payment_cards (id, card_number, expiration_date, cvv, created, changed, cardholder)
VALUES (2, '4321432143214321', '03/24', 'fi4m1LNsqm3bduULBIxhjA==', '2023-05-22 23:14:09.461094',
        '2023-05-22 23:14:09.461094', 'MIKHAIL DVORETSKIY');
INSERT INTO public.payment_cards (id, card_number, expiration_date, cvv, created, changed, cardholder)
VALUES (3, '4321432143214321', '05/26', 'yQdh2YdpDp+S8Sd4mUPMqA==', '2023-05-22 23:14:59.665093',
        '2023-05-22 23:14:59.665093', 'KARINA SOBAKINA');
INSERT INTO public.payment_cards (id, card_number, expiration_date, cvv, created, changed, cardholder)
VALUES (4, '4321432145514321', '02/24', 'Hukfkz7FzSseukkgJxYHZg==', '2023-05-22 23:16:30.411479',
        '2023-05-22 23:16:30.411479', 'MAGOMED KHALILOV');

INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (8, 'ROLE_USER', 5, '2023-05-22 23:25:23.202183', '2023-05-22 23:25:23.202183');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (1, 'ROLE_USER', 1, '2023-05-22 22:47:35.430231', '2023-05-22 22:47:35.430231');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (2, 'ROLE_MODERATOR', 1, '2023-05-22 22:49:22.000000', '2023-05-22 22:49:24.000000');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (3, 'ROLE_ADMIN', 1, '2023-05-22 22:49:45.000000', '2023-05-22 22:49:45.000000');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (4, 'ROLE_USER', 2, '2023-05-22 22:53:28.760464', '2023-05-22 22:53:28.760464');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (5, 'ROLE_USER', 3, '2023-05-22 22:55:39.450518', '2023-05-22 22:55:39.450518');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (6, 'ROLE_MODERATOR', 2, '2023-05-22 22:57:40.351604', '2023-05-22 22:57:40.351604');
INSERT INTO public.roles (id, role_name, user_id, created, changed)
VALUES (7, 'ROLE_USER', 4, '2023-05-22 23:18:30.071408', '2023-05-22 23:18:30.071408');

INSERT INTO public.sessions (id, user_id, car_id, start_time, end_time, total_price, status, distance_passed, created,
                             changed, start_location)
VALUES (1, 4, 1, '2023-02-22 17:24:01.000000', '2023-02-22 19:14:56.000000', 120.2, 'FINISHED', 15.5,
        '2023-05-22 23:32:12.604058', '2023-05-22 23:32:12.604058', '1st Ring 11, Minsk');
INSERT INTO public.sessions (id, user_id, car_id, start_time, end_time, total_price, status, distance_passed, created,
                             changed, start_location)
VALUES (2, 4, 5, '2023-02-23 17:24:01.000000', '2023-02-23 19:14:56.000000', 50, 'FINISHED', 8,
        '2023-05-22 23:32:33.539481', '2023-05-22 23:32:33.539481', '1st Ring 11, Minsk');
INSERT INTO public.sessions (id, user_id, car_id, start_time, end_time, total_price, status, distance_passed, created,
                             changed, start_location)
VALUES (3, 5, 3, '2023-04-23 17:24:01.000000', '2023-04-23 19:14:56.000000', 117, 'FINISHED', 87,
        '2023-05-22 23:33:05.696711', '2023-05-22 23:33:05.696711', '1st Ring 11, Minsk');

INSERT INTO public.subscriptions (id, user_id, start_time, end_time, status, amount_of_trips, days_total, created,
                                  changed, level_id)
VALUES (1, 4, '2023-05-22 23:29:28.061950', '2023-05-26 23:29:28.061950', 'ACTIVE', 0, 4, '2023-05-22 23:29:28.047924',
        '2023-05-22 23:29:28.047924', 1);
INSERT INTO public.subscriptions (id, user_id, start_time, end_time, status, amount_of_trips, days_total, created,
                                  changed, level_id)
VALUES (2, 5, '2023-05-22 23:29:42.671572', '2023-05-25 23:29:42.671572', 'ACTIVE', 0, 3, '2023-05-22 23:29:42.655957',
        '2023-05-22 23:29:42.655957', 2);

INSERT INTO public.l_users_cards (id, user_id, card_id, created, changed)
VALUES (1, 4, 1, '2023-05-22 23:30:35.901911', '2023-05-22 23:30:35.901911');
INSERT INTO public.l_users_cards (id, user_id, card_id, created, changed)
VALUES (2, 5, 2, '2023-05-22 23:30:39.772933', '2023-05-22 23:30:39.772933');

INSERT INTO public.accidents (id, session_id, name, fine, time, rating_subtraction, damage_level, is_critical, created,
                              changed)
VALUES (1, 1, 'Scratch', 20, '2023-02-22 17:24:01.000000', 0.5, 1, false, '2023-05-22 23:34:34.067344',
        '2023-05-22 23:34:34.067344');
INSERT INTO public.accidents (id, session_id, name, fine, time, rating_subtraction, damage_level, is_critical, created,
                              changed)
VALUES (2, 2, 'Salon problem', 20, '2023-02-22 17:24:01.000000', 0.5, 1, false, '2023-05-22 23:36:35.304318',
        '2023-05-22 23:36:35.304318');
