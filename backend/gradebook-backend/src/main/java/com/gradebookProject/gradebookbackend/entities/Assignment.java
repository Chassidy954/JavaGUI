package com.gradebookProject.gradebookbackend.entities;


import jakarta.persistence.*;

@Entity
@Table (name = "assignments") //works
public class Assignment {
	@EmbeddedId
	private AssignmentId id;
	
	private String assignmentType;
	
	private Integer maxScore;
	
	public Assignment() {}
	
	public Assignment(String assignmentType, Integer maxScore) 
	{ 
		this.assignmentType = assignmentType; 
		this.maxScore = maxScore;
	}
	
	public Integer getMaxScore()
	{
		return maxScore;
	}
	
	public void setMaxScore(Integer maxScore)
	{
		this.maxScore = maxScore;
	}

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
