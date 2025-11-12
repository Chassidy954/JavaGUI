package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table (name = "studentenrollments", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"student_id", "section_id"})
})
public class StudentEnrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "student_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Student student;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "section_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Section section;

	
	public StudentEnrollment() {}
	
	public StudentEnrollment(Student student, Section section)
	{
		this.student = student;
		this.section = section;
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
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public Integer getId()
	{
		return id;
	}

	@Override
	public String toString() {
		return "StudentEnrollment [student=" + student + ", section=" + section + "]";
	}
	
	
}
