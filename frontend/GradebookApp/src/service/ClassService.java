package service;

import model.Student;
import model.Subject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service to handle business logic related to classes, students, and subjects.
 * Currently returns simulated data.
 */
public class ClassService {

    // Simulates fetching all classes taught by a teacher
    public List<Subject> getClassesForTeacher(String teacherId) {
        // Hard-coded list of classes for demonstration
        return Arrays.asList(
            new Subject("1101", "Algebra I"),
            new Subject("1102", "CSCI 1102"),
            new Subject("1103", "Hospitality and Hospitals")
        );
    }
    
    // Simulates fetching the roster for a selected class
    public List<Student> getRosterForClass(String classId) {
        // Hard-coded list of students for demonstration
        return Arrays.asList(
            new Student(2001, "Alice", "Smith"),
            new Student(2002, "Bob", "Johnson"),
            new Student(2003, "Charlie", "Brown"),
            new Student(2004, "Dana", "White")
        );
    }
    
    // Simulates fetching the current subject name
    public String getSubjectName(String classId) {
        if ("C001".equals(classId)) return "Algebra I";
        if ("C002".equals(classId)) return "Geometry";
        return "Unknown Subject";
    }
    
    // Simulates fetching attendance data (TBD)
    public double getAttendanceRate(int studentId) {
        // For now, return a random simulated value
        return 92.5; 
    }
}