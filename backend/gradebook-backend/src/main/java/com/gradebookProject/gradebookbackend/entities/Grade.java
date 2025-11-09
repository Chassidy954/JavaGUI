package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "grades", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"student_id", "assignment_name"})
})
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;
    
    @Column(nullable = false)
    private Double score;
    
    private String comments;
    
    // Constructors
    public Grade() {}
    
    public Grade(Student student, Assignment assignment, Double score) {
        this.student = student;
        this.assignment = assignment;
        this.score = score;
    }
    
    public Grade(Student student, Assignment assignment, Double score, String comments) {
        this.student = student;
        this.assignment = assignment;
        this.score = score;
        this.comments = comments;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Assignment getAssignment() {
        return assignment;
    }
    
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    // Helper method to calculate percentage
    public Double getPercentage() {
        if (assignment != null && assignment.getMaxScore() != null && assignment.getMaxScore() > 0) {
            return (score / assignment.getMaxScore()) * 100;
        }
        return null;
    }
    
    // Helper method to get letter grade
    public String getLetterGrade() {
        Double percentage = getPercentage();
        if (percentage == null) return "N/A";
        
        if (percentage >= 90) return "A";
        else if (percentage >= 80) return "B";
        else if (percentage >= 70) return "C";
        else if (percentage >= 60) return "D";
        else return "F";
    }
    
    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", student=" + (student != null ? student.getFirstName() + " " + student.getLastName() : "null") +
                ", assignment=" + (assignment != null ? assignment.getAssignmentName() : "null") +
                ", score=" + score +
                ", comments='" + comments + '\'' +
                '}';
    }
}