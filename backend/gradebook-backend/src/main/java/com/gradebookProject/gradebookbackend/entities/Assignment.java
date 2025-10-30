package com.gradebookProject.gradebookbackend.entities;


import jakarta.persistence.*;

@Entity
@Table (name = "assignments") //works
public class Assignment {
	@EmbeddedId
	private AssignmentId id;
	
	private String assignmentType;
	
	public Assignment() {}
	public Assignment(String assignmentType) { this.assignmentType = assignmentType; }

	public AssignmentId getId() {
		return id;
	}

	public void setId(AssignmentId id) {
		this.id = id;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	@Override
	public String toString() {
		return "Assignment [id=" + id + ", assignmentType=" + assignmentType + "]";
	}
	
}
