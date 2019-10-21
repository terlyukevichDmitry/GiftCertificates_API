Insert into tag values(1, 'first');
Insert into tag values(2, 'second');
Insert into tag values(3, 'third');

Insert into giftcertificates values(1, 'firstGift', 'first description', 5,
'2019-04-14T21:07:36Z', '2019-04-14T21:07:36Z', 5);
Insert into giftcertificates values(2, 'secondGift', 'second description', 6,
'2019-04-14T21:07:36Z', '2020-04-14T21:07:36Z', 6);
Insert into giftcertificates values(3, 'thirdGift', 'third description', 7,
'2011-04-14T21:07:36Z', '2022-04-14T21:07:36Z', 7);

Insert into tag_giftcertificates values(1, 1);
Insert into tag_giftcertificates values(2, 2);
Insert into tag_giftcertificates values(3, 3);

Insert into user values(1, 'firstLogin', '$2a$10$Zl/tRA2pePM64Pb2ese0iu1Dut9Mz1gDNLMsM6xAq3A6aU2gCVhO6', 'ADMIN');
Insert into user values(2, 'secondLogin', '$2a$10$liH1qT8DjQlEz9/PpMWET.dG341ui8X/JlcFLWQ3y2QKfr4MEj3aC', 'USER');
Insert into user values(3, 'thirdLogin', '$2a$10$6a.9osqnVKD3CqTMUkrN9uutfqiH7vPVphsmRjrUW/ZzclPIZ1C7y', 'ADMIN');

Insert into user_giftcertificates values('2019-08-23T09:42:36Z', 5, 1, 1);
Insert into user_giftcertificates values('2019-08-23T09:42:36Z', 6, 2, 2);
Insert into user_giftcertificates values('2019-08-23T09:42:36Z', 7, 3, 3);
