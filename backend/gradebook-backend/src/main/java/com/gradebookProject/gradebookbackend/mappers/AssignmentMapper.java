package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.Assignment;
import com.gradebookProject.gradebookbackend.dto.AssignmentDTO;
import com.gradebookProject.gradebookbackend.entities.Section;
import com.gradebookProject.gradebookbackend.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {
	
	@Autowired
	SectionRepository sectionRepository;
	
	public AssignmentDTO convertToDTO(Assignment assignment)
	{
		AssignmentDTO dto = new AssignmentDTO();
		dto.setId(assignment.getId());
		dto.setAssignmentName(assignment.getAssignmentName());
		dto.setAssignmentType(assignment.getAssignmentType());
		dto.setSectionId(assignment.getSection().getSectionId());
		dto.setSectionName(assignment.getSection().getSectionName());
		dto.setMaxScore(assignment.getMaxScore());
		return dto;
	}
	
	public Assignment convertToEntity(AssignmentDTO dto)
	{
		Assignment assignment = new Assignment();
		assignment.setAssignmentName(dto.getAssignmentName());
		assignment.setAssignmentType(dto.getAssignmentType());
		assignment.setMaxScore(dto.getMaxScore());
		
		Section section = sectionRepository.findById(dto.getSectionId())
				.orElseThrow(() -> new RuntimeException("Section not found with id: " + dto.getSectionId()));
		assignment.setSection(section);
		
		return assignment;
	}
}
