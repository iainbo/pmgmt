-- This file us used to load seed data into the database using SQL statements on Application build

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('test_user', 'password', 'Test', 'User', 'test@email.com', '123456789', sysdate());