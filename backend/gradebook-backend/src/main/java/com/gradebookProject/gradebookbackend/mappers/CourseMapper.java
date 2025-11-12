package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.Course;
import com.gradebookProject.gradebookbackend.dto.CourseDTO;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
	public CourseDTO convertToDTO(Course course)
	{
		CourseDTO dto = new CourseDTO();
		dto.setCourseName(course.getCourseName());
		dto.setId(course.getCourseId());
		return dto;
	}
	
	public Course convertToEntity(CourseDTO dto)
	{
		Course course = new Course();
		course.setCourseName(dto.getCourseName());
		return course;
	}
}
