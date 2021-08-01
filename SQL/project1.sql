--DDL

DROP TABLE IF EXISTS ers_reimbursement;
DROP TABLE IF EXISTS ers_reimbursement_status;
DROP TABLE IF EXISTS ers_reimbursement_type;
DROP TABLE IF EXISTS ers_users;
DROP TABLE IF EXISTS ers_user_roles;


CREATE TABLE ers_reimbursement_status (
	reimb_status_id NUMERIC(2) NOT NULL UNIQUE,
	reimb_status VARCHAR(10) NOT NULL,
	PRIMARY KEY(reimb_status_id)
);


CREATE TABLE ers_reimbursement_type (
	reimb_type_id NUMERIC(2) NOT NULL UNIQUE,
	reimb_type VARCHAR(10) NOT NULL,
	PRIMARY KEY(reimb_type_id)
);


CREATE TABLE ers_user_roles (
	ers_user_role_id NUMERIC(2) NOT NULL UNIQUE,
	user_role VARCHAR(10) NOT NULL,
	PRIMARY KEY (ers_user_role_id)
);


CREATE TABLE ers_users (
	ers_users_id NUMERIC(9) NOT NULL UNIQUE,
	ers_username VARCHAR(50) NOT NULL UNIQUE,
	ers_password VARCHAR(50) NOT NULL,
	user_first_name VARCHAR(100) NOT NULL,
	user_last_name VARCHAR(100) NOT NULL,
	user_email VARCHAR(150) NOT NULL UNIQUE,
	user_role_id NUMERIC(2) NOT NULL,
	PRIMARY KEY (ers_users_id),
	FOREIGN KEY (user_role_id) REFERENCES ers_user_roles(ers_user_role_id)
);


CREATE TABLE ers_reimbursement (
	reimb_id SERIAL NOT NULL UNIQUE,
	reimb_amount DECIMAL(15,2) NOT NULL,
	reimb_submitted TIMESTAMP NOT NULL,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt VARCHAR(250),
	reimb_author NUMERIC(9) NOT NULL,
	reimb_resolver NUMERIC(9),
	reimb_status_id NUMERIC(2) NOT NULL,
	reimb_type_id NUMERIC(2) NOT NULL,
	PRIMARY KEY (reimb_id),
	FOREIGN KEY (reimb_author) REFERENCES ers_users(ers_users_id),
	FOREIGN KEY (reimb_resolver) REFERENCES ers_users(ers_users_id),
	FOREIGN KEY (reimb_status_id) REFERENCES ers_reimbursement_status(reimb_status_id),
	FOREIGN KEY (reimb_type_id) REFERENCES ers_reimbursement_type(reimb_type_id)
);


--DML

--reimbursement statuses
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
	VALUES (01, 'PENDING');
	
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
	VALUES (02, 'APPROVED');
	
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
	VALUES (03, 'DENIED');

--reimbursement types
INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
	VALUES (01, 'LODGING');

INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
	VALUES (02, 'TRAVEL');

INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
	VALUES (03, 'FOOD');
	
INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
	VALUES (04, 'OTHER');

--user roles
INSERT INTO ers_user_roles (ers_user_role_id, user_role)
	VALUES (01, 'EMPLOYEE');
	
INSERT INTO ers_user_roles (ers_user_role_id, user_role)
	VALUES (02, 'MANAGER');

--example users
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
	VALUES (111222333, 'JohnQPublic', 'password', 'John', 'Public', 'johnqpublic@email.com', 01);

INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
	VALUES (333222111, 'BaxterBanker', 'password', 'Baxter', 'Banker', 'baxterbanker@email.com', 02);

--example reimbursements
INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
	VALUES (400.00, '2021-07-01', '2021-07-07', 'Business Trip Holiday Inn Express',  null, 111222333, 333222111, 02, 01);

INSERT INTO ers_reimbursement ( reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
	VALUES (80.00, '2021-07-01', null, 'Magic Shop',  null, 111222333, null, 01, 04);


--DQL

CREATE OR REPLACE FUNCTION check_type(
	type_id NUMERIC(2)
	)
	RETURNS TABLE(
	reimbursement_type VARCHAR(10)
	)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT reimb_type
FROM ers_reimbursement_type
WHERE type_id = reimb_type_id;
END;$$;




CREATE OR REPLACE FUNCTION check_status(
	status_id NUMERIC(2)
	)
	RETURNS TABLE(
	status VARCHAR(10)
	)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT reimb_status
FROM ers_reimbursement_status
WHERE reimb_status_id = status_id;
END;$$;



--a function that checks login details.
CREATE OR REPLACE FUNCTION check_login_details(
	input_username VARCHAR(50),
	input_password VARCHAR(50)
	)
	RETURNS TABLE(
	username VARCHAR(50),
	user_password VARCHAR(50)
	)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT ers_users.ers_username, ers_users.ers_password
FROM ers_users
WHERE ers_users.ers_username = input_username AND ers_users.ers_password = input_password;
END;$$;

CREATE OR REPLACE FUNCTION get_user(
	input_username VARCHAR(50),
	input_password VARCHAR(50)
	)
	RETURNS TABLE(
	id  NUMERIC(9),
	username VARCHAR(50),
	user_password VARCHAR(50),
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	email VARCHAR(150),
	user_role VARCHAR(10)
	)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT ers_users.ers_users_id, ers_users.ers_username, ers_users.ers_password, ers_users.user_first_name, ers_users.user_last_name, ers_users.user_email, ers_user_roles.user_role
FROM ers_users INNER JOIN ers_user_roles
ON ers_users.user_role_id = ers_user_roles.ers_user_role_id
WHERE ers_users.ers_username = input_username AND ers_users.ers_password = input_password;
END;$$;

--a function that finds a user from an Id
CREATE OR REPLACE FUNCTION find_user(
	user_id NUMERIC(9)
	)
	RETURNS TABLE(
	id  NUMERIC(9),
	username VARCHAR(50),
	user_password VARCHAR(50),
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	email VARCHAR(150),
	user_role VARCHAR(10)
	)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT ers_users.ers_users_id, ers_users.ers_username, ers_users.ers_password, ers_users.user_first_name, ers_users.user_last_name, ers_users.user_email, ers_user_roles.user_role
FROM ers_users INNER JOIN ers_user_roles
ON ers_users.user_role_id = ers_user_roles.ers_user_role_id
WHERE ers_users.ers_users_id = user_id;
END;$$;


--a function that allows employees to view their past reimbursement submissions
DROP FUNCTION IF EXISTS view_past_reimbursements;
CREATE OR REPLACE FUNCTION view_past_reimbursements(
	employee_id int) 
	RETURNS TABLE (
	id INTEGER,
	amount DECIMAL(15,2),
	submitted TIMESTAMP,
	resolved TIMESTAMP,
	description VARCHAR(250),
	receipt VARCHAR(250),
	author NUMERIC(9),
	resolver NUMERIC(9),
	type_id NUMERIC(2),
	status_id NUMERIC(2)
	)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY
	SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_type_id, reimb_status_id 
	FROM ers_reimbursement
	WHERE reimb_author = employee_id;
END;$$;

--a function that allows managers to view all reimbursements
DROP FUNCTION IF EXISTS view_all_reimbursements;
CREATE OR REPLACE FUNCTION view_all_reimbursements()
	RETURNS TABLE (
	reimb_id INTEGER,
	reimb_amount DECIMAL(15,2),
	reimb_submitted TIMESTAMP,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt VARCHAR(250),
	reimb_author NUMERIC(9),
	reimb_resolver NUMERIC(9),
	reimb_type VARCHAR(10),
	reimb_status VARCHAR(10)
	)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY
	SELECT reimbs.reimb_id, reimbs.reimb_amount, reimbs.reimb_submitted, reimbs.reimb_resolved, reimbs.reimb_description, reimbs.reimb_receipt, reimbs.reimb_author, reimbs.reimb_resolver, types.reimb_type, status.reimb_status 
	FROM ers_reimbursement reimbs INNER JOIN ers_reimbursement_type types ON reimbs.reimb_type_id = types.reimb_type_id INNER JOIN ers_reimbursement_status status ON reimbs.reimb_status_id = status.reimb_status_id;
END;$$;

--a function for a manager filtering reimbursements by status
DROP FUNCTION IF EXISTS view_filtered_reimbursements;
CREATE OR REPLACE FUNCTION view_filtered_reimbursements(
	status_id int) 
	RETURNS TABLE (
	reimb_id INTEGER,
	reimb_amount DECIMAL(15,2),
	reimb_submitted TIMESTAMP,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt VARCHAR(250),
	reimb_author NUMERIC(9),
	reimb_resolver NUMERIC(9),
	reimb_type VARCHAR(10),
	reimb_status VARCHAR(10)
	)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY
	SELECT reimbs.reimb_id, reimbs.reimb_amount, reimbs.reimb_submitted, reimbs.reimb_resolved, reimbs.reimb_description, reimbs.reimb_receipt, reimbs.reimb_author, reimbs.reimb_resolver, types.reimb_type, status.reimb_status 
	FROM ers_reimbursement reimbs INNER JOIN ers_reimbursement_type types ON reimbs.reimb_type_id = types.reimb_type_id INNER JOIN ers_reimbursement_status status ON reimbs.reimb_status_id = status.reimb_status_id 
	WHERE reimbs.reimb_status_id = status_id;
END;$$;

--testing the DQL functions
SELECT * FROM view_past_reimbursements(111222333);
SELECT * FROM view_all_reimbursements();
SELECT * FROM view_filtered_reimbursements(02);
SELECT * FROM check_login_details('JohnQPublic', 'password');
SELECT * FROM get_user('JohnQPublic', 'password');
SELECT * FROM find_user(111222333);
SELECT * FROM check_status(1);
SELECT * FROM check_type(1);
--TCL

-- a procedure for an employee creating a reimbursement
CREATE OR REPLACE PROCEDURE create_reimbursement( amount FLOAT, description VARCHAR(250), receipt VARCHAR(250), author INT, type_id NUMERIC(2))
LANGUAGE plpgsql 
AS $$
BEGIN
	INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
		VALUES (amount, NOW(), null, description, receipt, author, null, 01, type_id);
	COMMIT;
END;$$;
 
 -- a procedure that allows a manager to approve a reimbursement 
 CREATE OR REPLACE PROCEDURE approve_reimbursement(
	id INT, resolver INT)
LANGUAGE plpgsql 
AS $$
BEGIN
	UPDATE ers_reimbursement
	SET 
		reimb_status_id = 02,
		reimb_resolved = NOW(),
		reimb_resolver = resolver
	WHERE reimb_id = id;
	COMMIT;
END;$$;
 
 -- a procedure that allows a manager to deny a reimbursement 
 CREATE OR REPLACE PROCEDURE deny_reimbursement(
	id INT, resolver INT)
LANGUAGE plpgsql 
AS $$
BEGIN
	UPDATE ers_reimbursement
	SET 
		reimb_status_id = 03,
		reimb_resolved = NOW(),
		reimb_resolver = resolver
	WHERE reimb_id = id;
	COMMIT;
END;$$;
 
--testing the TCL functions
CALL create_reimbursement(100, 'Red Lobster', null, 111222333, 3);
CALL approve_reimbursement(3, 333222111);
CALL deny_reimbursement(2, 333222111);

