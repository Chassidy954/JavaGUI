package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "grades") //works
@IdClass(GradeId.class)
public class Grade {
	@Id
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	@Id
	@ManyToOne
	@JoinColumn (name = "sectionId")
	private Section section;
	
	@Id
	private String assignmentName;
	
	private Double score;
	
	public Grade() {}
	public Grade(Student student, Section section, String assignmentName, Double score)
	{
		this.student = student; this.section = section; this.assignmentName = assignmentName; this.score = score;
	}
	

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	
}
