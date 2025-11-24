package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Grade;
import model.Section;
import model.Student;
import model.Teacher;
import model.Assignment;
import service.ClassService; 

public class DashboardController {

    @FXML private BorderPane rootPane; 
    @FXML private Label teacherNameLabel; 
    @FXML private ListView<Section> classListView;
    @FXML private Label contentHeaderLabel; 
    @FXML private VBox contentArea; 
    @FXML private HBox headerBar; 

    private TableView<Student> rosterTable = new TableView<>();

    private Teacher currentTeacher;
    private Section currentSection;
    private ClassService classService = new ClassService();
    private ObservableList<Student> studentData = FXCollections.observableArrayList();

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
        classListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Section>() {
            @Override
            protected void updateItem(Section subject, boolean empty) {
                super.updateItem(subject, empty);
                setText(empty ? null : subject.getSectionName());
            }
        });
        if (!classes.isEmpty()) classListView.getSelectionModel().selectFirst();
    }

    private void setupRosterTable() {
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

        TableColumn<Student, Double> avgGradeCol = new TableColumn<>("Avg Grade");
        avgGradeCol.setCellValueFactory(new PropertyValueFactory<>("average"));
        avgGradeCol.setPrefWidth(100);
        avgGradeCol.setStyle("-fx-alignment: CENTER-RIGHT;"); 

        //this customizes the cells to format strings properly. In this case I used it to display one decimal point
        //JavaFX doesn't have a simple way to do this for some reason
        avgGradeCol.setCellFactory(tc -> new TableCell<Student, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.1f%%", item)); 
                }
            }
        });

        //this warning is safe to ignore its mad that the parameters are all different types (this shouldn't effect anything)
        rosterTable.getColumns().setAll(idCol, firstNameCol, lastNameCol, avgGradeCol); 
        rosterTable.setItems(studentData);

        // this is the listener for mouse clicks (double click to pull up student detail pages)
        rosterTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    Student selectedStudent = row.getItem();
                    openStudentDetailWindow(selectedStudent);
                }
            });
            return row;
        });
    }

    private void openStudentDetailWindow(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StudentDetailView.fxml")); 
            Parent root = loader.load();

            StudentDetailController controller = loader.getController();

            int sectionId = (currentSection != null) ? currentSection.getId() : 0;
            //System.out.println("DEBUG: Opening student details for section ID: " + sectionId);
            controller.setStudent(student, sectionId);

            controller.setOnDataChanged(() -> {
                System.out.println("Recalculating average for: " + student.getFirstName());

                // Use the SECTION-FILTERED grades for recalculation
                List<Grade> freshGrades = classService.getGradesForStudent(student.getId());
                List<Assignment> sectionAssignments = classService.getAssignmentsForSection(currentSection.getId());
                Set<Integer> validAssignmentIds = sectionAssignments.stream()
                        .map(Assignment::getId)
                        .collect(Collectors.toSet());

                List<Grade> sectionGrades = freshGrades.stream()
                        .filter(g -> validAssignmentIds.contains(g.getAssignmentId()))
                        .collect(Collectors.toList());

                double newAverage = classService.calculateUnweightedAverage(sectionGrades);
                student.setAverage(newAverage);

                rosterTable.refresh();
            });

            Stage stage = new Stage();
            stage.setTitle("Student Record: " + student.getLastName() + " - " + currentSection.getSectionName());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // this makes them handle the student window before doing other stuff
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading StudentDetailView.");
        }
    }

    // I had a billion display issues here so this is different
    private void loadClassDetails(Section subject) {
        this.currentSection = subject; 
        contentHeaderLabel.setText(subject.getSectionName() + " Roster & Grades");

        List<Student> students = classService.getRosterForClass(subject.getId());

        // Get assignments for THIS section to use for filtering
        List<Assignment> sectionAssignments = classService.getAssignmentsForSection(subject.getId());
        Set<Integer> validAssignmentIds = sectionAssignments.stream()
                .map(Assignment::getId)
                .collect(Collectors.toSet());

        //System.out.println("DEBUG: Loading class details for " + subject.getSectionName());
        //System.out.println("DEBUG: Valid assignment IDs: " + validAssignmentIds);

        for (Student student : students) {
            // Get ALL grades for student
            List<Grade> allGrades = classService.getGradesForStudent(student.getId());

            // Filter to only include grades from THIS section (this gave me a really hard time for no reason)
            List<Grade> sectionGrades = allGrades.stream()
                    .filter(g -> validAssignmentIds.contains(g.getAssignmentId()))
                    .collect(Collectors.toList());

            //System.out.println("DEBUG: " + student.getFirstName() + " - All grades: " + allGrades.size() + ", Section grades: " + sectionGrades.size());

            // Calculate average using ONLY section grades
            double average = classService.calculateUnweightedAverage(sectionGrades);
            student.setAverage(average); 
        }

        studentData.clear();
        studentData.addAll(students);

        if (!contentArea.getChildren().contains(contentHeaderLabel)) {
            contentArea.getChildren().add(0, contentHeaderLabel);
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); 
            Parent loginRoot = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            loginScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            stage.setScene(loginScene);
            stage.setTitle("EduTracker Login");
            stage.show();
            System.out.println("User logged out successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login screen during logout.");
        }
    }
}