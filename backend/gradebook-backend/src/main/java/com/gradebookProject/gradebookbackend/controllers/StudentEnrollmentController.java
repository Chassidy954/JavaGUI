//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.StudentEnrollmentRepository;
import com.gradebookProject.gradebookbackend.entities.StudentEnrollment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/student-enrollments")
public class StudentEnrollmentController {
	@Autowired
	private StudentEnrollmentRepository studentEnrollmentRepository;
	
	@GetMapping //returns all student enrollments
	public List<StudentEnrollment> getAllStudentEnrollment()
	{
		return studentEnrollmentRepository.findAll();
	}
	
	@PostMapping //creates a new student enrollment
	public StudentEnrollment createStudentEnrollment(@RequestBody StudentEnrollment studentEnrollment)
	{
		return studentEnrollmentRepository.save(studentEnrollment);
	}
}
