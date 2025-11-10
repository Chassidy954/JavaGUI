package model;

import java.time.LocalDate;

public class Attendance {
    // Identification keys
    private final int studentId;
    private final int sectionId;
    private final LocalDate date; 

    // Data field
    private final String status; // e.g., "PRESENT", "ABSENT"

    public Attendance(int studentId, int sectionId, LocalDate date, String status) {
        this.studentId = studentId;
        this.sectionId = sectionId;
        this.date = date;
        this.status = status;
    }

    // Getters for TableView binding
    public int getStudentId() { return studentId; }
    public int getSectionId() { return sectionId; }
    public LocalDate getDate() { return date; }
    public String getStatus() { return status; }
}