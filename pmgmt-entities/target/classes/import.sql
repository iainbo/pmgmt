-- This file us used to load seed data into the database using SQL statements on Application build

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'Administrator', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('test', 'XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=', 'Admin', 'User', 'test@email.com', '123456789', sysdate());

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'ADMIN');

INSERT INTO ROLES(DATE_CREATED, ROLE) VALUES (sysdate(), 'USER');

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'ADMIN'), (SELECT ID FROM USERS WHERE USER_NAME = 'admin'));

INSERT INTO USER_ROLES(ROLE_ID, USER_ID) VALUES ((SELECT ID FROM ROLES WHERE ROLE = 'USER'), (SELECT ID FROM USERS WHERE USER_NAME = 'test'));

INSERT INTO GALLERIES(date_created, name, user_id, thumbnail) VALUES (sysdate(), 'Test Gallery 1', (SELECT ID FROM USERS WHERE USER_NAME = 'ADMIN'), LOAD_FILE('/Users/iainb/Public/thumbnail_example.jpg'));

INSERT INTO GALLERIES(date_created, name, user_id) VALUES (sysdate(), 'Test Gallery 2', (SELECT ID FROM USERS WHERE USER_NAME = 'ADMIN'));

INSERT INTO IMAGES (TITLE, GALLERY_ID, DESCRIPTION) VALUES ('TEST IMAGE', (SELECT ID FROM GALLERIES WHERE NAME = 'Test Gallery 1'), 'This is a test image');

INSERT INTO REVISIONS(REVISION_NUMBER, HEAD_REVISION, USER_ID, DATE_UPLOADED, IMAGE_ID) VALUES ('01','Y', (SELECT ID FROM USERS WHERE USER_NAME = 'admin'), sysdate(), (SELECT ID FROM IMAGES WHERE TITLE = 'TEST IMAGE'));

INSERT INTO FILES(REVISION_ID, FILENAME, FILEDATA) VALUES((SELECT R.ID FROM IMAGES I, REVISIONS R WHERE I.ID = R.IMAGE_ID AND I.TITLE = 'TEST IMAGE'), 'example_image.jpg', LOAD_FILE('/Users/iainb/Public/example_image.jpg'));

INSERT INTO IMAGES (TITLE, GALLERY_ID) VALUES ('TEST IMAGE 2', (SELECT ID FROM GALLERIES WHERE NAME = 'Test Gallery 1'));

INSERT INTO REVISIONS(REVISION_NUMBER, HEAD_REVISION, USER_ID, DATE_UPLOADED, IMAGE_ID) VALUES ('01', 'Y', (SELECT ID FROM USERS WHERE USER_NAME = 'admin'), sysdate(), (SELECT ID FROM IMAGES WHERE TITLE = 'TEST IMAGE 2'));

INSERT INTO FILES(REVISION_ID, FILENAME, FILEDATA) VALUES((SELECT R.ID FROM IMAGES I, REVISIONS R WHERE I.ID = R.IMAGE_ID AND I.TITLE = 'TEST IMAGE 2'), 'example_image2.jpeg', LOAD_FILE('/Users/iainb/Public/example_image2.jpeg'));
