INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (1, 'Steve','Zemanick','Michael','IT',17, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 
INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (2, 'Jim','Zemanick','','Accounting',23, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 
INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (3, 'Peter','Harrison','Q','Accounting',34, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 
INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (4, 'Tom','Hill','','Accounting',54, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 
INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (5, 'Joe','Jones','','IT',56, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 
INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (6, 'Harry','Smith','','IT',34, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 
INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (7, 'Mark','Jones','','IT',65, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 
INSERT INTO PERSON (ID, FIRST_NAME,LAST_NAME,MIDDLE_NAME,OCCUPATION,AGE,DOB, CREATED_ON, UPDATED_ON) VALUES (8, 'Rob','Smith','','IT',31, parsedatetime('17-09-2012', 'dd-MM-yyyy'), now(), now()); 

INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (1, '1 Main Street','Apt 1','Plymouth Meeting','PA', '12345', now(), now()); 
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (1, '23 Market Street','','Wayne','PA', '19087', now(), now());
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (1, '1231 Washington Street','','Malvern','PA', '19027', now(), now());

INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (2, '1 Main Street','Apt 1','Plymouth Meeting','PA', '12345', now(), now()); 
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (2, '23 Market Street','','Wayne','PA', '19087', now(), now());
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (2, '1231 Washington Street','','Malvern','PA', '19027', now(), now());

INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (3, '1 Main Street','Apt 1','Plymouth Meeting','PA', '12345', now(), now()); 
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (3, '23 Market Street','','Wayne','PA', '19087', now(), now());
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (3, '1231 Washington Street','','Malvern','PA', '19027', now(), now());

INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (4, '1 Main Street','Apt 1','Plymouth Meeting','PA', '12345', now(), now()); 
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (4, '23 Market Street','','Wayne','PA', '19087', now(), now());
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (4, '1231 Washington Street','','Malvern','PA', '19027', now(), now());

INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (5, '1 Main Street','Apt 1','Plymouth Meeting','PA', '12345', now(), now()); 
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (5, '23 Market Street','','Wayne','PA', '19087', now(), now());
INSERT INTO ADDRESS (person_id, address_1,address_2,city,state,zip_code, CREATED_ON, UPDATED_ON) VALUES (5, '1231 Washington Street','','Malvern','PA', '19027', now(), now());

