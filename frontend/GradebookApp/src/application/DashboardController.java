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
import java.util.stream.Collectors; 

import model.Grade;
import model.Section;
import model.Student;
import model.Teacher;
import service.ClassService; 


public class DashboardController {

    // FXML Elements
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
        
        if (!classes.isEmpty()) {
            classListView.getSelectionModel().selectFirst();
        }
    }
    
    private void setupRosterTable() {
        // Clear default content and add the TableView
        contentArea.getChildren().clear();
        contentArea.setPadding(new javafx.geometry.Insets(20));
        contentArea.getChildren().add(rosterTable);
        rosterTable.setPrefWidth(Double.MAX_VALUE);
        rosterTable.setPrefHeight(500);

        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);
        
        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(150);
        
        TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(150);
        
        // Average Grade Column
        TableColumn<Student, String> avgGradeCol = new TableColumn<>("Avg Grade");
        // Binds to the new display getter that performs rounding
        avgGradeCol.setCellValueFactory(new PropertyValueFactory<>("average"));
        avgGradeCol.setPrefWidth(100);
        avgGradeCol.setStyle("-fx-alignment: CENTER-RIGHT;"); 
        
        // Add columns to the table
        rosterTable.getColumns().setAll(idCol, firstNameCol, lastNameCol, avgGradeCol); 
        rosterTable.setItems(studentData);
    }

    private void loadClassDetails(Section subject) {
        // 1. Update the header
        contentHeaderLabel.setText(subject.getSectionName() + " Roster & Grades");
        
        // 2. Fetch the student roster from the service
        List<Student> students = classService.getRosterForClass(subject.getId());
        for (Student student : students) {
        	List<Grade> studentGrades = classService.getGradesForStudent(student.getId());
            // Calculate average using the service layer method
            double average = classService.calculateUnweightedAverage(studentGrades);
            
            // Set the calculated average on the Student model (mutator method)
            student.setAverage(average); // REWORK THIS. 
            // Students should not have an average like this since it's per section.
            // I've only added this to fix the error from "setCalculatedAverage", which appeared randomly.
        }
        
        // 3. Update Table View with processed students
        studentData.clear();
        studentData.addAll(students);
        
        if (!contentArea.getChildren().contains(contentHeaderLabel)) {
            contentArea.getChildren().add(0, contentHeaderLabel);
        }
        
        System.out.println("Roster for " + subject.getSectionName() + " loaded successfully with calculated averages.");
    }

    // Action handler for login
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); 
            Parent loginRoot = loader.load();
            
            Stage stage = (Stage) rootPane.getScene().getWindow();
            
            Scene loginScene = new Scene(loginRoot);
            
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