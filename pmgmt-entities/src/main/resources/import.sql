-- This file us used to load seed data into the database using SQL statements on Application build

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'Administrator', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('test', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 'Admin', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'ADMIN');

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'USER');

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'ADMIN'), (SELECT ID FROM USERS WHERE USER_NAME = 'admin'));

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'USER'), (SELECT ID FROM USERS WHERE USER_NAME = 'test'));

INSERT INTO GALLERY(date_created, name, user_id) values (sysdate(), 'Test Gallery', (SELECT ID FROM USERS WHERE USER_NAME = 'ADMIN'));

INSERT INTO GALLERY_METADATA(name, value, gallery_id) values ('Description', 'Test Gallery for testing', (SELECT ID FROM GALLERY WHERE NAME = 'Test Gallery'));
