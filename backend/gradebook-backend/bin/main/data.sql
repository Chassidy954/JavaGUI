-- @Author(s): Jon Wich
-- @Created: 10/3/2025
-- @Brief: Populates table info. Spring Boot will automatically run this file.
-- @NOTE: This is just for testing. Later on we should handle it through the API, especially since this hard-codes the serial values.

-- Course IDs in the form 10XX
INSERT INTO Courses(course_id, course_name)
VALUES
(1001, 'Math'),
(1002, 'CompSci'),
(1003, 'Fine Dining... and Breathing');

-- Section IDs in the form 11XX
INSERT INTO Sections(section_id, course_id, section_name)
VALUES
(1101, 1001, 'Algebra 1'),
(1102, 1002, 'CSCI 1102'),
(1103, 1003, 'Hospitality and Hospitals');

-- Student IDs in the form 20XX
INSERT INTO Students(student_id, first_name, last_name)
VALUES
(2001, 'Montgomery', 'Placeholder'),
(2002, 'Isaac', 'Nerdton'),
(2003, 'JimJam', 'FlimFlam');

-- Teacher IDs in the form of 21XX
INSERT INTO Teachers(teacher_id, first_name, last_name)
VALUES
(2101, 'Marvin', 'McClendontron'),
(2102, 'Robert', 'Smithington');

-- Assignment_type in SCREAMING_SNAKE_CASE
INSERT INTO Assignments(section_id, assignment_name, assignment_type)
VALUES
(1101, 'Homework 1', 'HOMEWORK'), -- Algebra 1
(1101, 'Final', 'FINAL_EXAM'), -- Algebra 1

(1102, 'Homework 1', 'HOMEWORK'), -- CSCI
(1102, 'Identifying .txt files', 'EXAM'), -- CSCI

(1103, 'Forgetting HR', 'CLA'), -- Hospitality
(1103, 'OLA3', 'OLA'); -- Hospitality


INSERT INTO Grades(student_id, section_id, assignment_name, score)
VALUES
(2001, 1101, 'Homework 1', 100.00), -- Montgomery | Algebra 
(2001, 1102, 'Identifying .txt files', 12.34), -- Montgomery | CSCI

(2003, 1103, 'Forgetting HR', 100.00); -- Jimjam | Hospitality

-- Set the relationship in the junction tables
INSERT INTO StudentEnrollments(student_id, section_id)
VALUES
-- Enroll Montgomery in Algebra, CSCI
(2001, 1101),
(2001, 1102),

-- Enroll Isaac in Algebra
(2002, 1101),

-- Enroll JimJam in Hospitality
(2003, 1103);


-- Assign teachers to sections
INSERT INTO TeacherEnrollments(teacher_id, section_id)
VALUES
-- Marvin in Math, Compsci
(2101, 1101),
(2101, 1102),

-- Robert in Hospitality
(2102, 1103);