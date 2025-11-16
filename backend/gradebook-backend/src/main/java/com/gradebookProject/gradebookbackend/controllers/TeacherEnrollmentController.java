//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.TeacherEnrollmentRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.StudentEnrollmentDTO;
import com.gradebookProject.gradebookbackend.dto.TeacherEnrollmentDTO;
import com.gradebookProject.gradebookbackend.entities.TeacherEnrollment;

import java.util.List;
import java.util.Optional;

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
	
	// Find all the enrollments for a given teacher
	@GetMapping("/teacher/{teacherId}")
	public List<TeacherEnrollmentDTO> getTeacherEnrollmentsById(@PathVariable Integer teacherId)
	{
		return gradeBookService.findEnrollmentsByTeacherId(teacherId);
	}
	
	//getmapping for id
	@GetMapping("/{id}")
	public TeacherEnrollmentDTO getTeacherEnrollmentById(@PathVariable Integer id)
	{
		return gradeBookService.findTeacherEnrollmentById(id)
				.orElseThrow(() -> new RuntimeException("Could not find Teacher Enrollment with id: " + id));
	}
	
	@PostMapping //creates a new teacher enrollment
	public TeacherEnrollmentDTO createTeacherEnrollment(@RequestBody TeacherEnrollmentDTO teacherEnrollment)
	{
		return gradeBookService.saveTeacherEnrollment(teacherEnrollment);
	}
	
	//deletemapping
	@DeleteMapping("/{id}")
	public void deleteTeacherEnrollment(@PathVariable Integer id)
	{
		gradeBookService.deleteTeacherEnrollment(id);
	}
}

	
	

	

