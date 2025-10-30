package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "studentenrollments") //works
@IdClass(StudentEnrollmentId.class)
public class StudentEnrollment {
	
	@Id
	@ManyToOne
	@JoinColumn (name = "student_id")
	private Student student;
	
	@Id
	@ManyToOne
	@JoinColumn (name = "section_id")
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
