package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class GradeId implements Serializable {
	private Integer student;
	private Integer section;
	private String assignmentName;
	
	public GradeId() {}
	public GradeId (Integer student, Integer section, String assignmentName)
	{
		this.student = student; this.section = section; this.assignmentName = assignmentName;
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
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(assignmentName, section, student);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GradeId other = (GradeId) obj;
		return Objects.equals(assignmentName, other.assignmentName) && Objects.equals(section, other.section)
				&& Objects.equals(student, other.student);
	}
	@Override
	public String toString() {
		return "GradeId [student=" + student + ", section=" + section + ", assignmentName=" + assignmentName
				+ "]";
	}
	
	
}
