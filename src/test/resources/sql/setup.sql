DROP TABLE IF EXISTS gift_certificate CASCADE ;
DROP TABLE IF EXISTS tag CASCADE ;
DROP TABLE IF EXISTS gift_certificate_tag;

CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255)   NOT NULL,
    description      TEXT           NOT NULL,
    price            NUMERIC(10, 2) NOT NULL,
    duration         INTEGER        NOT NULL,
    create_date      TIMESTAMP      NOT NULL DEFAULT current_timestamp,
    last_update_date TIMESTAMP      NOT NULL DEFAULT current_timestamp
);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE gift_certificate_tag
(
    gift_certificate_id INTEGER NOT NULL,
    tag_id              INTEGER NOT NULL,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);
