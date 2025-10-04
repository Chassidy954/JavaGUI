package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class TeacherEnrollmentId implements Serializable {
	private Integer teacher;
	private Integer section;
	
	public TeacherEnrollmentId() {}
	public TeacherEnrollmentId(Integer teacher, Integer section) { this.teacher = teacher; this.section = section; }
	public Integer getTeacher() {
		return teacher;
	}
	public void setTeacher(Integer teacher) {
		this.teacher = teacher;
	}
	public Integer getSection() {
		return section;
	}
	public void setSection(Integer sectionId) {
		this.section = sectionId;
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
		return Objects.equals(section, other.section) && Objects.equals(teacher, other.teacher);
	}
	
	
}
