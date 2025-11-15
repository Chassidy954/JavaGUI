package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "teacherenrollments", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"teacher_id", "section_id"})
})
public class TeacherEnrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "teacher_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Teacher teacher;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "section_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Section section;
	
	public TeacherEnrollment() {}
	public TeacherEnrollment(Teacher teacher, Section section)
	{
		this.teacher = teacher;
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
