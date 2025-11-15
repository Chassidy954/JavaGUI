package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.StudentEnrollment;
import com.gradebookProject.gradebookbackend.entities.Section;
import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.dto.StudentEnrollmentDTO;
import com.gradebookProject.gradebookbackend.repositories.SectionRepository;
import com.gradebookProject.gradebookbackend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentEnrollmentMapper {
	@Autowired
	SectionRepository sectionRepository;
	@Autowired
	StudentRepository studentRepository;
	
	public StudentEnrollmentDTO convertToDTO(StudentEnrollment enrollment)
	{
		StudentEnrollmentDTO dto = new StudentEnrollmentDTO();
		dto.setId(enrollment.getId());
		dto.setSectionId(enrollment.getSection().getSectionId());
		dto.setSectionName(enrollment.getSection().getSectionName());
		dto.setStudentId(enrollment.getStudent().getStudentId());
		dto.setStudentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName());
		return dto;
	}
	
	public StudentEnrollment convertToEntity(StudentEnrollmentDTO dto)
	{
		StudentEnrollment enrollment = new StudentEnrollment();
		Section section = sectionRepository.findById(dto.getSectionId())
				.orElseThrow(() -> new RuntimeException("Couldn't find section with id: " + dto.getSectionId()));
		enrollment.setSection(section);
		Student student = studentRepository.findById(dto.getStudentId())
				.orElseThrow(() -> new RuntimeException("Couldn't find student with id: " + dto.getStudentId()));
		enrollment.setStudent(student);
		return enrollment;
	}
}
