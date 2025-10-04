package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AssignmentId implements Serializable {
	private Integer sectionId;
	private String assignmentName;
	
	public AssignmentId() {}
	public AssignmentId(Integer sectionId, String assignmentName) { this.sectionId = sectionId; this.assignmentName = assignmentName; } 
	
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	@Override
	public String toString() {
		return "AssignmentId [sectionId=" + sectionId + ", assignmentName=" + assignmentName + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(assignmentName, sectionId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignmentId other = (AssignmentId) obj;
		return Objects.equals(assignmentName, other.assignmentName) && Objects.equals(sectionId, other.sectionId);
	}
	
	
}
