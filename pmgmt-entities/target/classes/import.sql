-- This file us used to load seed data into the database using SQL statements on Application build

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'Administrator', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('test', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 'Admin', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'ADMIN');

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'USER');

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'ADMIN'), (SELECT ID FROM USERS WHERE USER_NAME = 'admin'));

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'USER'), (SELECT ID FROM USERS WHERE USER_NAME = 'test'));

INSERT INTO GALLERIES(date_created, name, user_id, thumbnail) VALUES (sysdate(), 'Test Gallery 1', (SELECT ID FROM USERS WHERE USER_NAME = 'ADMIN'), LOAD_FILE('/Users/iainb/Public/thumbnail_example.jpg'));

INSERT INTO GALLERY_METADATA(name, value, gallery_id) VALUES ('Description', 'Test Gallery 1 for testing', (SELECT ID FROM GALLERIES WHERE NAME = 'Test Gallery 1'));

INSERT INTO GALLERIES(date_created, name, user_id) VALUES (sysdate(), 'Test Gallery 2', (SELECT ID FROM USERS WHERE USER_NAME = 'ADMIN'));

INSERT INTO GALLERY_METADATA(name, value, gallery_id) VALUES ('Description', 'Test Gallery 2 for testing', (SELECT ID FROM GALLERIES WHERE NAME = 'Test Gallery 2'));

INSERT INTO IMAGES (DATE_UPLOADED, FILEDATA, FILENAME, TITLE, GALLERY_ID, USER_ID) VALUES (sysdate(), LOAD_FILE('/Users/iainb/Public/example_image.jpg'), 'example_image.jpg', 'TEST IMAGE', (SELECT ID FROM GALLERIES WHERE NAME = 'Test Gallery 1'), (SELECT ID FROM USERS WHERE USER_NAME = 'admin'));