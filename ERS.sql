
/*================================================================================*/
--                   Creating a new ers_reimbursement table                  --
/*================================================================================*/

CREATE TABLE ers_reimbursement(
    reimb_id NUMBER NOT NULL,
    reimb_status_id NUMBER,
    reimb_date TIMESTAMP,
    reimb_firstname VARCHAR2(250),
    reimb_lastname VARCHAR2(250),
    reimb_username NUMBER,
    reimb_city VARCHAR2(250),
    reimb_state VARCHAR2(250),
    reimb_zip NUMBER,
    reimb_amount NUMBER,
    reimb_type_id NUMBER NOT NULL,
    reimb_file BLOB,
    CONSTRAINT ers_reimbursement_pk PRIMARY KEY(reimb_id)
);

-- Current reimbursement statuses: APPROVED, PENDING and DENIED
CREATE TABLE ers_reimbursement_status (
  reimb_status_id NUMBER       NOT NULL,
  reimb_status    VARCHAR2(10) NOT NULL,
  CONSTRAINT reimb_status_pk PRIMARY KEY (reimb_status_id)
);

-- Current reimbursement types: LODGING, TRAVEL, FOOD or OTHER
CREATE TABLE ers_reimbursement_type (
  reimb_type_id NUMBER       NOT NULL,
  reimb_type    VARCHAR2(10) NOT NULL,
  CONSTRAINT reimb_type_pk PRIMARY KEY (reimb_type_id)
);


--ERS user detail table :for all user identification details
CREATE TABLE ers_users (
  ers_users_id    NUMBER        NOT NULL,
  ers_username    VARCHAR2(50)  NOT NULL UNIQUE,
  ers_password    VARCHAR2(100)  NOT NULL,
  user_first_name VARCHAR2(100) NOT NULL,
  user_last_name  VARCHAR2(100) NOT NULL,
  user_email      VARCHAR2(150) NOT NULL UNIQUE,
  user_role_id    NUMBER        NOT NULL,
  CONSTRAINT ers_users_pk PRIMARY KEY (ers_users_id)
);

--create user roles table
CREATE TABLE ers_user_roles (
  ers_user_role_id NUMBER       NOT NULL,
  user_role        VARCHAR2(20) NOT NULL,
  CONSTRAINT ers_user_roles_pk PRIMARY KEY (ers_user_role_id)
);



/*================================================================================*/
--                   Creating relationships for the tables above                  --
/*================================================================================*/

-- ERS_REIMBURSEMENT table foreign keys


ALTER TABLE ers_reimbursement
  ADD CONSTRAINT ers_reimbursement_status_fk
FOREIGN KEY (reimb_status_id)
REFERENCES ers_reimbursement_status (reimb_status_id);

ALTER TABLE ers_reimbursement
  ADD CONSTRAINT ers_reimbursement_type_fk
FOREIGN KEY (reimb_type_id)
REFERENCES ers_reimbursement_type (reimb_type_id);

-- ERS_USERS table foreign keys
ALTER TABLE ers_users
  ADD CONSTRAINT user_roles_fk
FOREIGN KEY (user_role_id)
REFERENCES ers_user_roles (ers_user_role_id);


--ERS_REIMBURSEMENT_STATUS
INSERT INTO ers_reimbursement_status
VALUES (1, 'Approved');
INSERT INTO ers_reimbursement_status
VALUES (2, 'Pending');
INSERT INTO ers_reimbursement_status
VALUES (3, 'Denied');

Populating ERS_USER_ROLES
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (1, 'Finance Manager');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (2, 'Office Assistant III');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (3, 'Nurse Practicioner');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (4, 'HR Manager');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (5, 'Project Manager');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (6, 'VP Accounting');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (7, 'Senior Editor');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (8, 'Geological Engineer');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (9, 'Associate Professor');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (10, 'Civil Engineer');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (11, 'Web Designer I');


INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (1, 'shital', 'LdTPHY', 'shital', 'moradiya', 'shamilton0@list-manage.com', 10);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (2, 'sierra', 'O6SvXdR7BA5', 'sierra', 'stewart', 'sierra1@wiley.com', 6);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (3, 'sbaker2', 'i1D0iG', 'Sharon', 'Baker', 'sbaker2@mtv.com', 2);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (4, 'jgardner3', 'g95W52', 'Janice', 'Gardner', 'jgardner3@indiegogo.com', 1);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (5, 'kallen4', '0LfdkMqgras', 'Kathryn', 'Allen', 'kallen4@google.es', 1);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (6, 'raj', 'efKw7QZ6', 'Christopher', 'Gutierrez', 'cgutierrez5@cnn.com', 11);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (7, 'heena', '1l9yXrk62aC', 'Elizabeth', 'Riley', 'eriley6@cam.ac.uk', 4);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (8, 'deepak', 'DlUu2H2Am', 'Johnny', 'Wood', 'jwood7@hao123.com', 6);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (9, 'shivay', 'AeasuTmyyfq', 'Jose', 'Diaz', 'jdiaz8@samsung.com', 4);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (10, 'anuja', 'kGemtEMcNwiq', 'Edward', 'Richards', 'erichards9@technorati.com', 9);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (11, 'prerana', 'uqnef9ChDX6Q', 'Kathy', 'Reid', 'kreida@fastcompany.com', 9);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (12, 'disha', '5TNabu1ePURN', 'Judith', 'Carr', 'jcarrb@cafepress.com', 9);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (13, 'subham', 'YmPtNxKfH1e', 'Larry', 'Ortiz', 'lortizc@globo.com', 7);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (14, 'lay', 'ZzHgVW6HM8', 'Thomas', 'Rodriguez', 'trodriguezd@vimeo.com', 7);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (15, 'kashyap', 'Qpy3zJuN', 'Nicholas', 'Murray', 'nmurraye@histats.com', 7);
INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES (16, 'shivani', '79sQvf2b', 'Brandon', 'Porter', 'bporterf@amazon.co.jp', 10);


INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (1, 610.2, TIMESTAMP '2015-12-07 11:22:52', NULL,
        'dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac', NULL, 5, 23, 2, 1);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (2, 1246.05, TIMESTAMP '2016-07-13 03:13:15', NULL, NULL, NULL, 23, 13, 2, 2);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (3, 1297.18, TIMESTAMP '2016-06-18 21:37:49', NULL,
        'sem fusce consequat nulla nisl nunc nisl duis bibendum felis sed interdum venenatis turpis', NULL, 2, 42, 1,
        1);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (4, 1710.83, TIMESTAMP '2016-07-18 01:18:20', NULL,
        'viverra dapibus nulla suscipit ligula in lacus curabitur at ipsum ac tellus semper interdum mauris ullamcorper',
        NULL, 40, 1, 3, 4);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (5, 67.3, TIMESTAMP '2016-09-23 13:56:22', NULL,
        'pellentesque ultrices mattis odio donec vitae nisi nam ultrices libero non mattis pulvinar nulla', NULL, 16,
        18, 1, 1);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (6, 534.64, TIMESTAMP '2016-05-20 14:53:27', NULL, NULL, NULL, 35, 5, 3, 2);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (7, 80.12, TIMESTAMP '2015-12-04 21:34:59', NULL,
        'vitae consectetuer eget rutrum at lorem integer tincidunt ante vel ipsum praesent blandit lacinia erat vestibulum',
        NULL, 18, 40, 3, 2);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES
  (8, 253.56, TIMESTAMP '2016-09-22 18:02:40', NULL, 'quam a odio in hac habitasse platea dictumst maecenas ut', NULL,
   48, 33, 2, 3);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (9, 417.78, TIMESTAMP '2016-02-01 04:25:31', NULL,
        'commodo placerat praesent blandit nam nulla integer pede justo lacinia eget tincidunt eget tempus vel pede morbi porttitor',
        NULL, 20, 49, 3, 4);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES (10, 1925.03, TIMESTAMP '2016-08-17 07:28:28', NULL,
        'mauris non ligula pellentesque ultrices phasellus id sapien in sapien iaculis congue vivamus metus arcu adipiscing molestie hendrerit at',
        NULL, 25, 20, 1, 1);