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

