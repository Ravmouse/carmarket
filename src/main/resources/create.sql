CREATE DATABASE carmarket;
---------------------------------------------------
DROP TABLE IF EXISTS carbody;
CREATE TABLE carbody (
  id SERIAL PRIMARY KEY,
  name VARCHAR (255) NOT NULL
);
INSERT INTO carbody VALUES (1, 'Седан');
INSERT INTO carbody VALUES (2, 'Хэтчбек');
INSERT INTO carbody VALUES (3, 'Универсал');
---------------------------------------------------
DROP TABLE IF EXISTS engine;
CREATE TABLE engine (
  id SERIAL PRIMARY KEY,
  name VARCHAR (255) NOT NULL
);
INSERT INTO engine VALUES (1, 'Бензин');
INSERT INTO engine VALUES (2, 'Дизель');
INSERT INTO engine VALUES (3, 'Газ');
---------------------------------------------------
DROP TABLE IF EXISTS model;
CREATE TABLE model (
  id SERIAL PRIMARY KEY,
  name VARCHAR (255) NOT NULL
);
INSERT INTO model VALUES (1, 'CEED');
INSERT INTO model VALUES (2, 'Rio');
INSERT INTO model VALUES (3, 'X5');
INSERT INTO model VALUES (4, '3 серия');
INSERT INTO model VALUES (5, 'Mondeo');
INSERT INTO model VALUES (6, 'Focus');
INSERT INTO model VALUES (7, 'Golf');
INSERT INTO model VALUES (8, 'Passat');
INSERT INTO model VALUES (9, 'Astra');
INSERT INTO model VALUES (10, 'Corsa');
---------------------------------------------------
DROP TABLE IF EXISTS brand;
CREATE TABLE brand (
  id SERIAL PRIMARY KEY,
  name VARCHAR (255) NOT NULL
);
INSERT INTO brand VALUES (1, 'Kia');
INSERT INTO brand VALUES (2, 'BMW');
INSERT INTO brand VALUES (3, 'Ford');
INSERT INTO brand VALUES (4, 'Volkswagen');
INSERT INTO brand VALUES (5, 'Opel');
---------------------------------------------------
DROP TABLE IF EXISTS transmission;
CREATE TABLE transmission (
  id SERIAL PRIMARY KEY,
  name VARCHAR (255) NOT NULL
);
INSERT INTO transmission VALUES (1, 'Механика');
INSERT INTO transmission VALUES (2, 'Автомат');
INSERT INTO transmission VALUES (3, 'Вариатор');
---------------------------------------------------
DROP TABLE IF EXISTS car;
CREATE TABLE car (
  id SERIAL PRIMARY KEY,
  year TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  brand_id INT REFERENCES brand(id) NOT NULL,
  model_id INT REFERENCES model(id) NOT NULL,
  transmission_id INT REFERENCES transmission(id) NOT NULL,
  carbody_id INT REFERENCES carbody(id) NOT NULL,
  engine_id INT REFERENCES engine(id) NOT NULL
);
---------------------------------------------------
DROP TABLE IF EXISTS offer;
CREATE TABLE offer (
  id SERIAL PRIMARY KEY,
  car_id INT REFERENCES car(id) NOT NULL,
  image_name VARCHAR(255) NOT NULL,
  sold BOOLEAN
);
---------------------------------------------------
DROP TABLE IF EXISTS offer_user;
CREATE TABLE offer_user (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);
INSERT INTO offer_user VALUES (1, 'Administrator', 'password');
INSERT INTO offer_user VALUES (2, 'Moderator', 'mod');
---------------------------------------------------
ALTER TABLE offer ADD COLUMN offeruser_id INT REFERENCES offer_user(id) NOT NULL DEFAULT 1;
---------------------------------------------------
ALTER TABLE offer ADD COLUMN create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now();