package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.Section;
import com.gradebookProject.gradebookbackend.entities.Course;
import com.gradebookProject.gradebookbackend.dto.SectionDTO;
import com.gradebookProject.gradebookbackend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SectionMapper {
	
	@Autowired
	CourseRepository courseRepository;
	
	public SectionDTO convertToDTO(Section section)
	{
		SectionDTO dto = new SectionDTO();
		dto.setId(section.getSectionId());
		dto.setSectionName(section.getSectionName());
		dto.setCourseId(section.getCourse().getCourseId());
		dto.setCourseName(section.getCourse().getCourseName());
		return dto;
	}
	
	public Section convertToEntity(SectionDTO dto)
	{
		Section section = new Section();
		section.setSectionName(dto.getSectionName());
		Course course = courseRepository.findById(dto.getCourseId())
				.orElseThrow(() -> new RuntimeException("Couldn't find course with id: " + dto.getCourseId()));
		section.setCourse(course);
		return section;
	}
}
