//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.dto.CourseDTO;
import com.gradebookProject.gradebookbackend.entities.Course;
import com.gradebookProject.gradebookbackend.repositories.CourseRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping //get all courses
	public List<CourseDTO> getAllCourses()
	{
		return gradeBookService.findAllCourses();
	}
	
	@PostMapping //create new course
	public CourseDTO createCourse(@RequestBody CourseDTO dto)
	{
		return gradeBookService.saveCourse(dto);
	}
	
}
