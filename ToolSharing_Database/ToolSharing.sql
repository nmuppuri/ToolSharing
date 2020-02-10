/*	*****TEAM MEMBERS*****

Narendra Kumar Muppuri
Narender Reddy Gayam
Narasimha Reddy Patel
Arun Kumbapillil Ravi
Sai Ram Pramodhini Reddy Aleti
Usha Lakhani


*/


DROP SCHEMA IF EXISTS ToolSharing ;
CREATE SCHEMA IF NOT EXISTS ToolSharing DEFAULT CHARACTER SET UTF8 ;
USE ToolSharing ;

DROP TABLE IF EXISTS person;
CREATE TABLE IF NOT EXISTS person (
    id				INT PRIMARY KEY NOT NULL,
    passwd			VARCHAR(50) 	NULL,
    first_name		VARCHAR(50) 	NOT NULL,
    last_name		VARCHAR(50) 	NOT NULL,
    email			VARCHAR(50) 	NOT NULL,
    phone			BIGINT,
    address			VARCHAR(255),
    admin_access	VARCHAR(10)
);


DROP TABLE IF EXISTS student;
CREATE TABLE IF NOT EXISTS student (
    student_id INT PRIMARY KEY NOT NULL,
    FOREIGN KEY (student_id)
        REFERENCES person (id)
);


DROP TABLE IF EXISTS admins;
CREATE TABLE IF NOT EXISTS admins (
    admin_id INT PRIMARY KEY NOT NULL,
    FOREIGN KEY (admin_id)
        REFERENCES person (id)
);


DROP TABLE IF EXISTS student_registration;
CREATE TABLE IF NOT EXISTS student_registration (
    student_id INT NOT NULL,
    admin_id INT NULL,
    decision VARCHAR(50) NOT NULL,
    decision_date DATE,
    PRIMARY KEY (student_id),
    FOREIGN KEY (admin_id)
        REFERENCES admins (admin_id),
    FOREIGN KEY (student_id)
        REFERENCES person (id)
);


DROP TABLE IF EXISTS tools;
CREATE TABLE IF NOT EXISTS tools (
    tool_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tool_name VARCHAR(50) NOT NULL,
    tool_desc VARCHAR(255)
);

ALTER TABLE tools AUTO_INCREMENT = 100;


DROP TABLE IF EXISTS order_details;
CREATE TABLE IF NOT EXISTS order_details (
    tool_id INT NOT NULL,
    borrowed_student_id INT NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    return_date DATE,
    rating INT,
    PRIMARY KEY (tool_id , borrowed_student_id),
    FOREIGN KEY (tool_id)
        REFERENCES tools (tool_id),
    FOREIGN KEY (borrowed_student_id)
        REFERENCES student (student_id)
);


DROP TABLE IF EXISTS message;
CREATE TABLE IF NOT EXISTS message (
    message_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    message VARCHAR(1000) NOT NULL,
    sent_date DATE NOT NULL,
    from_student_id INT NOT NULL,
    FOREIGN KEY (from_student_id)
        REFERENCES student (student_id)
);

ALTER TABLE message AUTO_INCREMENT = 100000;


DROP TABLE IF EXISTS message_recepient;
CREATE TABLE IF NOT EXISTS message_recepient (
    message_id INT NOT NULL,
    to_student_id INT NOT NULL,
    PRIMARY KEY (message_id , to_student_id),
    FOREIGN KEY (to_student_id)
        REFERENCES student (student_id),
    FOREIGN KEY (message_id)
        REFERENCES message (message_id)
);


