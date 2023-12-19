delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$9e6GQpTExSE8ToGplI8kvuMYMjw9hr204NZO4vQTk8B.b8/W63H42', 'admin'),
(2, true, '$2a$08$9e6GQpTExSE8ToGplI8kvuMYMjw9hr204NZO4vQTk8B.b8/W63H42', 'mike');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');