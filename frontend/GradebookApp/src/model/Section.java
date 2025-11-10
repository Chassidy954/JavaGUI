package model;

public class Section {
    
    private final int sectionId; 
    private final String sectionName; 
    private final int courseId; 

    public Section(int sectionId, String sectionName, int courseId) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.courseId = courseId;
    }

    public int getSectionId() { return sectionId; }
    public String getSectionName() { return sectionName; } 
    public int getCourseId() { return courseId; } 
    
    @Override
    public String toString() {
        return sectionName;
    }
}