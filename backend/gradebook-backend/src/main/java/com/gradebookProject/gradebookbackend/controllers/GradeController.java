//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.GradeDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/grades")
public class GradeController {

	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping("/student/{studentId}") // returns all grades for a student
	public List<GradeDTO> findGradesByStudent(@PathVariable Integer studentId)
	{
		return gradeBookService.findGradesByStudentId(studentId);
	}
	
	@GetMapping("/assignment/{assignmentId}") // returns all grades for an assignment
	public List<GradeDTO> findGradesByAssignment(@PathVariable Integer assignmentId)
	{
		return gradeBookService.findGradesByAssignment(assignmentId);
	}
	
	@GetMapping("/section/{sectionId}") // returns all grades for a section
	public List<GradeDTO> findGradesBySection(@PathVariable Integer sectionId)
	{
		return gradeBookService.findGradesBySection(sectionId);
	}
	@PostMapping //creates a new grade
	public GradeDTO createGrade(@RequestBody GradeDTO grade)
	{
		return gradeBookService.submitGrade(
				grade.getStudentId(),
				grade.getAssignmentId(),
				grade.getScore()
		);
	}
}
