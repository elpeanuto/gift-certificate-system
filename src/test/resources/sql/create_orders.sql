INSERT INTO orders(id, user_id, price, create_date)
VALUES (1, 1, 10, '2020-10-20T07:20:15.156'),
       (2, 2, 30, '2019-10-20T07:20:15.156');

INSERT INTO order_gift_certificate(order_id, gift_certificate_id)
VALUES (1, 3),
       (2, 2);