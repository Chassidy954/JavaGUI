package model;

/**
 * Model representing a single student record.
 */
public class Student {
    
    private final int studentId;
    private final String firstName;
    private final String lastName;
    
    // hold the calculated average for the current section
    private double calculatedAverage; // added for easier grade display

    public Student(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.calculatedAverage = 0.0; // Default to 0.0
    }

    public int getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; } 

    public String getDisplayAverage() {
        return String.format("%.2f%%", calculatedAverage);
    }

    public void setCalculatedAverage(double calculatedAverage) {
        this.calculatedAverage = calculatedAverage;
    }
}