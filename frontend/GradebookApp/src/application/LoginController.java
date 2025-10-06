package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

/**
 * Controller for the Login.fxml view. Handles user authentication logic.
 * This file connects the FXML elements (like text fields and buttons) 
 * to the Java code.
 */
public class LoginController {

    // FXML element injections (fx:id matching those in Login.fxml)
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorMessageLabel;

    /**
     * Initializes the controller class. Called after the FXML has been loaded.
     */
    public void initialize() {
        // Hide the error message when the application first starts
        errorMessageLabel.setVisible(false);
    }

    /**
     * Handles the action when the Sign In button is clicked.
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // 1. Basic Input Validation
        if (username.isEmpty() || password.isEmpty()) {
            displayError("Username and password are required.");
            return;
        }

        // 2. Placeholder/Simulated Authentication
        // This is where you will integrate your AuthService later. 
        
        if ("teacher".equals(username) && "pass".equals(password)) {
            // Authentication Success
            System.out.println("Login successful for user: " + username);
            loadDashboard(); 
        } else {
            // Authentication Failure
            displayError("Invalid username or password. Please try again.");
        }
    }
    
    /**
     * Placeholder for the forgotten password link action.
     */
    @FXML
    private void handleForgotPassword(ActionEvent event) {
        System.out.println("Forgot Password clicked. This function needs implementation.");
    }

    /**
     * Sets the error message text and makes it visible.
     */
    private void displayError(String message) {
        // Use the error color defined in application.css (E74C3C)
        errorMessageLabel.setText(message);
        errorMessageLabel.setVisible(true);
    }
    
    /**
     * Placeholder method to switch scenes after successful login.
     * TO DO: Implement actual scene switching logic.
     */
    private void loadDashboard() {
        System.out.println("--- Login Successful! Transitioning to Dashboard ---");
    }
}
