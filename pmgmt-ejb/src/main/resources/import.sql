-- This file us used to load seed data into the database using SQL statements on Application build
insert into member(id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212');

INSERT INTO USERS(user_name, password, first_name, surname, email, phone_number, date_created) VALUES('test_user', 'password', 'Test', 'User', 'test@email.com', '123456789', sysdate());