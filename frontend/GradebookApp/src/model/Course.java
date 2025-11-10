package model;

/**
 * Model representing a single Course entity. 
 * This is used if the front end needs to list all course types, 
 * independent of specific sections.
 */
public class Course {
    
    // Uses Integer because the backend uses the object wrapper for IDs
    private final int courseId; 
    private final String courseName;

    public Course(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    // Getters (Required for JavaFX TableView/ListView binding)
    public int getCourseId() { 
        return courseId; 
    }
    
    public String getCourseName() { 
        return courseName; 
    }

}