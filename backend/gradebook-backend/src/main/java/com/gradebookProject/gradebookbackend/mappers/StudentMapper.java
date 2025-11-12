package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.dto.StudentDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
	public StudentDTO convertToDTO(Student student)
	{
		StudentDTO dto = new StudentDTO();
		dto.setId(student.getStudentId());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		return dto;
	}
	
	public Student convertToEntity(StudentDTO dto)
	{
		Student student = new Student();
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		
		return student;
	}
}
