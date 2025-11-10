package model;

/**
 * Model representing a single Grade entry. 
 * This model is tailored for display in the Gradebook TableView, combining 
 * the score data (from the Grades table) with the assignment context.
 */
public class Grade {
    
    // Primary Record ID (Essential for sending updates back to the API)
    private final int id; 
    
    // Identification Fields (Maps to GradeId components)
    private final int studentId;
    private final String assignmentName;
    
    // Score Data Fields (From Grades entity)
    private final double score;       
    private final double maxScore;
    private final String comments;    

    // Constructor requires all fields necessary for display and calculation
    public Grade(int id, int studentId, String assignmentName, double score, double maxScore, String comments) {
        this.id = id; 
        this.studentId = studentId;
        this.assignmentName = assignmentName;
        this.score = score;
        this.maxScore = maxScore;
        this.comments = comments;
    }

    // Getters for Data Binding (Required for TableView)
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public String getAssignmentName() { return assignmentName; }
    public double getScore() { return score; }
    public double getMaxScore() { return maxScore; }
    public String getComments() { return comments; }

    /**
     * Calculates the percentage score.
     */
    public double getPercentage() {
        if (maxScore <= 0) return 0.0;
        return (score / maxScore) * 100.0;
    }

    /**
     * Helper method to assign a simple letter grade based on standards.
     */
    public String getLetterGrade() {
        double percentage = getPercentage();
        if (percentage >= 90) return "A";
        else if (percentage >= 80) return "B";
        else if (percentage >= 70) return "C";
        else if (percentage >= 60) return "D";
        else return "F";
    }
}