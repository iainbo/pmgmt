-- This file us used to load seed data into the database using SQL statements on Application build

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('admin', 'password', 'Admin', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('test', 'password', 'Admin', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'ADMIN');

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'USER');

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'ADMIN'), (SELECT ID FROM USERS WHERE USER_NAME = 'admin'));

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'USER'), (SELECT ID FROM USERS WHERE USER_NAME = 'test'));

