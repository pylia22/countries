INSERT INTO country
VALUES (1, 'Germany'),
       (2, 'Spain'),
       (3, 'France'),
       (4, 'UK');

INSERT INTO city
VALUES (1, 'Berlin',  1),
       (2, 'Frankfurt',  1),
       (3, 'Dortmund',  1),
       (4, 'Madrid',  2),
       (5, 'Bilbao',  2),
       (6, 'Barcelona',  2),
       (7, 'Paris',  3),
       (8, 'London',  4),
       (9, 'Manchester',  4),
       (10, 'Liverpool',  4);

INSERT INTO users
VALUES (1, 'admin', '$2a$10$Ll/I47FxR00F61HXVRfU4eECPTH7k6IbTA/3h4pBevkAj3vLRwrMG', 'ROLE_EDITOR, ROLE_USER'),
       (2, 'user', '$2a$10$nx3Gu3e22GEZEpoYFz7AfeubGjNXiett7MFsfS2o979oy7za54u9S', 'ROLE_USER');

INSERT INTO logo
VALUES (1, 'berlin_logo', null),
       (2, 'francfurt_logo', null),
       (3, 'dortmund_logo', null),
       (4, 'madrid_logo', null),
       (5, 'bilbao_logo', null),
       (6, 'barcelona_logo', null),
       (7, 'paris_logo', null),
       (8, 'london_logo', null),
       (9, 'manchester_logo', null),
       (10, 'liverpool_logo', null);