package service;

import model.Student;
import model.Section; 
import model.Assignment; 
import model.Grade; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service to handle business logic related to classes, students, and sections.
 * Currently returns simulated data, ready for replacement with API calls.
 */
public class ClassService {

    // Simulates fetching all classes taught by a teacher
    public List<Section> getClassesForTeacher(String teacherId) {
        // Corrected to pass primitive int IDs and String name. (Constructor: int, String, int)
        // Note: The third argument (courseId) is needed for the Section model.
        return Arrays.asList(
            new Section(1101, "Algebra I", 1001), 
            new Section(1102, "CSCI 1102", 1002),
            new Section(1103, "Hospitality and Hospitals", 1003)
        );
    }
    
    // Simulates fetching the roster for a selected class
    public List<Student> getRosterForClass(String classId) {
        // Corrected to match Student constructor: (int studentId, String firstName, String lastName)
        return Arrays.asList(
            new Student(2001, "Montgomery", "Placeholder"),
            new Student(2002, "Isaac", "Nerdton"),
            new Student(2003, "JimJam", "FlimFlam"),
            new Student(2004, "Dana", "White")
        );
    }
    
    // Simulates fetching all assignments for a class
    public List<Assignment> getAssignmentsForSection(int sectionId) {
        // Assignment constructor: (sectionId, assignmentName, assignmentType, maxPoints)
        return Arrays.asList(
            new Assignment(sectionId, "Homework 1", "HOMEWORK", 100.0),
            new Assignment(sectionId, "Final Exam", "FINAL_EXAM", 200.0),
            new Assignment(sectionId, "Midterm Exam", "EXAM", 150.0)
        );
    }
    
    // Simulates fetching all grades for a specific section
    public List<Grade> getGradesForSection(int sectionId) {
        List<Grade> grades = new ArrayList<>();
        
        // Grade constructor: (id, studentId, assignmentName, score, maxScore, comments)
        grades.add(new Grade(1, 2001, "Homework 1", 95.0, 100.0, "Great work!"));
        grades.add(new Grade(2, 2001, "Final Exam", 185.0, 200.0, "Excellent performance."));
        grades.add(new Grade(3, 2001, "Midterm Exam", 105.0, 150.0, "Needs improvement."));

        // Simulating grades for Student ID 2002
        grades.add(new Grade(4, 2002, "Homework 1", 80.0, 100.0, "Complete."));
        grades.add(new Grade(5, 2002, "Final Exam", 150.0, 200.0, "Average."));
        
        // Simulating grades for Student ID 2003
        grades.add(new Grade(6, 2003, "Midterm Exam", 145.0, 150.0, "Top score!"));
        
        return grades;
    }
    
    // Simulates fetching attendance rate
    public double getAttendanceRate(int studentId) {
        return 92.5; 
    }
}