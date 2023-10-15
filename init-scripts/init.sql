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

INSERT INTO role (id, name)
VALUES (2, 'USER_ROLE');

INSERT INTO users (id, first_name, last_name, email, password, role_id)
VALUES (1, 'John', 'Doe', 'admin@gmail.com', '$2a$10$HMyJUv0VPwmJ2b3EUPPaKeL2VLjynfLU8Tb3Zqc02U1R5Led.BB02', 1);

INSERT INTO gift_certificate (id, create_date, description, duration, last_update_date, name, price)
VALUES
    (1, '2023-10-15 08:00:00', 'Experience tranquility with our Relaxation Package, including massage and spa treatments.', 30, '2023-10-15 08:00:00', 'Relaxation Package', 49.99),
    (2, '2023-10-15 08:01:00', 'Embark on a culinary journey with our Gourmet Dining Experience at top-rated restaurants.', 45, '2023-10-15 08:01:00', 'Gourmet Dining Experience', 79.99),
    (3, '2023-10-15 08:02:00', 'Unleash your creativity with our Artistic Workshop - perfect for aspiring artists of all levels.', 60, '2023-10-15 08:02:00', 'Artistic Workshop', 69.99),
    (4, '2023-10-15 08:03:00', 'Discover the thrill of adventure with our Outdoor Expedition - hiking, camping, and more.', 30, '2023-10-15 08:03:00', 'Outdoor Expedition', 59.99),
    (5, '2023-10-15 08:04:00', 'Indulge in the ultimate pampering with our Luxury Spa Retreat and rejuvenating treatments.', 45, '2023-10-15 08:04:00', 'Luxury Spa Retreat', 89.99),
    (6, '2023-10-15 08:05:00', 'Experience the adrenaline rush with our Extreme Sports Package - skydiving, bungee jumping, and more.', 15, '2023-10-15 08:05:00', 'Extreme Sports Package', 99.99),
    (7, '2023-10-15 08:06:00', 'Enjoy a romantic getaway with our Couples Retreat - luxury accommodation, candlelight dinner, and more.', 60, '2023-10-15 08:06:00', 'Couples Retreat', 129.99),
    (8, '2023-10-15 08:07:00', 'Unwind in nature with our Nature Escape - explore scenic trails, wildlife, and peaceful surroundings.', 45, '2023-10-15 08:07:00', 'Nature Escape', 69.99),
    (9, '2023-10-15 08:08:00', 'Ignite your taste buds with our Wine Tasting Tour - savor exquisite wines and learn from expert sommeliers.', 90, '2023-10-15 08:08:00', 'Wine Tasting Tour', 109.99),
    (10, '2023-10-15 08:09:00', 'Experience the magic of live performances with our Theater Night - tickets to top Broadway shows.', 30, '2023-10-15 08:09:00', 'Theater Night', 79.99),
    (11, '2023-10-15 08:10:00', 'Discover the underwater world with our Scuba Diving Adventure - explore coral reefs and marine life.', 45, '2023-10-15 08:10:00', 'Scuba Diving Adventure', 129.99),
    (12, '2023-10-15 08:11:00', 'Satisfy your sweet tooth with our Dessert Lovers Delight - a dessert tasting experience.', 30, '2023-10-15 08:11:00', 'Dessert Lovers Delight', 39.99),
    (13, '2023-10-15 08:12:00', 'Elevate your fitness game with our Personal Training Sessions - achieve your health goals.', 60, '2023-10-15 08:12:00', 'Personal Training Sessions', 79.99),
    (14, '2023-10-15 08:13:00', 'Capture memories with our Photography Workshop - learn photography skills from experts.', 45, '2023-10-15 08:13:00', 'Photography Workshop', 69.99),
    (15, '2023-10-15 08:14:00', 'Relish the flavors of Japan with our Sushi Masterclass - roll, nigiri, and sashimi making.', 90, '2023-10-15 08:14:00', 'Sushi Masterclass', 89.99),
    (16, '2023-10-15 08:15:00', 'Feel the wind in your hair with our Hot Air Balloon Ride - enjoy breathtaking views from above.', 60, '2023-10-15 08:15:00', 'Hot Air Balloon Ride', 149.99),
    (17, '2023-10-15 08:16:00', 'Escape to a tropical paradise with our Beach Getaway - sun, sand, and relaxation.', 5, '2023-10-15 08:16:00', 'Beach Getaway', 199.99),
    (18, '2023-10-15 08:17:00', 'Unleash your inner chef with our Cooking Class - hands-on culinary experience.', 75, '2023-10-15 08:17:00', 'Cooking Class', 59.99),
    (19, '2023-10-15 08:18:00', 'Experience the thrill of the racetrack with our Exotic Car Racing - drive high-performance cars.', 30, '2023-10-15 08:18:00', 'Exotic Car Racing', 199.99),
    (20, '2023-10-15 08:19:00', 'Embark on a wine lovers journey with our Vineyard Tour and Wine Tasting.', 60, '2023-10-15 08:19:00', 'Vineyard Tour and Wine Tasting', 79.99),
    (21, '2023-10-15 08:20:00', 'Pamper yourself with a Spa Day - massages, facials, and relaxation in a luxurious spa.', 45, '2023-10-15 08:20:00', 'Spa Day', 99.99),
    (22, '2023-10-15 08:21:00', 'Journey to the stars with our Stargazing Experience - explore the night sky with telescopes.', 90, '2023-10-15 08:21:00', 'Stargazing Experience', 49.99),
    (23, '2023-10-15 08:22:00', 'Treat your senses to a Chocolate Tasting Adventure - indulge in premium chocolates.', 30, '2023-10-15 08:22:00', 'Chocolate Tasting Adventure', 59.99),
    (24, '2023-10-15 08:23:00', 'Unwind and rejuvenate with our Yoga Retreat - find inner peace in serene surroundings.', 60, '2023-10-15 08:23:00', 'Yoga Retreat', 69.99),
    (25, '2023-10-15 08:24:00', 'Experience the thrill of Formula 1 with our Grand Prix VIP Access - exclusive race experience.', 2, '2023-10-15 08:24:00', 'Grand Prix VIP Access', 499.99),
    (26, '2023-10-15 08:25:00', 'Savor the finest steaks with our Steakhouse Dining Experience - premium cuts and flavors.', 90, '2023-10-15 08:25:00', 'Steakhouse Dining Experience', 129.99),
    (27, '2023-10-15 08:26:00', 'Embark on a cultural journey with our Museum Pass - explore art, history, and more.', 30, '2023-10-15 08:26:00', 'Museum Pass', 29.99),
    (28, '2023-10-15 08:27:00', 'Get your adrenaline pumping with our Zip Line Adventure - soar through the treetops.', 15, '2023-10-15 08:27:00', 'Zip Line Adventure', 79.99),
    (29, '2023-10-15 08:28:00', 'Enjoy a day of horseback riding with our Equestrian Adventure - scenic trails and horse bonding.', 60, '2023-10-15 08:28:00', 'Equestrian Adventure', 89.99),
    (30, '2023-10-15 08:29:00', 'Relax and unwind in a private cabin with our Mountain Retreat - nature at its best.', 7, '2023-10-15 08:29:00', 'Mountain Retreat', 249.99),
    (31, '2023-10-15 08:30:00', 'Experience the thrill of a Helicopter Tour - breathtaking aerial views of your favorite city.', 30, '2023-10-15 08:30:00', 'Helicopter Tour', 199.99),
    (32, '2023-10-15 08:31:00', 'Sail the open seas with our Yacht Adventure - luxury yacht, gourmet dining, and more.', 2, '2023-10-15 08:31:00', 'Yacht Adventure', 999.99),
    (33, '2023-10-15 08:32:00', 'Embark on a cultural exploration with our City Museum Pass - access to top museums.', 7, '2023-10-15 08:32:00', 'City Museum Pass', 49.99),
    (34, '2023-10-15 08:33:00', 'Discover your inner artist with our Pottery Workshop - create unique ceramics.', 45, '2023-10-15 08:33:00', 'Pottery Workshop', 79.99),
    (35, '2023-10-15 08:34:00', 'Experience the thrill of Indoor Skydiving - feel the rush in a safe and controlled environment.', 15, '2023-10-15 08:34:00', 'Indoor Skydiving', 49.99),
    (36, '2023-10-15 08:35:00', 'Treat yourself to a Wine and Cheese Tasting - a delightful pairing of flavors.', 60, '2023-10-15 08:35:00', 'Wine and Cheese Tasting', 69.99),
    (37, '2023-10-15 08:36:00', 'Get up close and personal with wildlife on our Safari Adventure - safari tour and wildlife encounters.', 3, '2023-10-15 08:36:00', 'Safari Adventure', 349.99),
    (38, '2023-10-15 08:37:00', 'Escape to a secluded cabin in the woods with our Forest Retreat - tranquility and nature.', 7, '2023-10-15 08:37:00', 'Forest Retreat', 199.99),
    (39, '2023-10-15 08:38:00', 'Explore the depths with our Submarine Expedition - an underwater journey like no other.', 90, '2023-10-15 08:38:00', 'Submarine Expedition', 299.99),
    (40, '2023-10-15 08:39:00', 'Indulge in a Spa and Wine Weekend - relaxation and wine tasting in a beautiful setting.', 2, '2023-10-15 08:39:00', 'Spa and Wine Weekend', 249.99),
    (41, '2023-10-15 08:40:00', 'Experience the magic of a Broadway Show - front-row seats to a world-class performance.', 3, '2023-10-15 08:40:00', 'Broadway Show', 199.99),
    (42, '2023-10-15 08:41:00', 'Unleash your inner detective with our Escape Room Challenge - solve puzzles and mysteries.', 2, '2023-10-15 08:41:00', 'Escape Room Challenge', 69.99),
    (43, '2023-10-15 08:42:00', 'Treat your taste buds to a Gourmet Cooking Class - master the art of fine cuisine.', 60, '2023-10-15 08:42:00', 'Gourmet Cooking Class', 89.99),
    (44, '2023-10-15 08:43:00', 'Discover the wonders of the night sky with our Astronomy Night - telescope observation and star stories.', 3, '2023-10-15 08:43:00', 'Astronomy Night', 59.99),
    (45, '2023-10-15 08:44:00', 'Experience the thrill of Whitewater Rafting - navigate exciting rapids in stunning locations.', 1, '2023-10-15 08:44:00', 'Whitewater Rafting', 129.99),
    (46, '2023-10-15 08:45:00', 'Get lost in the world of books with our Bookstore Shopping Spree - find your next favorite read.', 4, '2023-10-15 08:45:00', 'Bookstore Shopping Spree', 79.99),
    (47, '2023-10-15 08:46:00', 'Embark on a Historical Walking Tour - explore the rich history of your city with an expert guide.', 4, '2023-10-15 08:46:00', 'Historical Walking Tour', 39.99),
    (48, '2023-10-15 08:47:00', 'Relax and rejuvenate with a Couples Spa Retreat - massages, facials, and quality time together.', 2, '2023-10-15 08:47:00', 'Couples Spa Retreat', 299.99),
    (49, '2023-10-15 08:48:00', 'Experience the thrill of Rock Climbing - conquer challenging cliffs with expert instructors.', 1, '2023-10-15 08:48:00', 'Rock Climbing Adventure', 89.99),
    (50, '2023-10-15 08:49:00', 'Discover the art of Mixology with our Cocktail Making Class - craft your favorite drinks.', 3, '2023-10-15 08:49:00', 'Cocktail Making Class', 69.99),
    (51, '2023-10-15 08:50:00', 'Enjoy the serenity of a Lakeside Cabin Getaway - fishing, boating, and relaxation.', 5, '2023-10-15 08:50:00', 'Lakeside Cabin Getaway', 249.99),
    (52, '2023-10-15 08:51:00', 'Embark on a Culinary Tour - savor local flavors and dishes on a guided food adventure.', 7, '2023-10-15 08:51:00', 'Culinary Tour', 79.99),
    (53, '2023-10-15 08:52:00', 'Explore the wonders of the ocean with a Snorkeling Expedition - colorful coral reefs and marine life.', 2, '2023-10-15 08:52:00', 'Snorkeling Expedition', 59.99),
    (54, '2023-10-15 08:53:00', 'Savor the flavors of a Wine and Chocolate Pairing - a delightful combination of two indulgences.', 2, '2023-10-15 08:53:00', 'Wine and Chocolate Pairing', 49.99),
    (55, '2023-10-15 08:54:00', 'Experience the thrill of Horse Racing - VIP access to the racetrack and live horse racing.', 1, '2023-10-15 08:54:00', 'Horse Racing VIP', 199.99),
    (56, '2023-10-15 08:55:00', 'Unwind with a Garden Spa Retreat - surrounded by lush greenery and serenity.', 4, '2023-10-15 08:55:00', 'Garden Spa Retreat', 149.99),
    (57, '2023-10-15 08:56:00', 'Discover the art of Archery - learn precision and focus with expert archery instructors.', 2, '2023-10-15 08:56:00', 'Archery Lessons', 59.99),
    (58, '2023-10-15 08:57:00', 'Experience the thrill of a Helicopter Ski Trip - heli-skiing in breathtaking mountain terrain.', 3, '2023-10-15 08:57:00', 'Helicopter Ski Trip', 499.99),
    (59, '2023-10-15 08:58:00', 'Treat yourself to a Wine and Jazz Evening - live jazz music and fine wine in an intimate setting.', 4, '2023-10-15 08:58:00', 'Wine and Jazz Evening', 79.99),
    (60, '2023-10-15 08:59:00', 'Embark on a Cultural Exchange Program - immerse yourself in a different culture and community.', 30, '2023-10-15 08:59:00', 'Cultural Exchange Program', 499.99);


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