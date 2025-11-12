package com.gradebookProject.gradebookbackend.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "teachers") 
public class Teacher {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer teacherId;
	
	private String firstName;
	private String lastName;
	
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Teacher() {}
	
	public Teacher(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	
	@Override
	public String toString() {
		return "Teacher [teacherId=" + teacherId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
