//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.StudentEnrollmentRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.StudentEnrollmentDTO;
import com.gradebookProject.gradebookbackend.entities.StudentEnrollment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/studentenrollments")
public class StudentEnrollmentController {
	
	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping //returns all student enrollments
	public List<StudentEnrollmentDTO> getAllStudentEnrollment()
	{
		return gradeBookService.findAllStudentEnrollments();
	}
	
	@GetMapping("/sections/{sectionId}")
	public List<StudentEnrollmentDTO> getEnrollmentsForSection(@PathVariable Integer sectionId)
	{
		return gradeBookService.findStudentEnrollmentsBySectionId(sectionId);
	}
	
	@PostMapping //creates a new student enrollment
	public StudentEnrollmentDTO createStudentEnrollment(@RequestBody StudentEnrollmentDTO studentEnrollment)
	{
		return gradeBookService.saveStudentEnrollment(studentEnrollment);
	}
}
