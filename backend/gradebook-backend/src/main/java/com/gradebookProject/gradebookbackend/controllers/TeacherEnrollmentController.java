//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.TeacherEnrollmentRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.TeacherEnrollmentDTO;
import com.gradebookProject.gradebookbackend.entities.TeacherEnrollment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/teacherenrollments")
public class TeacherEnrollmentController {
	
	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping //returns all teacher enrollments
	public List<TeacherEnrollmentDTO> getAllTeacherEnrollment()
	{
		return gradeBookService.findAllTeacherEnrollments();
	}
	
	@PostMapping //creates a new teacher enrollment
	public TeacherEnrollmentDTO createTeacherEnrollment(@RequestBody TeacherEnrollmentDTO teacherEnrollment)
	{
		return gradeBookService.saveTeacherEnrollment(teacherEnrollment);
	}
}
