BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS professors;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS deliverables;
DROP TABLE IF EXISTS user_course;
DROP TABLE IF EXISTS courseGrades;
DROP TABLE IF EXISTS deliverableGrades;

CREATE TABLE users(
	userID integer PRIMARY KEY NOT NULL,
	username text NOT NULL,
	password text NOT NULL
);
	
CREATE TABLE students(
	userID integer PRIMARY KEY NOT NULL,
	firstName text NOT NULL,
	lastName text NOT NULL,
	birthday text NOT NULL,
	FOREIGN KEY (userID) REFERENCES users(userID)
);	

CREATE TABLE professors(
	userID integer PRIMARY KEY NOT NULL,
	firstName text NOT NULL,
	lastName text NOT NULL,
	FOREIGN KEY (userID) REFERENCES users(UID)
);

CREATE TABLE courses(
	courseID integer PRIMARY KEY NOT NULL,
	name text NOT NULL,
	location text NOT NULL,
	section text NOT NULL,
	term text NOT NULL,
	cap integer NOT NULL,
	time text NOT NULL
);

CREATE TABLE deliverables(
	deliverableID integer PRIMARY KEY NOT NULL,
	courseID integer NOT NULL,
	maxGrade integer NOT NULL,
	openDate text NOT NULL,
	deadLine text NOT NULL,
	FOREIGN KEY (courseID) REFERENCES courses(courseID)
);

CREATE TABLE user_course(
	userID integer NOT NULL,
	courseID integer NOT NULL,
	active text NOT NULL,
	PRIMARY KEY (userID, courseID),
	FOREIGN KEY (userID) REFERENCES users(userID),
	FOREIGN KEY (courseID) REFERENCES courses(courseID)
);

CREATE TABLE courseGrades(
	userID integer NOT NULL,
	courseID integer NOT NULL,
	grade integer NOT NULL,
	PRIMARY KEY (userID, courseID),
	FOREIGN KEY (userID) REFERENCES users(userID),
	FOREIGN KEY (courseID) REFERENCES courses(courseID)
);

CREATE TABLE deliverableGrades(
	userID integer NOT NULL,
	deliverableID integer NOT NULL,
	grade integer NOT NULL,
	PRIMARY KEY (userID, deliverableID),
	FOREIGN KEY (userID) REFERENCES users(userID),
	FOREIGN KEY (deliverableID) REFERENCES deliverables(deliverableID)
);

COMMIT;