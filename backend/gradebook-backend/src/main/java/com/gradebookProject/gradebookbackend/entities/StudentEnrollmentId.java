// NOTE: MARKED FOR DELETION

package com.gradebookProject.gradebookbackend.entities;

import java.io.Serializable;
import java.util.Objects;

public class StudentEnrollmentId implements Serializable {
    private Integer student;  // Must match the field name in StudentEnrollment class
    private Integer section;  // Must match the field name in StudentEnrollment class
    
    public StudentEnrollmentId() {}
    
    public StudentEnrollmentId(Integer student, Integer section) {
        this.student = student;
        this.section = section;
    }
    
    public Integer getStudent() {
        return student;
    }
    
    public void setStudent(Integer student) {
        this.student = student;
    }
    
    public Integer getSection() {
        return section;
    }
    
    public void setSection(Integer section) {
        this.section = section;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEnrollmentId that = (StudentEnrollmentId) o;
        return Objects.equals(student, that.student) && 
               Objects.equals(section, that.section);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(student, section);
    }
    
    @Override
    public String toString() {
        return "StudentEnrollmentId{" +
                "student=" + student +
                ", section=" + section +
                '}';
    }
}