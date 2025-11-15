package com.gradebookProject.gradebookbackend.dto;

public class CourseDTO {
	private Integer id;
	private String courseName;
	
	public CourseDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
}
