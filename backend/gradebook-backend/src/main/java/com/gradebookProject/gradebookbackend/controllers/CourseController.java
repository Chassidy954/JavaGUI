//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.entities.Course;
import com.gradebookProject.gradebookbackend.repositories.CourseRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping //get all courses
	public List<Course> getAllCourses()
	{
		return courseRepository.findAll();
	}
	
	@PostMapping //create new course
	public Course createCourse(@RequestBody Course course)
	{
		return courseRepository.save(course);
	}
	
}
