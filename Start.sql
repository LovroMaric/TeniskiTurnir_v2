-- =========================================
-- TENNIS APP DATABASE INIT SCRIPT
-- PostgreSQL
-- =========================================

-- =========================
-- DROP FUNCTIONS / PROCEDURES (za ponovno pokretanje skripte)
-- =========================

DROP FUNCTION IF EXISTS login_user(TEXT, TEXT);
DROP FUNCTION IF EXISTS get_players();
DROP FUNCTION IF EXISTS get_tournaments();
DROP FUNCTION IF EXISTS get_countries();
DROP FUNCTION IF EXISTS get_categories();

DROP PROCEDURE IF EXISTS register_user(VARCHAR, VARCHAR);
DROP PROCEDURE IF EXISTS create_admin(TEXT, TEXT);
DROP PROCEDURE IF EXISTS add_player(VARCHAR, VARCHAR, INT, TEXT, BIGINT);
DROP PROCEDURE IF EXISTS update_player(BIGINT, VARCHAR, VARCHAR, INT, TEXT, BIGINT);
DROP PROCEDURE IF EXISTS delete_player(BIGINT);
DROP PROCEDURE IF EXISTS add_tournament(VARCHAR, INT, NUMERIC, VARCHAR, TEXT, BIGINT, BIGINT);
DROP PROCEDURE IF EXISTS update_tournament(BIGINT, VARCHAR, INT, NUMERIC, VARCHAR, TEXT, BIGINT, BIGINT);
DROP PROCEDURE IF EXISTS delete_tournament(BIGINT);
DROP PROCEDURE IF EXISTS add_player_to_tournament(BIGINT, BIGINT, VARCHAR);
DROP PROCEDURE IF EXISTS add_country(VARCHAR);
DROP PROCEDURE IF EXISTS update_country(BIGINT, VARCHAR);
DROP PROCEDURE IF EXISTS delete_country(BIGINT);

-- =========================
-- DROP TABLES
-- =========================

DROP TABLE IF EXISTS tournament_player CASCADE;
DROP TABLE IF EXISTS tournament_sponsor CASCADE;
DROP TABLE IF EXISTS tournament CASCADE;
DROP TABLE IF EXISTS player CASCADE;
DROP TABLE IF EXISTS sponsor CASCADE;
DROP TABLE IF EXISTS country CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- =========================
-- TABLES
-- =========================

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL
);

CREATE TABLE country (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE category (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE sponsor (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE player (
                        id SERIAL PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        ranking INT,
                        image_path TEXT,
                        country_id INT REFERENCES country(id)
);

CREATE TABLE tournament (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(150) NOT NULL,
                            founded_year INT,
                            prize_money DECIMAL(15,2),
                            surface VARCHAR(20),
                            image_path TEXT,
                            country_id INT REFERENCES country(id),
                            category_id INT REFERENCES category(id)
);

CREATE TABLE tournament_player (
                                   id SERIAL PRIMARY KEY,
                                   tournament_id INT NOT NULL REFERENCES tournament(id) ON DELETE CASCADE,
                                   player_id INT NOT NULL REFERENCES player(id) ON DELETE CASCADE,
                                   result VARCHAR(100),
                                   UNIQUE(tournament_id, player_id)
);

CREATE TABLE tournament_sponsor (
                                    id SERIAL PRIMARY KEY,
                                    tournament_id INT NOT NULL REFERENCES tournament(id) ON DELETE CASCADE,
                                    sponsor_id INT NOT NULL REFERENCES sponsor(id) ON DELETE CASCADE
);

-- =========================================
-- USERS
-- =========================================

CREATE OR REPLACE FUNCTION login_user(
    p_username TEXT,
    p_password TEXT
)
    RETURNS TABLE(id INT, username VARCHAR, password VARCHAR, role VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT u.id, u.username, u.password, u.role
        FROM users u
        WHERE u.username = p_username
          AND u.password = p_password;
END;
$$;

CREATE OR REPLACE PROCEDURE register_user(
    p_username VARCHAR,
    p_password VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO users(username, password, role)
    VALUES (p_username, p_password, 'USER');
END;
$$;

CREATE OR REPLACE PROCEDURE create_admin(
    p_username TEXT,
    p_password TEXT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO users(username, password, role)
    VALUES (p_username, p_password, 'ADMIN');
END;
$$;

-- =========================================
-- PLAYER
-- =========================================

CREATE OR REPLACE PROCEDURE add_player(
    p_first_name VARCHAR,
    p_last_name VARCHAR,
    p_ranking INT,
    p_image_path TEXT,
    p_country_id BIGINT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO player(first_name, last_name, ranking, image_path, country_id)
    VALUES (p_first_name, p_last_name, p_ranking, p_image_path, p_country_id);
END;
$$;

CREATE OR REPLACE PROCEDURE update_player(
    p_id BIGINT,
    p_first_name VARCHAR,
    p_last_name VARCHAR,
    p_ranking INT,
    p_image_path TEXT,
    p_country_id BIGINT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE player
    SET first_name = p_first_name,
        last_name = p_last_name,
        ranking = p_ranking,
        image_path = p_image_path,
        country_id = p_country_id
    WHERE id = p_id;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_player(p_id BIGINT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM player WHERE id = p_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_players()
    RETURNS TABLE(
                     id INT,
                     first_name VARCHAR,
                     last_name VARCHAR,
                     ranking INT,
                     image_path TEXT,
                     country_id INT,
                     country_name VARCHAR
                 )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT p.id, p.first_name, p.last_name, p.ranking, p.image_path, c.id, c.name
        FROM player p
                 LEFT JOIN country c ON p.country_id = c.id
        ORDER BY p.id;
END;
$$;


-- =========================================
-- TOURNAMENT
-- =========================================

CREATE OR REPLACE PROCEDURE add_tournament(
    p_name VARCHAR,
    p_founded_year INT,
    p_prize_money NUMERIC,
    p_surface VARCHAR,
    p_image_path TEXT,
    p_country_id BIGINT,
    p_category_id BIGINT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO tournament(name, founded_year, prize_money, surface, image_path, country_id, category_id)
    VALUES (p_name, p_founded_year, p_prize_money, p_surface, p_image_path, p_country_id, p_category_id);
END;
$$;

CREATE OR REPLACE PROCEDURE update_tournament(
    p_id BIGINT,
    p_name VARCHAR,
    p_founded_year INT,
    p_prize_money NUMERIC,
    p_surface VARCHAR,
    p_image_path TEXT,
    p_country_id BIGINT,
    p_category_id BIGINT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE tournament
    SET name = p_name,
        founded_year = p_founded_year,
        prize_money = p_prize_money,
        surface = p_surface,
        image_path = p_image_path,
        country_id = p_country_id,
        category_id = p_category_id
    WHERE id = p_id;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_tournament(p_id BIGINT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM tournament WHERE id = p_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_tournaments()
    RETURNS TABLE(
                     id INT,
                     name VARCHAR,
                     founded_year INT,
                     prize_money NUMERIC,
                     surface VARCHAR,
                     image_path TEXT,
                     country_id INT,
                     country_name VARCHAR,
                     category_id INT,
                     category_name VARCHAR
                 )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT t.id, t.name, t.founded_year, t.prize_money, t.surface, t.image_path,
               c.id, c.name, cat.id, cat.name
        FROM tournament t
                 JOIN country c ON t.country_id = c.id
                 JOIN category cat ON t.category_id = cat.id
        ORDER BY t.id;
END;
$$;

CREATE OR REPLACE PROCEDURE add_player_to_tournament(
    p_tournament_id BIGINT,
    p_player_id BIGINT,
    p_result VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO tournament_player(tournament_id, player_id, result)
    VALUES (p_tournament_id, p_player_id, p_result);
END;
$$;

-- =========================================
-- COUNTRY
-- =========================================

CREATE OR REPLACE PROCEDURE add_country(p_name VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO country(name) VALUES (p_name);
END;
$$;

CREATE OR REPLACE PROCEDURE update_country(p_id BIGINT, p_name VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE country SET name = p_name WHERE id = p_id;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_country(p_id BIGINT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM country WHERE id = p_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_countries()
    RETURNS TABLE(id INT, name VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT c.id, c.name FROM country c ORDER BY c.id;
END;
$$;

-- =========================================
-- CATEGORY
-- =========================================

CREATE OR REPLACE FUNCTION get_categories()
    RETURNS TABLE(id INT, name VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT cat.id, cat.name FROM category cat ORDER BY cat.id;
END;
$$;

-- =========================================
-- DEFAULT / SEED PODACI
-- =========================================

INSERT INTO country(name) VALUES ('Croatia'), ('Serbia'), ('Spain'), ('Italy');

INSERT INTO category(name) VALUES ('Grand Slam'), ('Masters 1000'), ('ATP 500'), ('ATP 250');

INSERT INTO sponsor(name) VALUES ('Nike'), ('Rolex'), ('Adidas'), ('Wilson');

CALL create_admin('admin', 'admin123');

CALL add_player('Novak', 'Djokovic', 1, 'assets/djokovic.jpg', 2);
CALL add_player('Carlos', 'Alcaraz', 2, 'assets/alcaraz.jpg', 3);

CALL add_tournament('Wimbledon', 1877, 50000000, 'GRASS', 'assets/wimbledon.png', 4, 1);

CALL add_player_to_tournament(1, 1, 'Winner');
CALL add_player_to_tournament(1, 2, 'Finalist');