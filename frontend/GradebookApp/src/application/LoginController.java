package application;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Teacher; 
import service.AuthService; 

//This file still being worked on..

/**
 * Controller for the Login view (Login.fxml).
 * Handles user input and delegates authentication logic to the AuthService.
 */
public class LoginController {

    // FXML injected UI components
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;
    
    // Service Instance: Dependency Injection (manually for now.)
    private AuthService authService = new AuthService();

    /**
     * Handles the action when the 'Sign In' button is clicked.
     */
    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        // 1. Get user input
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Reset error message visibility
        errorMessageLabel.setVisible(false);

        // 2. Delegate Authentication to the Service Layer
        Optional<Teacher> teacherResult = authService.login(username, password);

        if (teacherResult.isPresent()) {
            // Authentication Success
            Teacher authenticatedTeacher = teacherResult.get();
            
            System.out.println("Login successful. Teacher details: " + authenticatedTeacher); 
            
            // Proceed to the next scene
            loadDashboard(); 
        } else {
            // Authentication Failure
            displayError("Invalid username or password. Please try again.");
        }
    }
    
    /**
     * Placeholder action for the Forgot Password link.
     */
    @FXML
    public void handleForgotPassword(ActionEvent event) {
        System.out.println("Forgot Password clicked! Implement navigation to recovery page.");
        // This is optional and may not be implemented, can be easily removed if we don't need it.
    }

    /**
     * Displays an error message on the UI.
     * @param message The message to display.
     */
    private void displayError(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setVisible(true);
        passwordField.clear(); // Clear password field for security
    }
    
    /**
     * Loads the main application dashboard scene upon successful login.
     * Note: This is a placeholder and doesn't pass the Teacher model yet.
     */
    private void loadDashboard() {
        try {
            // Just began work on the teacher dashboard for login!! that will be placed here eventually
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); 
            Parent dashboardRoot = loader.load();
            
            // Get the current stage from any element (e.g., the username field's scene)
            Stage stage = (Stage) usernameField.getScene().getWindow();
            
            // Create and set the new scene
            Scene scene = new Scene(dashboardRoot, 800, 600);
            
            // Re-apply the application-wide stylesheet to the new scene
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("Gradebook - Teacher Dashboard"); // Update window title
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback error display
            displayError("Error loading the next screen.");
        }
    }
}
