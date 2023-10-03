create table gift_certificate
(
    id               bigint         not null
        primary key,
    create_date      timestamp      not null,
    description      text           not null,
    duration         integer        not null,
    last_update_date timestamp      not null,
    name             varchar(255),
    price            numeric(10, 2) not null
);

alter table gift_certificate
    owner to postgres;

create table revinfo
(
    rev      integer not null
        primary key,
    revtstmp bigint
);

alter table revinfo
    owner to postgres;

create table gift_certificate_aud
(
    id               bigint  not null,
    rev              integer not null
        constraint fkhriym6x1m3uyap2l3lxfmitku
            references revinfo,
    revtype          smallint,
    create_date      timestamp,
    description      text,
    duration         integer,
    last_update_date timestamp,
    name             varchar(255),
    price            numeric(10, 2),
    primary key (rev, id)
);

alter table gift_certificate_aud
    owner to postgres;

create table gift_certificate_tag_aud
(
    rev                 integer not null
        constraint fk8ja9vo8r28smftruecdwx9ir0
            references revinfo,
    gift_certificate_id bigint  not null,
    tag_id              bigint  not null,
    revtype             smallint,
    primary key (gift_certificate_id, rev, tag_id)
);

alter table gift_certificate_tag_aud
    owner to postgres;

create table order_gift_certificate_aud
(
    rev                 integer not null
        constraint fk1awe3ngq48mkjfbjmfk4cjoyq
            references revinfo,
    order_id            bigint  not null,
    gift_certificate_id bigint  not null,
    revtype             smallint,
    primary key (order_id, rev, gift_certificate_id)
);

alter table order_gift_certificate_aud
    owner to postgres;

create table orders_aud
(
    id          bigint  not null,
    rev         integer not null
        constraint fkinujab7ljkelflu16c9jjch19
            references revinfo,
    revtype     smallint,
    create_date timestamp(6),
    price       numeric(10, 2),
    user_id     bigint,
    primary key (rev, id)
);

alter table orders_aud
    owner to postgres;

create table role
(
    id   bigint       not null
        primary key,
    name varchar(255) not null
        constraint role_name
            unique
);

alter table role
    owner to postgres;

create table role_aud
(
    id      bigint  not null,
    rev     integer not null
        constraint fkrks7qtsmup3w81fdp0d6omfk7
            references revinfo,
    revtype smallint,
    name    varchar(255),
    primary key (rev, id)
);

alter table role_aud
    owner to postgres;

create table tag
(
    id   bigint       not null
        primary key,
    name varchar(255) not null
        constraint tag_name
            unique
);

alter table tag
    owner to postgres;

create table gift_certificate_tag
(
    gift_certificate_id bigint not null
        constraint fka9orffdp51dqmamwv59d01rf1
            references gift_certificate,
    tag_id              bigint not null
        constraint fk5tjjbkwfbad84jkeobe07owf9
            references tag,
    primary key (gift_certificate_id, tag_id)
);

alter table gift_certificate_tag
    owner to postgres;

create table tag_aud
(
    id      bigint  not null,
    rev     integer not null
        constraint fkep272jdrgxgmq608l5y3792jn
            references revinfo,
    revtype smallint,
    name    varchar(255),
    primary key (rev, id)
);

alter table tag_aud
    owner to postgres;

create table users
(
    id         bigint       not null
        primary key,
    email      varchar(255) not null
        constraint "user-email"
            unique,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255) not null,
    role_id    bigint       not null
        constraint fk4qu1gr772nnf6ve5af002rwya
            references role
);

alter table users
    owner to postgres;

create table orders
(
    id          bigint         not null
        primary key,
    create_date timestamp(6)   not null,
    price       numeric(10, 2) not null,
    user_id     bigint         not null
        constraint fk32ql8ubntj5uh44ph9659tiih
            references users
);

alter table orders
    owner to postgres;

create table order_gift_certificate
(
    order_id            bigint not null
        constraint fkceisvy50h6nvkyann4fkh38te
            references orders,
    gift_certificate_id bigint not null
        constraint fkjsut7sjrnmbdqeae9tqfvf3l6
            references gift_certificate,
    primary key (order_id, gift_certificate_id)
);

alter table order_gift_certificate
    owner to postgres;

create table users_aud
(
    id         bigint  not null,
    rev        integer not null
        constraint fkc4vk4tui2la36415jpgm9leoq
            references revinfo,
    revtype    smallint,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role_id    bigint,
    primary key (rev, id)
);

alter table users_aud
    owner to postgres;

create sequence certificate_sequence START 100;

alter sequence certificate_sequence owner to postgres;

create sequence order_sequence START 100;

alter sequence order_sequence owner to postgres;

create sequence revinfo_seq
    increment by 50;

alter sequence revinfo_seq owner to postgres;

create sequence tag_sequence START 100;

alter sequence tag_sequence owner to postgres;

create sequence user_sequence START 100;

alter sequence user_sequence owner to postgres;

INSERT INTO role (id, name)
VALUES (1, 'ADMIN_ROLE');

INSERT INTO users (id, first_name, last_name, email, password, role_id)
VALUES (1, 'John', 'Doe', 'admin@gmail.com', '$2a$10$HMyJUv0VPwmJ2b3EUPPaKeL2VLjynfLU8Tb3Zqc02U1R5Led.BB02', 1);

INSERT INTO gift_certificate (id, create_date, description, duration, last_update_date, name, price)
VALUES
    (1, NOW(), 'Experience tranquility with our Relaxation Package, including massage and spa treatments.', 30, NOW(), 'Relaxation Package', 49.99),
    (2, NOW(), 'Embark on a culinary journey with our Gourmet Dining Experience at top-rated restaurants.', 45, NOW(), 'Gourmet Dining Experience', 79.99),
    (3, NOW(), 'Unleash your creativity with our Artistic Workshop - perfect for aspiring artists of all levels.', 60, NOW(), 'Artistic Workshop', 69.99),
    (4, NOW(), 'Discover the thrill of adventure with our Outdoor Expedition - hiking, camping, and more.', 30, NOW(), 'Outdoor Expedition', 59.99),
    (5, NOW(), 'Indulge in the ultimate pampering with our Luxury Spa Retreat and rejuvenating treatments.', 45, NOW(), 'Luxury Spa Retreat', 89.99),
    (6, NOW(), 'Experience the adrenaline rush with our Extreme Sports Package - skydiving, bungee jumping, and more.', 15, NOW(), 'Extreme Sports Package', 99.99),
    (7, NOW(), 'Enjoy a romantic getaway with our Couples Retreat - luxury accommodation, candlelight dinner, and more.', 60, NOW(), 'Couples Retreat', 129.99),
    (8, NOW(), 'Unwind in nature with our Nature Escape - explore scenic trails, wildlife, and peaceful surroundings.', 45, NOW(), 'Nature Escape', 69.99),
    (9, NOW(), 'Ignite your taste buds with our Wine Tasting Tour - savor exquisite wines and learn from expert sommeliers.', 90, NOW(), 'Wine Tasting Tour', 109.99),
    (10, NOW(), 'Experience the magic of live performances with our Theater Night - tickets to top Broadway shows.', 30, NOW(), 'Theater Night', 79.99),
    (11, NOW(), 'Discover the underwater world with our Scuba Diving Adventure - explore coral reefs and marine life.', 45, NOW(), 'Scuba Diving Adventure', 129.99),
    (12, NOW(), 'Satisfy your sweet tooth with our Dessert Lovers Delight - a dessert tasting experience.', 30, NOW(), 'Dessert Lovers Delight', 39.99),
    (13, NOW(), 'Elevate your fitness game with our Personal Training Sessions - achieve your health goals.', 60, NOW(), 'Personal Training Sessions', 79.99),
    (14, NOW(), 'Capture memories with our Photography Workshop - learn photography skills from experts.', 45, NOW(), 'Photography Workshop', 69.99),
    (15, NOW(), 'Relish the flavors of Japan with our Sushi Masterclass - roll, nigiri, and sashimi making.', 90, NOW(), 'Sushi Masterclass', 89.99),
    (16, NOW(), 'Feel the wind in your hair with our Hot Air Balloon Ride - enjoy breathtaking views from above.', 60, NOW(), 'Hot Air Balloon Ride', 149.99),
    (17, NOW(), 'Escape to a tropical paradise with our Beach Getaway - sun, sand, and relaxation.', 5, NOW(), 'Beach Getaway', 199.99),
    (18, NOW(), 'Unleash your inner chef with our Cooking Class - hands-on culinary experience.', 75, NOW(), 'Cooking Class', 59.99),
    (19, NOW(), 'Experience the thrill of the racetrack with our Exotic Car Racing - drive high-performance cars.', 30, NOW(), 'Exotic Car Racing', 199.99),
    (20, NOW(), 'Embark on a wine lovers journey with our Vineyard Tour and Wine Tasting.', 60, NOW(), 'Vineyard Tour and Wine Tasting', 79.99),
    (21, NOW(), 'Pamper yourself with a Spa Day - massages, facials, and relaxation in a luxurious spa.', 45, NOW(), 'Spa Day', 99.99),
    (22, NOW(), 'Journey to the stars with our Stargazing Experience - explore the night sky with telescopes.', 90, NOW(), 'Stargazing Experience', 49.99),
    (23, NOW(), 'Treat your senses to a Chocolate Tasting Adventure - indulge in premium chocolates.', 30, NOW(), 'Chocolate Tasting Adventure', 59.99),
    (24, NOW(), 'Unwind and rejuvenate with our Yoga Retreat - find inner peace in serene surroundings.', 60, NOW(), 'Yoga Retreat', 69.99),
    (25, NOW(), 'Experience the thrill of Formula 1 with our Grand Prix VIP Access - exclusive race experience.', 2, NOW(), 'Grand Prix VIP Access', 499.99),
    (26, NOW(), 'Savor the finest steaks with our Steakhouse Dining Experience - premium cuts and flavors.', 90, NOW(), 'Steakhouse Dining Experience', 129.99),
    (27, NOW(), 'Embark on a cultural journey with our Museum Pass - explore art, history, and more.', 30, NOW(), 'Museum Pass', 29.99),
    (28, NOW(), 'Get your adrenaline pumping with our Zip Line Adventure - soar through the treetops.', 15, NOW(), 'Zip Line Adventure', 79.99),
    (29, NOW(), 'Enjoy a day of horseback riding with our Equestrian Adventure - scenic trails and horse bonding.', 60, NOW(), 'Equestrian Adventure', 89.99),
    (30, NOW(), 'Relax and unwind in a private cabin with our Mountain Retreat - nature at its best.', 7, NOW(), 'Mountain Retreat', 249.99),
    (31, NOW(), 'Experience the thrill of a Helicopter Tour - breathtaking aerial views of your favorite city.', 30, NOW(), 'Helicopter Tour', 199.99),
    (32, NOW(), 'Sail the open seas with our Yacht Adventure - luxury yacht, gourmet dining, and more.', 2, NOW(), 'Yacht Adventure', 999.99),
    (33, NOW(), 'Embark on a cultural exploration with our City Museum Pass - access to top museums.', 7, NOW(), 'City Museum Pass', 49.99),
    (34, NOW(), 'Discover your inner artist with our Pottery Workshop - create unique ceramics.', 45, NOW(), 'Pottery Workshop', 79.99),
    (35, NOW(), 'Experience the thrill of Indoor Skydiving - feel the rush in a safe and controlled environment.', 15, NOW(), 'Indoor Skydiving', 49.99),
    (36, NOW(), 'Treat yourself to a Wine and Cheese Tasting - a delightful pairing of flavors.', 60, NOW(), 'Wine and Cheese Tasting', 69.99),
    (37, NOW(), 'Get up close and personal with wildlife on our Safari Adventure - safari tour and wildlife encounters.', 3, NOW(), 'Safari Adventure', 349.99),
    (38, NOW(), 'Escape to a secluded cabin in the woods with our Forest Retreat - tranquility and nature.', 7, NOW(), 'Forest Retreat', 199.99),
    (39, NOW(), 'Explore the depths with our Submarine Expedition - an underwater journey like no other.', 90, NOW(), 'Submarine Expedition', 299.99),
    (40, NOW(), 'Indulge in a Spa and Wine Weekend - relaxation and wine tasting in a beautiful setting.', 2, NOW(), 'Spa and Wine Weekend', 249.99),
    (41, NOW(), 'Experience the magic of a Broadway Show - front-row seats to a world-class performance.', 3, NOW(), 'Broadway Show', 199.99),
    (42, NOW(), 'Unleash your inner detective with our Escape Room Challenge - solve puzzles and mysteries.', 2, NOW(), 'Escape Room Challenge', 69.99),
    (43, NOW(), 'Treat your taste buds to a Gourmet Cooking Class - master the art of fine cuisine.', 60, NOW(), 'Gourmet Cooking Class', 89.99),
    (44, NOW(), 'Discover the wonders of the night sky with our Astronomy Night - telescope observation and star stories.', 3, NOW(), 'Astronomy Night', 59.99),
    (45, NOW(), 'Experience the thrill of Whitewater Rafting - navigate exciting rapids in stunning locations.', 1, NOW(), 'Whitewater Rafting', 129.99),
    (46, NOW(), 'Get lost in the world of books with our Bookstore Shopping Spree - find your next favorite read.', 4, NOW(), 'Bookstore Shopping Spree', 79.99),
    (47, NOW(), 'Embark on a Historical Walking Tour - explore the rich history of your city with an expert guide.', 4, NOW(), 'Historical Walking Tour', 39.99),
    (48, NOW(), 'Relax and rejuvenate with a Couples Spa Retreat - massages, facials, and quality time together.', 2, NOW(), 'Couples Spa Retreat', 299.99),
    (49, NOW(), 'Experience the thrill of Rock Climbing - conquer challenging cliffs with expert instructors.', 1, NOW(), 'Rock Climbing Adventure', 89.99),
    (50, NOW(), 'Discover the art of Mixology with our Cocktail Making Class - craft your favorite drinks.', 3, NOW(), 'Cocktail Making Class', 69.99),
    (51, NOW(), 'Enjoy the serenity of a Lakeside Cabin Getaway - fishing, boating, and relaxation.', 5, NOW(), 'Lakeside Cabin Getaway', 249.99),
    (52, NOW(), 'Embark on a Culinary Tour - savor local flavors and dishes on a guided food adventure.', 7, NOW(), 'Culinary Tour', 79.99),
    (53, NOW(), 'Explore the wonders of the ocean with a Snorkeling Expedition - colorful coral reefs and marine life.', 2, NOW(), 'Snorkeling Expedition', 59.99),
    (54, NOW(), 'Savor the flavors of a Wine and Chocolate Pairing - a delightful combination of two indulgences.', 2, NOW(), 'Wine and Chocolate Pairing', 49.99),
    (55, NOW(), 'Experience the thrill of Horse Racing - VIP access to the racetrack and live horse racing.', 1, NOW(), 'Horse Racing VIP', 199.99),
    (56, NOW(), 'Unwind with a Garden Spa Retreat - surrounded by lush greenery and serenity.', 4, NOW(), 'Garden Spa Retreat', 149.99),
    (57, NOW(), 'Discover the art of Archery - learn precision and focus with expert archery instructors.', 2, NOW(), 'Archery Lessons', 59.99),
    (58, NOW(), 'Experience the thrill of a Helicopter Ski Trip - heli-skiing in breathtaking mountain terrain.', 3, NOW(), 'Helicopter Ski Trip', 499.99),
    (59, NOW(), 'Treat yourself to a Wine and Jazz Evening - live jazz music and fine wine in an intimate setting.', 4, NOW(), 'Wine and Jazz Evening', 79.99),
    (60, NOW(), 'Embark on a Cultural Exchange Program - immerse yourself in a different culture and community.', 30, NOW(), 'Cultural Exchange Program', 499.99);

    INSERT INTO tag (id, name)
    VALUES
        (1, 'Adventure'),
        (2, 'Culinary'),
        (3, 'Relaxation'),
        (4, 'Art'),
        (5, 'Nature'),
        (6, 'Wine'),
        (7, 'Safari'),
        (8, 'Outdoor'),
        (9, 'Culture'),
        (10, 'Spa');


    INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
    VALUES
        (1, 3),  -- Relaxation
        (2, 2),  -- Culinary
        (3, 4),  -- Art
        (4, 1),  -- Adventure
        (5, 10), -- Spa
        (6, 1),  -- Adventure
        (7, 10), -- Spa
        (8, 5),  -- Nature
        (9, 6),  -- Wine
        (10, 9), -- Culture
        (11, 1), -- Adventure
        (12, 2), -- Culinary
        (13, 8), -- Outdoor
        (14, 4), -- Art
        (15, 2), -- Culinary
        (16, 1), -- Adventure
        (17, 5), -- Nature
        (18, 2), -- Culinary
        (19, 1), -- Adventure
        (20, 6), -- Wine
        (21, 10), -- Spa
        (22, 9),  -- Culture
        (23, 2), -- Culinary
        (24, 10), -- Spa
        (25, 1), -- Adventure
        (26, 2), -- Culinary
        (27, 9), -- Culture
        (28, 1), -- Adventure
        (29, 5), -- Nature
        (30, 10), -- Spa
        (36, 4),  -- Art
        (36, 10), -- Spa
        (37, 1),  -- Adventure
        (37, 9),  -- Culture
        (38, 5),  -- Nature
        (38, 8),  -- Outdoor
        (39, 9),  -- Culture
        (39, 10), -- Spa
        (40, 10), -- Spa
        (40, 6),  -- Wine
        (41, 3),  -- Relaxation
        (41, 2),  -- Culinary
        (42, 1),  -- Adventure
        (42, 8),  -- Outdoor
        (43, 2),  -- Culinary
        (43, 9),  -- Culture
        (44, 4),  -- Art
        (44, 3),  -- Relaxation
        (45, 2),  -- Culinary
        (45, 10), -- Spa
        (46, 5),  -- Nature
        (46, 4),  -- Art
        (47, 9),  -- Culture
        (47, 10), -- Spa
        (48, 10), -- Spa
        (48, 6),  -- Wine
        (49, 1),  -- Adventure
        (49, 8),  -- Outdoor
        (50, 3),  -- Relaxation
        (50, 6);  -- Wine