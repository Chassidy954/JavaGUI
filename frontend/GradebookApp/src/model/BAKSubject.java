package model;

public class BAKSubject {
    private final String subjectId;
    private final String subjectName;

    public BAKSubject(String subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getSubjectId() { return subjectId; }
    public String getSubjectName() { return subjectName; }
}