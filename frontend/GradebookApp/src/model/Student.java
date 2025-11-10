package model;

/**
 * Model representing a single student record. This is an immutable DTO 
 * used by the JavaFX presentation layer, should mirror key fields from the 
 * backend 'students' entity.
 */
public class Student {
    
    private final int studentId;
    private final String firstName;
    private final String lastName;
    

    // Constructor to create the immutable object
    public Student(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters
    // Note: The variable names (e.g., 'studentId') must exactly match the name 
    // used in the PropertyValueFactory.
    public int getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    
    /**
     * Custom getter used if we need the full name in one place (e.g., a header label).
     */
    public String getFullName() { 
        return firstName + " " + lastName; 
    }
}