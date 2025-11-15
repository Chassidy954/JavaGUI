package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.Grade;
import com.gradebookProject.gradebookbackend.entities.Assignment;
import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.repositories.AssignmentRepository;
import com.gradebookProject.gradebookbackend.repositories.StudentRepository;
import com.gradebookProject.gradebookbackend.dto.GradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	@Autowired
	StudentRepository studentRepository;
	
	public GradeDTO convertToDTO(Grade grade)
	{
		GradeDTO dto = new GradeDTO();
		dto.setId(grade.getId());
		dto.setAssignmentId(grade.getAssignment().getId());
		dto.setAssignmentName(grade.getAssignment().getAssignmentName());
		dto.setComments(grade.getComments());
		dto.setLetterGrade(grade.getLetterGrade());
		dto.setPercentage(grade.getPercentage());
		dto.setScore(grade.getScore());
		dto.setStudentId(grade.getStudent().getStudentId());
		dto.setStudentName(grade.getStudent().getFirstName() + " " + grade.getStudent().getLastName());
		return dto;
	}
	
	public Grade convertToEntity(GradeDTO dto)
	{
		Grade grade = new Grade();
		Assignment assignment = assignmentRepository.findById(dto.getAssignmentId())
				.orElseThrow(() -> new RuntimeException("Couldn't find assignment with id: " + dto.getAssignmentId()));
		grade.setAssignment(assignment);
		Student student = studentRepository.findById(dto.getStudentId())
				.orElseThrow(() -> new RuntimeException("Couldn't find student with id: " + dto.getStudentId()));
		grade.setStudent(student);
		grade.setComments(dto.getComments());
		grade.setScore(dto.getScore());
		return grade;
	}
}
