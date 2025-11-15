package com.gradebookProject.gradebookbackend.dto;

public class AssignmentDTO {
	private Integer id;
	private Integer sectionId;
	private String sectionName;
	private String assignmentName;
	private String assignmentType;
	private Integer maxScore;
	
	public AssignmentDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}
	
	
}
