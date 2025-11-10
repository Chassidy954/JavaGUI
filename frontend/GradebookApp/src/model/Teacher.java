package model;

public class Teacher {
    private final String teacherId;
    private final String firstName;
    private final String lastName;

    public Teacher(String teacherId, String firstName, String lastName, String email) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters for display
    public String getTeacherId() { return teacherId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
  
   
    @Override
    public String toString() {
        return "Teacher{" +
               "id='" + teacherId + '\'' +
               ", name='" + firstName + " " + lastName + 
               '}';
    }
}