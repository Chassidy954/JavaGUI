package com.gradebookProject.gradebookbackend.entities;
import java.time.LocalDate;
import jakarta.persistence.*;


@Entity
@Table (name = "attendance", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"student_id", "section_id", "attendanceDate"})
})
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "section_id", nullable = false)
	private Section section;
	
	@Column(nullable = false)
	private LocalDate attendanceDate;
	
	@Column(nullable = false)
	private String status; // Present, Absent, Tardy, Excused, etc.
	
	
	public Attendance() {}
	
	public Attendance(Student student, Section section, LocalDate attendanceDate, String status) {
		this.student = student;
		this.section = section;
		this.attendanceDate = attendanceDate;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public void setAttendanceDate(LocalDate attendanceDate)
	{
		this.attendanceDate = attendanceDate;
	}
	
	public LocalDate getAttendanceDate()
	{
		return attendanceDate;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getStatus()
	{
		return status;
	}
}
