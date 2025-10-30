//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.TeacherEnrollmentRepository;
import com.gradebookProject.gradebookbackend.entities.TeacherEnrollment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/teacherenrollments")
public class TeacherEnrollmentController {
	@Autowired
	private TeacherEnrollmentRepository teacherEnrollmentRepository;
	
	@GetMapping //returns all teacher enrollments
	public List<TeacherEnrollment> getAllTeacherEnrollment()
	{
		return teacherEnrollmentRepository.findAll();
	}
	
	@PostMapping //creates a new teacher enrollment
	public TeacherEnrollment createTeacherEnrollment(@RequestBody TeacherEnrollment teacherEnrollment)
	{
		return teacherEnrollmentRepository.save(teacherEnrollment);
	}
}
