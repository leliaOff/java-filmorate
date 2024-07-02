DROP TABLE FILM_GENRES CASCADE;

DROP TABLE FILM_USER_RATINGS CASCADE;

DROP TABLE FILMS CASCADE;

DROP TABLE GENRES CASCADE;

DROP TABLE RATINGS CASCADE;

DROP TABLE USER_FOLLOWS CASCADE;

DROP TABLE USERS CASCADE;

CREATE TABLE IF NOT EXISTS genres
(
    id bigint auto_increment,
    name varchar NOT NULL,
    CONSTRAINT genres_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ratings
(
    id bigint auto_increment,
    name varchar NOT NULL,
    CONSTRAINT ratings_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS films
(
    id bigint auto_increment,
    name varchar NOT NULL,
    description text,
    release_date date,
    duration integer,
    rating_id bigint,
    created_at timestamp DEFAULT NOW() NOT NULL,
    CONSTRAINT films_pk PRIMARY KEY (id),
    CONSTRAINT films_ratings_id_fk FOREIGN KEY (rating_id) REFERENCES RATINGS (id)
);

CREATE TABLE IF NOT EXISTS film_genres
(
    id bigint auto_increment,
    film_id bigint NOT NULL,
    genre_id bigint NOT NULL,
    CONSTRAINT film_genres_pk PRIMARY KEY (id),
    CONSTRAINT film_genres_films_id_fk FOREIGN KEY (film_id) REFERENCES FILMS (id),
    CONSTRAINT film_genres_genres_id_fk FOREIGN KEY (genre_id) REFERENCES GENRES (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id bigint auto_increment,
    email varchar NOT NULL,
    login varchar NOT NULL,
    name varchar NOT NULL,
    birthday date,
    created_at timestamp DEFAULT NOW() NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_follows
(
    id bigint auto_increment,
    following_user_id bigint NOT NULL,
    followed_user_id bigint NOT NULL,
    state smallint NOT NULL DEFAULT 0,
    created_at timestamp DEFAULT NOW() NOT NULL,
    CONSTRAINT user_follows_pk PRIMARY KEY (id),
    CONSTRAINT user_follows_following_users_id_fk FOREIGN KEY (following_user_id) REFERENCES USERS (id),
    CONSTRAINT user_follows_followed_users_id_fk FOREIGN KEY (followed_user_id) REFERENCES USERS (id)
);

CREATE TABLE IF NOT EXISTS film_user_ratings
(
    id bigint auto_increment,
    user_id bigint NOT NULL,
    film_id bigint NOT NULL,
    created_at timestamp DEFAULT NOW() NOT NULL,
    CONSTRAINT film_user_ratings_pk PRIMARY KEY (id),
    CONSTRAINT film_user_ratings_users_id_fk FOREIGN KEY (user_id) REFERENCES USERS (id),
    CONSTRAINT film_user_ratings_films_id_fk FOREIGN KEY (film_id) REFERENCES FILMS (id)
);