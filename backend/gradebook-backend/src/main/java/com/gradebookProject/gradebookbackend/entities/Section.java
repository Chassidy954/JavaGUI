package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "Sections")
public class Section {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer sectionId;
	
	@ManyToOne
	@JoinColumn (name="courseId")
	private Course course;
	
	private String sectionName;

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Override
	public String toString() {
		return "Section [sectionId=" + sectionId + ", course=" + course + ", sectionName=" + sectionName + "]";
	}
	
	public Section() {}
	public Section(String sectionName) { this.sectionName = sectionName; }
}
