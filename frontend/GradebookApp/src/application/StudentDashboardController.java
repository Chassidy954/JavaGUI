package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import model.Assignment;
import model.Attendance;
import model.Grade;
import model.Section;
import model.Student;
import service.ClassService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentDashboardController {

    @FXML private BorderPane rootPane;
    @FXML private Label studentNameLabel;
    @FXML private ListView<Section> classListView;
    @FXML private Label classNameLabel;
    @FXML private Label overallGradeLabel;

    @FXML private TableView<Grade> gradesTable;
    @FXML private TableView<Attendance> attendanceTable;

    private Student currentStudent;
    private ClassService classService = new ClassService();

    @FXML
    public void initialize() {
        setupGradesTable();
        setupAttendanceTable();

        classListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadClassData(newVal);
            }
        });
    }

    public void setStudent(Student student) {
        this.currentStudent = student;
        studentNameLabel.setText("Welcome, " + student.getFirstName());

        List<Section> myClasses = classService.getClassesForStudent(student.getId());
        classListView.getItems().setAll(myClasses);

        classListView.setCellFactory(param -> new ListCell<Section>() {
            @Override
            protected void updateItem(Section item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.getSectionName());
            }
        });

        if (!myClasses.isEmpty()) {
            classListView.getSelectionModel().selectFirst();
        }
    }

    private void loadClassData(Section section) {
        classNameLabel.setText(section.getSectionName());
        //System.out.println("------------------------------------------------");
        //System.out.println("DEBUG: Selected Class: " + section.getSectionName() + " (ID: " + section.getId() + ")");

        // 1. Get ALL grades for student
        List<Grade> allGrades = classService.getGradesForStudent(currentStudent.getId());
        System.out.println("DEBUG: Total grades found for student: " + allGrades.size());

        // 2. Get assignments for THIS section only
        List<Assignment> sectionAssignments = classService.getAssignmentsForSection(section.getId());
        System.out.println("DEBUG: Assignments found for " + section.getSectionName() + ": " + sectionAssignments.size());

        // Debug: Print assignments for this section
        //for (Assignment assign : sectionAssignments) {
        //    System.out.println("  - Assignment: " + assign.getAssignmentName() + " (ID: " + assign.getId() + ")");
        //}

        // 3. Create a Set of valid Assignment IDs for this section
        Set<Integer> validAssignmentIds = sectionAssignments.stream()
                .map(Assignment::getId)
                .collect(Collectors.toSet());

        // 4. Keep only grades where assignment belongs to current section
        List<Grade> sectionGrades = allGrades.stream()
                .filter(g -> validAssignmentIds.contains(g.getAssignmentId()))
                .collect(Collectors.toList());

        //System.out.println("DEBUG: Filtered grades for " + section.getSectionName() + ": " + sectionGrades.size());

        // Debug: Print filtered grades
        //for (Grade grade : sectionGrades) {
        //    System.out.println("  - " + grade.getAssignmentName() + " (Score: " + grade.getScore() + ")");
        //}

        // 5. Calculate average using ONLY section-specific grades
        double average = classService.calculateUnweightedAverage(sectionGrades);
        overallGradeLabel.setText(String.format("%.1f%%", average));

        // 6. Update UI with filtered grades
        gradesTable.setItems(FXCollections.observableArrayList(sectionGrades));

        // 7. Filter attendance for this section
        List<Attendance> attendance = classService.getAttendanceForStudent(currentStudent.getId());
        List<Attendance> sectionAttendance = attendance.stream()
                .filter(a -> a.getSectionName() != null && a.getSectionName().equals(section.getSectionName()))
                .collect(Collectors.toList());

        attendanceTable.setItems(FXCollections.observableArrayList(sectionAttendance));
    }

    private void setupGradesTable() {
        TableColumn<Grade, String> assignCol = new TableColumn<>("Assignment");
        assignCol.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));

        TableColumn<Grade, Double> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setCellFactory(tc -> new TableCell<Grade, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("%.1f", item));
            }
        });

        TableColumn<Grade, Double> pctCol = new TableColumn<>("Percentage");
        pctCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        pctCol.setCellFactory(tc -> new TableCell<Grade, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("%.1f%%", item * 100));
            }
        });

        TableColumn<Grade, String> letterCol = new TableColumn<>("Letter");
        letterCol.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        // This auto-fits the tables to the width of the window
        gradesTable.getColumns().setAll(assignCol, scoreCol, pctCol, letterCol);
        gradesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupAttendanceTable() {
        TableColumn<Attendance, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("attendanceDate"));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dateCol.setCellFactory(tc -> new TableCell<Attendance, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(fmt.format(item));
            }
        });

        TableColumn<Attendance, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        attendanceTable.getColumns().setAll(dateCol, statusCol);
        attendanceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("EduTracker Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}