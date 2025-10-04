package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "TeacherEnrollments")
@IdClass(TeacherEnrollmentId.class)
public class TeacherEnrollment {
	@Id
	@ManyToOne
	@JoinColumn (name = "teacherId")
	private Teacher teacher;
	
	@Id
	@ManyToOne
	@JoinColumn (name = "sectionId")
	private Section section;

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "TeacherEnrollment [teacher=" + teacher + ", section=" + section + "]";
	}
	
	
}
