package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.TeacherEnrollment;
import com.gradebookProject.gradebookbackend.entities.Section;
import com.gradebookProject.gradebookbackend.entities.Teacher;
import com.gradebookProject.gradebookbackend.dto.TeacherEnrollmentDTO;
import com.gradebookProject.gradebookbackend.repositories.SectionRepository;
import com.gradebookProject.gradebookbackend.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherEnrollmentMapper {
	@Autowired
	SectionRepository sectionRepository;
	@Autowired
	TeacherRepository teacherRepository;
	
	public TeacherEnrollmentDTO convertToDTO(TeacherEnrollment enrollment)
	{
		TeacherEnrollmentDTO dto = new TeacherEnrollmentDTO();
		dto.setId(enrollment.getId());
		dto.setSectionId(enrollment.getSection().getSectionId());
		dto.setSectionName(enrollment.getSection().getSectionName());
		dto.setTeacherId(enrollment.getTeacher().getTeacherId());
		dto.setTeacherName(enrollment.getTeacher().getFirstName() + " " + enrollment.getTeacher().getLastName());
		return dto;
	}
	
	public TeacherEnrollment convertToEntity(TeacherEnrollmentDTO dto)
	{
		TeacherEnrollment enrollment = new TeacherEnrollment();
		Section section = sectionRepository.findById(dto.getSectionId())
				.orElseThrow(() -> new RuntimeException("Couldn't find section with id: " + dto.getSectionId()));
		enrollment.setSection(section);
		Teacher teacher = teacherRepository.findById(dto.getTeacherId())
				.orElseThrow(() -> new RuntimeException("Couldn't find teacher with id: " + dto.getTeacherId()));
		enrollment.setTeacher(teacher);
		return enrollment;
	}
}
