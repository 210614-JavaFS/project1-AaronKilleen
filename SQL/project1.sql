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
	reimb_id NUMERIC(9) NOT NULL UNIQUE,
	reimb_amount DECIMAL(15,2) NOT NULL,
	reimb_submitted TIMESTAMP NOT NULL,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt BYTEA,
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
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
	VALUES (222333444, 400.00, '2021-07-01', '2021-07-07', 'Business Trip Holiday Inn Express',  null, 111222333, 333222111, 02, 01);

INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
	VALUES (333444555, 80.00, '2021-07-01', null, 'Magic Shop',  null, 111222333, null, 01, 04);


--DQL

--a function that allows employees to view their past reimbursement submissions
CREATE OR REPLACE FUNCTION view_past_reimbursements(
	employee_id int) 
	RETURNS TABLE (
	reimb_id NUMERIC(9),
	reimb_amount DECIMAL(15,2),
	reimb_submitted TIMESTAMP,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt BYTEA,
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
	WHERE reimbs.reimb_author = employee_id;
END;$$;

--a function that allows managers to view all reimbursements
CREATE OR REPLACE FUNCTION view_all_reimbursements()
	RETURNS TABLE (
	reimb_id NUMERIC(9),
	reimb_amount DECIMAL(15,2),
	reimb_submitted TIMESTAMP,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt BYTEA,
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
CREATE OR REPLACE FUNCTION view_filtered_reimbursements(
	status_id int) 
	RETURNS TABLE (
	reimb_id NUMERIC(9),
	reimb_amount DECIMAL(15,2),
	reimb_submitted TIMESTAMP,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt BYTEA,
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


--TCL

-- a procedure for an employee creating a reimbursement
CREATE OR REPLACE PROCEDURE create_reimbursement(
	id INT, amount FLOAT, submitted TIMESTAMP, description VARCHAR(250), receipt BYTEA, author INT, reimb_type INT)
LANGUAGE plpgsql 
AS $$
BEGIN
	INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
		VALUES (id, amount, submitted, null, description, receipt, author, null, 01, reimb_type);
	COMMIT;
END;$$;
 
 -- a procedure that allows a manager to approve a reimbursement 
 CREATE OR REPLACE PROCEDURE approve_reimbursement(
	id INT, resolved TIMESTAMP, resolver INT)
LANGUAGE plpgsql 
AS $$
BEGIN
	UPDATE ers_reimbursement
	SET 
		reimb_status_id = 02,
		reimb_resolved = resolved,
		reimb_resolver = resolver
	WHERE reimb_id = id;
	COMMIT;
END;$$;
 
 -- a procedure that allows a manager to deny a reimbursement 
 CREATE OR REPLACE PROCEDURE deny_reimbursement(
	id INT, resolved TIMESTAMP, resolver INT)
LANGUAGE plpgsql 
AS $$
BEGIN
	UPDATE ers_reimbursement
	SET 
		reimb_status_id = 03,
		reimb_resolved = resolved,
		reimb_resolver = resolver
	WHERE reimb_id = id;
	COMMIT;
END;$$;
 
--testing the TCL functions
CALL create_reimbursement(444555666, 100, '2021-07-08', 'Red Lobster', null, 111222333, 3);
CALL approve_reimbursement(444555666, '2021-07-15', 333222111);
CALL deny_reimbursement(333444555, '2021-07-10', 333222111);
