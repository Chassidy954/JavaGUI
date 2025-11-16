//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.dto.AssignmentDTO;
import com.gradebookProject.gradebookbackend.dto.CourseDTO;
import com.gradebookProject.gradebookbackend.entities.Course;
import com.gradebookProject.gradebookbackend.repositories.CourseRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;

import java.util.List;
import java.util.Optional;

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
	
	//Optional<CourseDTO> findCourseById(Integer courseId);
	//getmapping for id
	@GetMapping("/{id}")
	public CourseDTO getById(@PathVariable Integer id)
	{
		return gradeBookService.findCourseById(id)
				.orElseThrow(() -> new RuntimeException("Could not find Course with id: " + id));
	}
	
	//putMapping
	@PutMapping("/{id}")
	public CourseDTO updateCourse(@PathVariable Integer id, @RequestBody CourseDTO dto)
	{
		return gradeBookService.updateCourse(id, dto);
	}
	
	@PostMapping //create new course
	public CourseDTO createCourse(@RequestBody CourseDTO dto)
	{
		return gradeBookService.saveCourse(dto);
	}
	
	//deletemapping
	@DeleteMapping("/{id}")
	public void deleteCourse(@PathVariable Integer id)
	{
		gradeBookService.deleteCourse(id);
	}
}
