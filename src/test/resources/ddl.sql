CREATE TABLE IF NOT EXISTS tag (
	id INT(11) AUTO_INCREMENT,
	name varchar(32) NOT NULL,
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

CREATE TABLE IF NOT EXISTS tag_giftcertificates (
    tag_id int(11) NOT NULL,
    giftcertificates_id int(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS user (
    id INT(11) NOT NULL AUTO_INCREMENT,
    login VARCHAR(64) UNIQUE NOT NULL,
    password varchar(100) NOT NULL,
    role varchar(10) NOT NULL,
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






















explain select tag.id, tag.name from tag
inner join tag_giftcertificates on tag.id = tag_giftcertificates.tag_id
inner join giftcertificates on giftcertificates.id = tag_giftcertificates.giftcertificates_id
inner join user_giftcertificates on giftcertificates.id = user_giftcertificates.giftcertificates_id
group by tag.name
order by sum(user_giftcertificates.price) desc limit 1;



