package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class StudentEnrollmentId implements Serializable {
	private Integer student;
	private Integer section;
	
	public StudentEnrollmentId() {}
	public StudentEnrollmentId(Integer student, Integer section) { this.student = student; this.section = section; }
	@Override
	public int hashCode() {
		return Objects.hash(section, student);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentEnrollmentId other = (StudentEnrollmentId) obj;
		return Objects.equals(section, other.section) && Objects.equals(student, other.student);
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
	
	
}
