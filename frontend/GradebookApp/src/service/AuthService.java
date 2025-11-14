package service;

import model.Teacher;
import java.util.Optional;

/**
 * Service class responsible for handling all authentication business logic.
 * This acts as the boundary between the controller and the data source (simulated database).
 */
public class AuthService {

    /**
     * Attempts to log in a user with the provided credentials.
 
     * @param username The username (e.g., employee ID or email).
     * @param password The raw password input.
     * @return An Optional containing the Teacher object if authentication is successful, 
     * or an empty Optional if credentials are invalid.
     */
    public Optional<Teacher> login(String username, String password) {

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

    }
}
//The simulation logic will be replaced with connection logic once the stuff is setup for that.
