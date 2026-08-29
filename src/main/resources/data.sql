

insert into mpa_ratings (id, name) values
                                       (1, 'G'),
                                       (2, 'PG'),
                                       (3, 'PG-13'),
                                       (4, 'R'),
                                       (5, 'NC-17');

insert into genres (id, name) values
                                  (1, 'Комедия'),
                                  (2, 'Драма'),
                                  (3, 'Мультфильм'),
                                  (4, 'Триллер'),
                                  (5, 'Документальный'),
                                  (6, 'Боевик');

insert into users (id, email, login, name, birthday) values
                                                         (1, 'maverick@topgun.com', 'mavguy', 'Pete Mitchell', '1962-07-03'),
                                                         (2, 'jack@overlook.com', 'torrance', 'Jack Torrance', '1946-04-22'),
                                                         (3, 'neo@matrix.io', 'the_one', 'Thomas Anderson', '1964-09-02'),
                                                         (4, 'amelie@poulain.fr', 'poulain', 'Amelie Poulain', '1974-08-09'),
                                                         (5, 'simba@priderock.org', 'king_lion', 'Simba', '1994-06-15');

select ('users_id_seq', (select max(id) from users));

insert into users_friends (user_id, friend_id) values
                                                   (1, 2), -- Pete дружит с Jack
                                                   (2, 1),
                                                   (1, 3), -- Pete дружит с Neo
                                                   (3, 4), -- Neo дружит с Amelie
                                                   (4, 5); -- Amelie дружит с Simba

insert into films (id, uploader_id, title, description, release_date, duration, mpa_id) values
                                                                                            (1, 1, 'Лучший Стрелок / Top Gun', 'Американский драматический боевик с Томом Крузом', '1986-05-18', 110, 3),
                                                                                            (2, 2, 'Сияние', 'Культовый фильм ужасов Стэнли Кубрика по роману Стивена Кинга', '1980-05-23', 146, 4),
                                                                                            (3, 3, 'Матрица', 'Культовый научно-фантастический боевик', '1999-03-31', 136, 4),
                                                                                            (4, 4, 'Амели', 'Французская романтическая комедия', '2001-04-25', 122, 2),
                                                                                            (5, 5, 'Король Лев', 'Анимационный фильм о львенке Симбе', '1994-06-15', 88, 1);

insert into film_genres (film_id, genre_id) values
                                                (1, 6), -- Top Gun -> Боевик
                                                (1, 2), -- Top Gun -> Драма
                                                (2, 4), -- Сияние -> Триллер
                                                (2, 2), -- Сияние -> Драма
                                                (3, 6), -- Матрица -> Боевик
                                                (3, 4), -- Матрица -> Триллер
                                                (4, 1), -- Амели -> Комедия
                                                (4, 2), -- Амели -> Драма
                                                (5, 3); -- Король Лев -> Мультфильм

insert into film_likes (film_id, user_id) values
                                              (3, 1), (3, 2), (3, 4), (3, 5), -- У "Матрицы" 4 лайка (самый популярный)
                                              (1, 1), (1, 3), (1, 5),         -- У "Top Gun" 3 лайка
                                              (4, 2), (4, 4),                 -- У "Амели" 2 лайка
                                              (2, 2); -- У "Сияния" 1 лайк