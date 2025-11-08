package model;

public class Subject {
    private final String subjectId;
    private final String subjectName;

    public Subject(String subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getSubjectId() { return subjectId; }
    public String getSubjectName() { return subjectName; }
}