package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.Teacher;
import com.gradebookProject.gradebookbackend.dto.TeacherDTO;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
	public TeacherDTO convertToDTO(Teacher teacher)
	{
		TeacherDTO dto = new TeacherDTO();
		dto.setId(teacher.getTeacherId());
		dto.setFirstName(teacher.getFirstName());
		dto.setLastName(teacher.getLastName());
		return dto;
	}
	
	public Teacher convertToEntity(TeacherDTO dto)
	{
		Teacher teacher = new Teacher();
		teacher.setFirstName(dto.getFirstName());
		teacher.setLastName(dto.getLastName());
		return teacher;
	}
}
