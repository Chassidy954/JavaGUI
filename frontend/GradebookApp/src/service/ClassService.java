package service;

import model.Student;
import model.Section; 
import model.Assignment; 
import model.Grade; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors; 

/**
 * Service to handle business logic related to classes, students, and sections.
 * Currently returns simulated data, ready for replacement with API calls.
 */
public class ClassService {

    // Simulates fetching all classes taught by a teacher, specific to the ID
    public List<Section> getClassesForTeacher(String teacherId) {
        
        // Define ALL sections for reference (used by Admin)
        List<Section> allSections = Arrays.asList(
            new Section(1101, "Algebra I", 1001), 
            new Section(1102, "CSCI 1102", 1002),
            new Section(1103, "Hospitality and Hospitals", 1003)
        );

        if ("T001".equals(teacherId)) { // Jane Doe (teacher)
            // Jane gets Algebra and CSCI
            return allSections.stream()
                             .filter(s -> s.getSectionId() == 1101 || s.getSectionId() == 1102)
                             .collect(Collectors.toList());

        } else if ("T002".equals(teacherId)) { // Robert Smith (teacher)
            // Robert gets Hospitality only
            return allSections.stream()
                             .filter(s -> s.getSectionId() == 1103)
                             .collect(Collectors.toList());

        } else if ("T003".equals(teacherId)) { // Mary Admin (admin/HR)
            // The Admin gets ALL sections
            return allSections; 
        } else {
            // Any other ID gets no sections
            return new ArrayList<>(); 
        }
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
        
        // Simulating different grades based on sectionId for realistic results
        if (sectionId == 1101) { // Algebra I: High Scores
            grades.add(new Grade(1, 2001, "Homework 1", 95.0, 100.0));
            grades.add(new Grade(2, 2001, "Final Exam", 185.0, 200.0));
            grades.add(new Grade(3, 2002, "Homework 1", 100.0, 100.0));
            grades.add(new Grade(4, 2003, "Midterm Exam", 145.0, 150.0));
            
        } else if (sectionId == 1102) { // CSCI 1102: Mixed Scores
            grades.add(new Grade(5, 2001, "Homework 1", 60.0, 100.0));
            grades.add(new Grade(6, 2002, "Final Exam", 125.0, 200.0));
            grades.add(new Grade(7, 2003, "Homework 1", 85.0, 100.0));
            grades.add(new Grade(8, 2004, "Midterm Exam", 90.0, 150.0));

        } else if (sectionId == 1103) { // Hospitality: Low Scores
            grades.add(new Grade(9, 2001, "Homework 1", 50.0, 100.0));
            grades.add(new Grade(10, 2004, "Final Exam", 100.0, 200.0));
            
        }
        
        return grades;
    }
    
    /**
     * Calculates the unweighted average score for a student based on their grades.
     * Defaults to 0.0 if no grades are found.
     */
    public double calculateUnweightedAverage(List<Grade> studentGrades) {
        if (studentGrades == null || studentGrades.isEmpty()) {
            return 0.0;
        }

        double totalPointsEarned = 0;
        double totalMaxPoints = 0;

        for (Grade grade : studentGrades) {
            totalPointsEarned += grade.getScore();
            totalMaxPoints += grade.getMaxScore();
        }

        if (totalMaxPoints <= 0) {
            return 0.0;
        }

        return (totalPointsEarned / totalMaxPoints) * 100.0;
    }
    
    // Simulates fetching attendance rate
    public double getAttendanceRate(int studentId) {
        return 92.5; 
    }
}