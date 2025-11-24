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

import model.Student;
import model.Teacher;
import service.AuthService; 

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessageLabel;

    private AuthService authService = new AuthService();

    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        errorMessageLabel.setVisible(false);

        Optional<Teacher> teacherResult = authService.loginTeacher(username, password);

        if (teacherResult.isPresent()) {
            System.out.println("Teacher Login Success: " + teacherResult.get().getFirstName());
            loadTeacherDashboard(teacherResult.get());
            return; 
        }

        Optional<Student> studentResult = authService.loginStudent(username, password);

        if (studentResult.isPresent()) {
            System.out.println("Student Login Success: " + studentResult.get().getFirstName());
            loadStudentDashboard(studentResult.get());
            return; // Stop here
        }

        displayError("Invalid username or password.");
    }

    private void loadTeacherDashboard(Teacher teacher) { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml")); 
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setTeacherData(teacher);

            switchScene(root, "EduTracker - Teacher Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            displayError("Error loading Teacher Dashboard.");
        }
    }

    private void loadStudentDashboard(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StudentDashboard.fxml")); 
            Parent root = loader.load();

            StudentDashboardController controller = loader.getController();
            controller.setStudent(student);

            switchScene(root, "EduTracker - Student Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading StudentDashboard.fxml. Check file name/path.");
            displayError("Error loading Student Dashboard.");
        }
    }

    //the CSS kept leaving for some reason. so this reapplies it on scene change
    private void switchScene(Parent root, String title) {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(root);

        // Apply CSS
        String css = getClass().getResource("/application/application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.setTitle(title); 
        stage.show();
    }

    private void displayError(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setVisible(true);
        passwordField.clear(); 
    }
}