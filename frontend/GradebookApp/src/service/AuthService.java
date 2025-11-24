package service;

import model.Student;
import model.Teacher;
import java.util.Optional;

public class AuthService {

    // --- TEACHER LOGIN ---
    public Optional<Teacher> loginTeacher(String username, String password) {
        String user = username.trim().toLowerCase();

        if (user.equals("teacher") && password.equals("pass")) {
            return Optional.of(new Teacher(1, "Jane", "Doe"));
        }
        if (user.equals("robert") && password.equals("smith")) {
            return Optional.of(new Teacher(2, "Robert", "Smith"));
        }

        return Optional.empty();
    }

    // --- STUDENT LOGIN ---
    public Optional<Student> loginStudent(String username, String password) {
        String user = username.trim().toLowerCase();

        // Simple password check for all students
        if (!password.equals("pass")) { 
            return Optional.empty(); 
        }

        switch (user) {
            case "montgomery":
                Student s1 = new Student();
                s1.setId(1);
                s1.setFirstName("Montgomery");
                s1.setLastName("Placeholder");
                return Optional.of(s1);

            case "isaac":
                Student s2 = new Student();
                s2.setId(2);
                s2.setFirstName("Isaac");
                s2.setLastName("Nerdton");
                return Optional.of(s2);

            case "jimjam":
                Student s3 = new Student();
                s3.setId(3);
                s3.setFirstName("JimJam");
                s3.setLastName("FlimFlam");
                return Optional.of(s3);

            default:
                return Optional.empty();
        }
    }
}