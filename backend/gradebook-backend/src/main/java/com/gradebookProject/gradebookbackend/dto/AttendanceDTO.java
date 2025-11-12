package com.gradebookProject.gradebookbackend.dto;

import java.time.LocalDate;

public class AttendanceDTO {
	private Integer id;
	private Integer studentId;
	private String studentName;
	private Integer sectionId;
	private String sectionName;
	private LocalDate attendanceDate;
	private String status;
	
	public AttendanceDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	
}
