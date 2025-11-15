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

import model.BAKTeacher; 
import model.Teacher;
import service.AuthService; 

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
            
            //Pass the authenticated teacher model to the transition method
            loadDashboard(authenticatedTeacher); 
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
    }

    /**
     * Displays an error message on the UI.
     * @param message The message to display.
     */
    private void displayError(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setVisible(true);
        passwordField.clear(); 
    }
    
    /**
     * Loads the main application dashboard scene upon successful login.
     * @param teacher The authenticated Teacher object to pass to the next controller.
     */
    //Accept the Teacher object as an argument
    private void loadDashboard(Teacher teacher) { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml")); 
            Parent dashboardRoot = loader.load();
            
            // 4. Get the DashboardController instance and set the data
            DashboardController dashboardController = loader.getController();
            dashboardController.setTeacherData(teacher);
            
            // 5. Get the current stage and switch the scene
            Stage stage = (Stage) usernameField.getScene().getWindow();
            
            Scene scene = new Scene(dashboardRoot);
            
            // 6. Re-apply the application-wide stylesheet to the new scene
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("EduTracker - Teacher Dashboard"); 
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            displayError("Critical Error: Could not load the main dashboard screen.");
        }
    }
}