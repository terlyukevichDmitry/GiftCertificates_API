DROP TABLE tag_giftcertificates;
DROP TABLE tag;
DROP TABLE user_giftcertificates;
DROP TABLE user;
DROP TABLE giftcertificates;

CREATE TABLE IF NOT EXISTS tag (
	id INT(11) AUTO_INCREMENT,
	name varchar(32) UNIQUE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS giftcertificates (
    id INT(11) AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    description varchar(512) NOT NULL,
    price DOUBLE NOT NULL,
    createDate varchar(21) NOT NULL,
    lastUpdateDate varchar(21) NOT NULL,
    duration INTEGER NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX indexForName ON giftcertificates(name);

CREATE TABLE IF NOT EXISTS tag_giftcertificates (
    tag_id int(11) NOT NULL,
    giftcertificates_id int(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS user (
    id INT(11) NOT NULL AUTO_INCREMENT,
    login VARCHAR(64) UNIQUE NOT NULL,
    password varchar(100) NOT NULL,
    role varchar(16) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_giftcertificates (
	purchaseDate varchar(21) NOT NULL,
	price DOUBLE NOT NULL,
    user_id int(11) NOT NULL,
    giftcertificates_id int(11) NOT NULL
);

ALTER TABLE tag_giftcertificates ADD CONSTRAINT tag_giftcertificates_v1 FOREIGN KEY (tag_id) REFERENCES tag(id);

ALTER TABLE tag_giftcertificates ADD CONSTRAINT tag_giftcertificates_v2 FOREIGN KEY (giftcertificates_id) REFERENCES giftcertificates(id);

ALTER TABLE user_giftcertificates ADD CONSTRAINT user_giftcertificates_v1 FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE user_giftcertificates ADD CONSTRAINT user_giftcertificates_v2 FOREIGN KEY (giftcertificates_id) REFERENCES giftcertificates(id);































