package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "StudentEnrollments")
@IdClass(StudentEnrollmentId.class)
public class StudentEnrollment {
	
	@Id
	@ManyToOne
	@JoinColumn (name = "studentId")
	private Student student;
	
	@Id
	@ManyToOne
	@JoinColumn (name = "sectionId")
	private Section section;

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

	@Override
	public String toString() {
		return "StudentEnrollment [student=" + student + ", section=" + section + "]";
	}
	
	
}
