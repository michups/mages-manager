DROP DATABASE IF EXISTS mages;

DROP USER IF EXISTS 'mage_admin';

CREATE DATABASE mages;

CREATE USER 'mage_admin' IDENTIFIED BY 'm4g34dm1n';

GRANT ALL ON mages.* TO 'mage_admin';

USE mages;

CREATE TABLE woods (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    toughness INT,
    PRIMARY KEY (id)
);

CREATE TABLE cores (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    power INT,
    consistency INT,
    PRIMARY KEY (id)
);

CREATE TABLE wands (
	id INT NOT NULL AUTO_INCREMENT,
    wood INT NOT NULL,
    core INT NOT NULL,
    production_date DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (wood) REFERENCES woods (id),
    FOREIGN KEY (core) REFERENCES cores (id)
);

CREATE TABLE mages (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    wand INT UNIQUE,
    supervisor INT,
    PRIMARY KEY (id),
    FOREIGN KEY (wand) REFERENCES wands (id),
    FOREIGN KEY (supervisor) REFERENCES mages (id)
);

CREATE TABLE spells (
	id INT NOT NULL AUTO_INCREMENT,
    incantation VARCHAR(1024),
    PRIMARY KEY (id)
);

CREATE TABLE mages_spells (
	mage INT NOT NULL,
    spell INT NOT NULL,
    PRIMARY KEY (mage, spell),
    FOREIGN KEY (mage) REFERENCES mages (id),
    FOREIGN KEY (spell) REFERENCES spells (id)
    
);

CREATE TABLE spell_books (
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(256),
    author INT NOT NULL,
    publish_date DATETIME, 
    PRIMARY KEY (id),
  FOREIGN KEY (author) REFERENCES mages (id)
);

CREATE TABLE spell_books_spells (
	spell_book INT NOT NULL,
    spell INT NOT NULL,
    PRIMARY KEY (spell_book, spell),
    FOREIGN KEY (spell_book) REFERENCES spell_books (id),
    FOREIGN KEY (spell) REFERENCES spells (id)
    
);
INSERT INTO woods (name, toughness) VALUES
	('Beechwood', 50),
    ('Maple', 60),
    ('Ebony', 30),
    ('Holly', 40);

INSERT INTO cores (name, power, consistency) VALUES
	('Dragon heartstring', 100, 50),
    ('Phoenix feather', 75, 75),
    ('Unicorn hair', 50, 100);

INSERT INTO spells (incantation) VALUES
	('Silencion'),
    ('Accio'),
    ('Lumos'),
    ('Alohomora'),
    ('Expecto Patronum'); 

INSERT INTO wands (wood, core, production_date) VALUES (
	(SELECT id FROM woods WHERE name = 'Beechwood'),
    (SELECT id from cores WHERE name = 'Dragon heartstring'),
    now()
);
INSERT INTO mages (name, wand) VALUES ('Bojan Letvin', (SELECT LAST_INSERT_ID()));

INSERT INTO wands (wood, core, production_date) VALUES (
	(SELECT id FROM woods WHERE name = 'Holly'),
    (SELECT id from cores WHERE name = 'Phoenix feather'),
    '2015-07-01 09:01:15'
);
INSERT INTO mages (name, wand) VALUES ('Zhivko Bernatsky', (SELECT LAST_INSERT_ID()));

INSERT INTO wands (wood, core, production_date) VALUES (
	(SELECT id FROM woods WHERE name = 'Maple'),
    (SELECT id from cores WHERE name = 'Unicorn hair'),
    '1999-05-01 22:00:00'
);
INSERT INTO mages (name, wand) VALUES ('Blagoja Petrovics', (SELECT LAST_INSERT_ID()));

INSERT INTO wands (wood, core, production_date) VALUES (
	(SELECT id FROM woods WHERE name = 'Maple'),
    (SELECT id from cores WHERE name = 'Unicorn hair'),
    now()
);
INSERT INTO mages (name, wand) VALUES ('Jaropluk Yablonsky', (SELECT LAST_INSERT_ID()));

INSERT INTO mages_spells (mage, spell) VALUES
	((SELECT id FROM mages WHERE name = 'Bojan Letvin'),
     (SELECT id FROM spells WHERE incantation = 'Expecto Patronum')),
	((SELECT id FROM mages WHERE name = 'Bojan Letvin'),
     (SELECT id FROM spells WHERE incantation = 'Silencion')),
    ((SELECT id FROM mages WHERE name = 'Zhivko Bernatsky'),
     (SELECT id FROM spells WHERE incantation = 'Expecto Patronum')),
    ((SELECT id FROM mages WHERE name = 'Zhivko Bernatsky'),
     (SELECT id FROM spells WHERE incantation = 'Accio')),
    ((SELECT id FROM mages WHERE name = 'Blagoja Petrovics'),
     (SELECT id FROM spells WHERE incantation = 'Expecto Patronum')),
    ((SELECT id FROM mages WHERE name = 'Blagoja Petrovics'),
     (SELECT id FROM spells WHERE incantation = 'Alohomora')),
    ((SELECT id FROM mages WHERE name = 'Jaropluk Yablonsky'),
     (SELECT id FROM spells WHERE incantation = 'Expecto Patronum')),
    ((SELECT id FROM mages WHERE name = 'Jaropluk Yablonsky'),
     (SELECT id FROM spells WHERE incantation = 'Alohomora'));













INSERT INTO spell_books (title, author, publish_date) VALUES ( 
    'Best spells you can get. Volume 1 (A-Al)',
    (SELECT id FROM mages WHERE name = 'Blagoja Petrovics') ,
    '1234-12-23 12:34:56'
);
INSERT INTO spell_books_spells (spell_book, spell) VALUES(
	(SELECT id FROM spell_books WHERE title = 'Best spells you can get. Volume 1 (A-Al)'),
    (SELECT id FROM spells WHERE incantation = 'Accio')),
    
    ((SELECT id FROM spell_books WHERE title = 'Best spells you can get. Volume 1 (A-Al)'),
    (SELECT id FROM spells WHERE incantation = 'Alohomora') 
    
);
 
 

INSERT INTO spell_books (title, author, publish_date) VALUES ( 
    'Become a thief in one day',
    (SELECT id FROM mages WHERE name = 'Bojan Letvin') ,
    '1234-12-23 12:34:56'
);
INSERT INTO spell_books_spells (spell_book, spell) VALUES(
	(SELECT id FROM spell_books WHERE title = 'Become a thief in one day'),
    (SELECT id FROM spells WHERE incantation = 'Accio')),
    
	((SELECT id FROM spell_books WHERE title = 'Become a thief in one day'),
    (SELECT id FROM spells WHERE incantation = 'Silencion')),
    
    ((SELECT id FROM spell_books WHERE title = 'Become a thief in one day'),
    (SELECT id FROM spells WHERE incantation = 'Alohomora') 
    
);



INSERT INTO spell_books (title, author, publish_date) VALUES ( 
    'Picking up a girl - complite guide for dummies',
    (SELECT id FROM mages WHERE name = 'Zhivko Bernatsky') ,
    '1269-12-23 12:12:56'
);
INSERT INTO spell_books_spells (spell_book, spell) VALUES(
	(SELECT id FROM spell_books WHERE title = 'Picking up a girl - complite guide for dummies'),
    (SELECT id FROM spells WHERE incantation = 'Accio')),
    
	((SELECT id FROM spell_books WHERE title = 'Picking up a girl - complite guide for dummies'),
    (SELECT id FROM spells WHERE incantation = 'Silencion') 
    
);



INSERT INTO spell_books (title, author, publish_date) VALUES ( 
    'Self-defense for weaklings',
    (SELECT id FROM mages WHERE name = 'Bojan Letvin') ,
    now()
);
INSERT INTO spell_books_spells (spell_book, spell) VALUES(
	(SELECT id FROM spell_books WHERE title = 'Self-defense for weaklings'),
    (SELECT id FROM spells WHERE incantation = 'Accio')),
    
	((SELECT id FROM spell_books WHERE title = 'Self-defense for weaklings'),
    (SELECT id FROM spells WHERE incantation = 'Expecto Patronum') 
    
); 