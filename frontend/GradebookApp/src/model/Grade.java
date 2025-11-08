package model;

// Assuming grade is a percentage or point value
public class Grade {
    private final String assignmentName;
    private final double score;
    private final double maxScore;
    
    public Grade(String assignmentName, double score, double maxScore) {
        this.assignmentName = assignmentName;
        this.score = score;
        this.maxScore = maxScore;
    }

    public String getAssignmentName() { return assignmentName; }
    public double getScore() { return score; }
    public double getMaxScore() { return maxScore; }
    
    public double getPercentage() {
        if (maxScore == 0) return 0.0;
        return (score / maxScore) * 100.0;
    }
}