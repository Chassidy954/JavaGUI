package com.gradebookProject.gradebookbackend.entities;

import java.io.Serializable;
import java.util.Objects;

public class TeacherEnrollmentId implements Serializable {
    private Teacher teacher;    // Change from Integer to Teacher
    private Section section;    // Change from Integer to Section
    
    public TeacherEnrollmentId() {}
    
    public TeacherEnrollmentId(Teacher teacher, Section section) { 
        this.teacher = teacher; 
        this.section = section; 
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public Section getSection() {
        return section;
    }
    
    public void setSection(Section section) {  // Fixed parameter name
        this.section = section;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(section, teacher);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TeacherEnrollmentId other = (TeacherEnrollmentId) obj;
        return Objects.equals(section, other.section) && 
               Objects.equals(teacher, other.teacher);
    }
    
    @Override
    public String toString() {
        return "TeacherEnrollmentId [teacher=" + teacher + ", section=" + section + "]";
    }
}