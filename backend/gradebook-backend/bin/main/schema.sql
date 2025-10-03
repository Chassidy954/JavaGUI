-- @Author(s): Jon Wich
-- @Created: 10/3/2025
-- @Brief: Table data for the database. Spring Boot will automatically run this file.

-- @TODO:
-- * Table for attendance tracking
-- * Table for grade weighting
-- * Table for user login stuff

-- Clean run each time the app is run
DROP TABLE IF EXISTS Grades CASCADE;
DROP TABLE IF EXISTS Assignments CASCADE;
DROP TABLE IF EXISTS StudentEnrollments CASCADE;
DROP TABLE IF EXISTS TeacherEnrollments CASCADE;
DROP TABLE IF EXISTS Sections CASCADE;
DROP TABLE IF EXISTS Courses CASCADE;
DROP TABLE IF EXISTS Students CASCADE;
DROP TABLE IF EXISTS Teachers CASCADE;

CREATE TABLE Courses (
	course_id SERIAL PRIMARY KEY, -- Unique identifier. Will be auto-generated once the API is more developed, should be manually set for now
	course_name VARCHAR(255) -- Display-friendly name
);

CREATE TABLE Sections (
	section_id SERIAL PRIMARY KEY,
	course_id INT REFERENCES Courses(course_id), -- Link between a section and a course
	section_name VARCHAR(255)
);

CREATE TABLE Students (
	student_id SERIAL PRIMARY KEY,
	first_name VARCHAR(255),
	last_name VARCHAR(255)
);

CREATE TABLE Teachers (
	teacher_id SERIAL PRIMARY KEY,
	first_name VARCHAR(255),
	last_name VARCHAR(255)
);

CREATE TABLE Assignments (
	section_id INT,
	assignment_name VARCHAR(255),
	assignment_type VARCHAR(255),
	PRIMARY KEY (section_id, assignment_name) -- Ensures unique assignments, e.g. "History" can have "Homework 1" and be distinct from "Algebra" "Homework 1"
);

CREATE TABLE Grades (
	student_id INT,
	section_id INT,
	assignment_name VARCHAR(100),
	score NUMERIC(5, 2), -- Stores a 3 digit number with 2 extra points of precision, e.g. 93.22
	CONSTRAINT FK_Class_Assignment -- Creates the link between a unique section_id and assignment_name combination to the corresponding assignment
		FOREIGN KEY (section_id, assignment_name) 
		REFERENCES Assignments(section_id, assignment_name)
);

CREATE TABLE StudentEnrollments (
	student_id INT REFERENCES Students(student_id), 
	section_id INT REFERENCES Sections(section_id),
	PRIMARY KEY (student_id, section_id) -- Creates unique student-section pairings. That way Little Timmy can't enroll in Calculus 1 twice.
);

CREATE TABLE TeacherEnrollments (
	teacher_id INT REFERENCES Teachers(teacher_id),
	section_id INT REFERENCES Sections(section_id),
	PRIMARY KEY (teacher_id, section_id)
);
