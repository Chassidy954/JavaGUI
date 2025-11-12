package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table (name="courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer courseId;
	
	private String courseName;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public Course() {}
	public Course(String courseName) { this.courseName = courseName; }

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + "]";
	}
}
