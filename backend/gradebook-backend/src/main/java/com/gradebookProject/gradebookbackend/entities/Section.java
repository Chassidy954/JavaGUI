package com.gradebookProject.gradebookbackend.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table (name = "sections") //works
@JsonIdentityInfo( // This is a placeholder to ensure things load. Swap to DTO later.
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "sectionId")

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
