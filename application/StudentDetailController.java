package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;

import model.Assignment; 
import model.Attendance;
import model.Grade;
import model.Student;
import service.ClassService;

public class StudentDetailController implements Initializable {

    @FXML private VBox rootPane;
    @FXML private Label studentNameLabel;
    @FXML private TableView<Grade> gradesTable;
    @FXML private TableView<Attendance> attendanceTable;
    
    private Student currentStudent;
    private Integer currentSectionId; 
    private ClassService classService = new ClassService();
    private Runnable onDataChangedCallback; 
    
    // refresh the main window when a grade is added or deleted
    public void setOnDataChanged(Runnable callback) {
        this.onDataChangedCallback = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupGradesTable();
        setupAttendanceTable();
    }

    public void setStudent(Student student, Integer sectionId) {
        this.currentStudent = student;
        this.currentSectionId = sectionId; 
        //System.out.println("DEBUG: StudentDetailController - Setting section ID: " + sectionId);
        studentNameLabel.setText(student.getFirstName() + " " + student.getLastName());
        loadStudentData();
    }

    private void setupGradesTable() {
        TableColumn<Grade, String> assignmentCol = new TableColumn<>("Assignment");
        assignmentCol.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
        
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
        
        TableColumn<Grade, Double> pctCol = new TableColumn<>("%");
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

        TableColumn<Grade, String> commentCol = new TableColumn<>("Comments");
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comments"));

        gradesTable.getColumns().addAll(assignmentCol, scoreCol, pctCol, letterCol, commentCol);
        gradesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    private void setupAttendanceTable() {
        TableColumn<Attendance, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("attendanceDate"));
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dateCol.setCellFactory(column -> new TableCell<Attendance, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) setText(null);
                else setText(myDateFormatter.format(date));
            }
        });
        TableColumn<Attendance, String> sectionCol = new TableColumn<>("Section");
        sectionCol.setCellValueFactory(new PropertyValueFactory<>("sectionName"));
        TableColumn<Attendance, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        attendanceTable.getColumns().addAll(dateCol, sectionCol, statusCol);
        attendanceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadStudentData() {
        if (currentStudent == null) return;
        
        //System.out.println("=== FIXING EXISTING ASSIGNMENTS: Loading student data for section ID: " + currentSectionId + " ===");
        
        // Get ALL grades for student
        List<Grade> allGrades = classService.getGradesForStudent(currentStudent.getId());
        //System.out.println("DEBUG: Found " + allGrades.size() + " total grades");
        
        // Get assignments for THIS section only
        List<Assignment> sectionAssignments = classService.getAssignmentsForSection(currentSectionId);
        Set<Integer> validAssignmentIds = sectionAssignments.stream()
                .map(Assignment::getId)
                .collect(Collectors.toSet());
        
        //System.out.println("DEBUG: Valid assignment IDs for this section: " + validAssignmentIds);
        
        // Filter grades to only include ones from current section
        List<Grade> filteredGrades = allGrades.stream()
                .filter(g -> validAssignmentIds.contains(g.getAssignmentId()))
                .collect(Collectors.toList());
        
        //System.out.println("DEBUG: After filtering - showing " + filteredGrades.size() + " grades");
        
        // Make sure assignment names are correct by fetching assignment details
        for (Grade grade : filteredGrades) {
            // If the grade doesn't have a proper assignment name, fetch it
            if (grade.getAssignmentName() == null || grade.getAssignmentName().isEmpty()) {
                Assignment assignment = classService.getAssignmentById(grade.getAssignmentId());
                if (assignment != null) {
                    grade.setAssignmentName(assignment.getAssignmentName());
                    System.out.println("DEBUG: Fixed assignment name for ID " + grade.getAssignmentId() + ": " + assignment.getAssignmentName());
                }
            }
        }
        
        // Display the filtered grades
        gradesTable.setItems(FXCollections.observableArrayList(filteredGrades));
        
        // Load attendance (filtered by section)
        List<Attendance> allAttendance = classService.getAttendanceForStudent(currentStudent.getId());
        List<Attendance> filteredAttendance = allAttendance.stream()
                .filter(a -> a.getSectionId() != null && a.getSectionId().equals(currentSectionId))
                .collect(Collectors.toList());
        
        attendanceTable.setItems(FXCollections.observableArrayList(filteredAttendance));
        //System.out.println("=== END FIX ===");
    }

    @FXML
    void handleDeleteGrade(ActionEvent event) {
        Grade selectedGrade = gradesTable.getSelectionModel().getSelectedItem();
        if (selectedGrade == null) {
            showAlert("No Selection", "Please select a grade to delete.");
            return;
        }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Grade");
        alert.setHeaderText("Delete " + selectedGrade.getAssignmentName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = classService.deleteGrade(selectedGrade.getId());
            if (success) {
                gradesTable.getItems().remove(selectedGrade);
                if (onDataChangedCallback != null) onDataChangedCallback.run();
            } else {
                showAlert("Error", "Failed to delete grade.");
            }
        }
    }

    @FXML
    void handleAddGrade(ActionEvent event) {
        //System.out.println("DEBUG: Adding grade for section ID: " + this.currentSectionId);
        
        Dialog<GradeFormData> dialog = new Dialog<>();
        dialog.setTitle("Add New Grade");
        dialog.setHeaderText("Create New Assignment & Grade for Current Class");
        ButtonType addButtonType = new ButtonType("Add", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        TextField scoreField = new TextField();
        TextField maxField = new TextField();
        TextField commentField = new TextField();

        grid.add(new Label("Assignment Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Score Earned:"), 0, 1);
        grid.add(scoreField, 1, 1);
        grid.add(new Label("Max Points:"), 0, 2);
        grid.add(maxField, 1, 2);
        grid.add(new Label("Comments:"), 0, 3); 
        grid.add(commentField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String name = nameField.getText();
                    double score = Double.parseDouble(scoreField.getText());
                    int max = Integer.parseInt(maxField.getText());
                    String comment = commentField.getText();
                    if (name == null || name.trim().isEmpty()) return null;
                    return new GradeFormData(name, score, max, comment);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter valid numbers.");
                    return null;
                }
            }
            return null;
        });

        Optional<GradeFormData> result = dialog.showAndWait();

        result.ifPresent(formData -> {
            try {
                if (this.currentSectionId == null || this.currentSectionId == 0) {
                    showAlert("Error", "Cannot determine current class section. Please close and reopen student details from the class roster.");
                    return;
                }
                
                //System.out.println("DEBUG: Creating assignment for section ID: " + this.currentSectionId);
                
                Assignment newAsgn = new Assignment();
                newAsgn.setAssignmentName(formData.name);
                newAsgn.setSectionId(this.currentSectionId);
                newAsgn.setMaxScore(formData.maxScore);
                newAsgn.setAssignmentType("Homework");
                
                Assignment savedAssignment = classService.addAssignment(newAsgn);
                
                if (savedAssignment != null && savedAssignment.getId() != null) {
                    Grade gradeToSend = new Grade();
                    gradeToSend.setStudentId(currentStudent.getId());
                    gradeToSend.setAssignmentId(savedAssignment.getId());
                    gradeToSend.setScore(formData.score);
                    gradeToSend.setComments("..."); 
                    
                    Grade savedGrade = classService.addGrade(gradeToSend);
                    
                    if (savedGrade != null) {
                        savedGrade.setComments(formData.comment);
                        classService.updateGrade(savedGrade);

                        savedGrade.setAssignmentName(savedAssignment.getAssignmentName());
                        if (savedAssignment.getMaxScore() > 0) {
                            double pct = savedGrade.getScore() / savedAssignment.getMaxScore();
                            savedGrade.setPercentage(pct);
                            savedGrade.setLetterGrade(calculateLetter(pct));
                        }
                        
                        gradesTable.getItems().add(savedGrade);

                        if (onDataChangedCallback != null) onDataChangedCallback.run();
                    } else {
                        showAlert("Error", "Failed to save the Grade.");
                    }
                } else {
                    showAlert("Error", "Could not create assignment. Name must be unique.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An unexpected error occurred.");
            }
        });
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private String calculateLetter(double pct) {
        if (pct >= 0.9) return "A";
        if (pct >= 0.8) return "B";
        if (pct >= 0.7) return "C";
        if (pct >= 0.6) return "D";
        return "F";
    }
    
    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private static class GradeFormData {
        String name;
        double score;
        int maxScore;
        String comment;

        public GradeFormData(String name, double score, int maxScore, String comment) {
            this.name = name;
            this.score = score;
            this.maxScore = maxScore;
            this.comment = comment;
        }
    }
}