package model;

public class BAKStudent {
    private final int studentId;
    private final String firstName;
    private final String lastName;

    public BAKStudent(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    
    // Helper method for display in tables/lists
    public String getFullName() { return firstName + " " + lastName; }
}