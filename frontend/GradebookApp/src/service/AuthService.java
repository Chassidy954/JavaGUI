package service;

import model.Teacher;
import java.util.Optional;

/**
 * Service class responsible for handling all authentication business logic.
 */
public class AuthService {

    public Optional<Teacher> login(String username, String password) {
        
        switch (username) {
            case "teacher":
                if ("pass".equals(password)) {
                    // Teacher 1: T001
                    Teacher teacher1 = new Teacher("T001", "Jane", "Doe"); 
                    return Optional.of(teacher1);
                }
                break;
            
            case "robert":
                if ("smith".equals(password)) {
                    // Teacher 2: T002
                    Teacher teacher2 = new Teacher("T002", "Robert", "Smith");
                    return Optional.of(teacher2);
                }
                break;

        // Define a valid username/password pair for testing
        final String VALID_USERNAME = "teacher";
        final String VALID_PASSWORD = "pass";

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            // Success: Return a fully constructed Teacher model object
            Teacher authenticatedTeacher = new Teacher(
                1, 
                "Jane", 
                "Doe"
            );
            return Optional.of(authenticatedTeacher);
        } else {
            // Failure: Return an empty Optional
            return Optional.empty();
        }

        // Failure: If the username/password combination didn't match any case, return empty
        return Optional.empty();
    }
}