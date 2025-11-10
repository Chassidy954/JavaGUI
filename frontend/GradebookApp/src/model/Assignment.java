package model;

public class Assignment {
    
    //Composite Key Fields (from AssignmentId)
    private final int sectionId; 
    private final String assignmentName; 
    
    //Data Fields
    private final String assignmentType; 
    private final double maxPoints;

    public Assignment(int sectionId, String assignmentName, String assignmentType, double maxPoints) {
        this.sectionId = sectionId;
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.maxPoints = maxPoints;
    }

    // Getters for display and identification
    public int getSectionId() { return sectionId; }
    public String getAssignmentName() { return assignmentName; }
    public String getAssignmentType() { return assignmentType; }
    public double getMaxPoints() { return maxPoints; }

}