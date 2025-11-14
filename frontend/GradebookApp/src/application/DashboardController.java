package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import model.BAKSubject;
import model.BAKStudent; 
import model.BAKTeacher;
import model.Section;
import model.Student;
import model.Teacher;
import service.ClassService; 
import java.util.List;

public class DashboardController {

    // --- FXML Elements ---
    @FXML private BorderPane rootPane; 
    @FXML private Label teacherNameLabel; 
    @FXML private ListView<Section> classListView;
    @FXML private Label contentHeaderLabel; 
    @FXML private VBox contentArea; 
    @FXML private HBox headerBar; 
    
    // Table View for Student Roster
    private TableView<Student> rosterTable = new TableView<>();
    
    private Teacher currentTeacher;
    private ClassService classService = new ClassService();
    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    // Runs after FXML loads
    @FXML
    public void initialize() {
        // --- 1. SET UP ROSTER TABLE STRUCTURE ---
        setupRosterTable();

        // --- 2. ADD LISTENER ---
        classListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.equals(oldValue)) {
                    loadClassDetails(newValue);
                }
            }
        );
    }

    public void setTeacherData(Teacher teacher) {
        this.currentTeacher = teacher;
        if (teacher != null) {
            String fullName = teacher.getFirstName() + " " + teacher.getLastName();
            teacherNameLabel.setText("Welcome, " + fullName);
            loadClassList(teacher.getId());
        }
    }

    private void loadClassList(Integer teacherId) {
        List<Section> classes = classService.getClassesForTeacher(teacherId);
        classListView.getItems().addAll(classes);
        
        // Set the display format for the Subject model (shows SubjectName in the list)
        classListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Section>() {
            @Override
            protected void updateItem(Section subject, boolean empty) {
                super.updateItem(subject, empty);
                setText(empty ? null : subject.getSectionName());
            }
        });
        
        // Auto-select the first class to load data immediately
        if (!classes.isEmpty()) {
            classListView.getSelectionModel().selectFirst();
        }
    }
    
    // --- Roster Display Logic ---

    private void setupRosterTable() {
        // Clear default content and add the TableView
        contentArea.getChildren().clear();
        contentArea.setPadding(new javafx.geometry.Insets(20));
        contentArea.getChildren().add(rosterTable);
        rosterTable.setPrefWidth(Double.MAX_VALUE);
        rosterTable.setPrefHeight(500);

        // 1. Student ID Column
        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);
        
        // 2. First Name Column
        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(150);
        
        // 3. Last Name Column
        TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(150);

        // Add columns to the table
        rosterTable.getColumns().setAll(idCol, firstNameCol, lastNameCol);
        
        // Link ObservableList to the TableView
        rosterTable.setItems(studentData);
    }

    private void loadClassDetails(Section subject) {
        // 1. Update the header
        contentHeaderLabel.setText(subject.getSectionName() + " Roster & Grades");
        
        // 2. Fetch the student roster from the service
        List<Student> students = classService.getRosterForClass(subject.getId());

        // 3. Clear the observable list and add the new data
        studentData.clear();
        studentData.addAll(students);
        
        // 4. Ensure the header label is added to the content area (if it was cleared)
        if (!contentArea.getChildren().contains(contentHeaderLabel)) {
            contentArea.getChildren().add(0, contentHeaderLabel);
        }
    }

    // --- ACTION HANDLER FOR LOGOUT ---
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // 1. Load the Login FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); 
            Parent loginRoot = loader.load();
            
            // 2. Get the current stage and switch the scene
            Stage stage = (Stage) rootPane.getScene().getWindow();
            
            Scene loginScene = new Scene(loginRoot);
            
            // 3. Re-apply the application-wide stylesheet
            loginScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            
            stage.setScene(loginScene);
            stage.setTitle("Gradebook Application");
            stage.show();
            
            System.out.println("User logged out successfully.");
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login screen during logout.");
        }
    }
}