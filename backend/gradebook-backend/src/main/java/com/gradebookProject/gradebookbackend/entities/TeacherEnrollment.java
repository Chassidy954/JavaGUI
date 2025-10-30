package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "teacherenrollments") //works!
@IdClass(TeacherEnrollmentId.class)
public class TeacherEnrollment {
	@Id
	@ManyToOne
	@JoinColumn (name = "teacher_id")
	private Teacher teacher;
	
	@Id
	@ManyToOne
	@JoinColumn (name = "section_id")
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
