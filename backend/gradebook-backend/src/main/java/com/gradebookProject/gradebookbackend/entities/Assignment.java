package com.gradebookProject.gradebookbackend.entities;


import jakarta.persistence.*;

@Entity
@Table (name = "assignments", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"section_id", "assignment_name"})
}) //works
public class Assignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "section_id", nullable = false)
	private Section section;
	
	@Column(name = "assignment_name")
	private String assignmentName;
	
	private String assignmentType;
	
	private Integer maxScore;
	
	public Assignment() {}
	
	public Assignment(String assignmentType, Section section, Integer maxScore) 
	{ 
		this.assignmentType = assignmentType; 
		this.section = section;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}
	
	public String getAssignmentName()
	{
		return assignmentName;
	}
	
	public void setAssignmentName(String name)
	{
		assignmentName = name;
	}
	
	public void setSection(Section section)
	{
		this.section = section;
	}
	
	public Section getSection()
	{
		return section;
	}
	

	@Override
	public String toString() {
		return "Assignment [id=" + id + ", assignmentType=" + assignmentType + "]";
	}
	
}
