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
import java.util.List;

// Corrected Imports
import model.Section; 
import model.Student; 
import model.Teacher; 
import service.ClassService; 

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
        setupRosterTable();

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
            loadClassList(teacher.getTeacherId());
        }
    }

    private void loadClassList(String teacherId) {
        List<Section> classes = classService.getClassesForTeacher(teacherId);
        classListView.getItems().addAll(classes);
        
        classListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Section>() {
            @Override
            protected void updateItem(Section section, boolean empty) {
                super.updateItem(section, empty);
                setText(empty ? null : section.getSectionName());
            }
        });
        
        if (!classes.isEmpty()) {
            classListView.getSelectionModel().selectFirst();
        }
    }
    
    @SuppressWarnings("unchecked") 
    private void setupRosterTable() {
        // Clear default content and add the TableView
        contentArea.getChildren().clear();
        contentArea.setPadding(new javafx.geometry.Insets(20));
        contentArea.getChildren().add(rosterTable);
        rosterTable.setPrefWidth(Double.MAX_VALUE);
        rosterTable.setPrefHeight(500);

        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        idCol.setPrefWidth(50);
        
        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(150);
        
        TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(150);
        
        rosterTable.getColumns().setAll(idCol, firstNameCol, lastNameCol);
        rosterTable.setItems(studentData);
    }

    private void loadClassDetails(Section section) {
        contentHeaderLabel.setText(section.getSectionName() + " Roster & Grades");
        
        String sectionIdString = section.getSectionId() + "";
        
        List<Student> students = classService.getRosterForClass(sectionIdString);

        studentData.clear();
        studentData.addAll(students);
        
        if (!contentArea.getChildren().contains(contentHeaderLabel)) {
            contentArea.getChildren().add(0, contentHeaderLabel);
        }
        
        System.out.println("Roster for " + section.getSectionName() + " loaded successfully.");
    }

    // --- ACTION HANDLER FOR LOGOUT ---
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); 
            Parent loginRoot = loader.load();
            
            // Get the current stage and switch the scene
            Stage stage = (Stage) rootPane.getScene().getWindow();
            
            Scene loginScene = new Scene(loginRoot);
            
            // Re-apply the application-wide stylesheet
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