//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.CourseDTO;
import com.gradebookProject.gradebookbackend.dto.GradeDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/grades")
public class GradeController {

	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping("/{id}")
	public GradeDTO getGradeById(@PathVariable Integer id)
	{
		return gradeBookService.findGradeById(id)
				.orElseThrow(() -> new RuntimeException("Could not find grade with id: " + id));
	}
	
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
	
	
	//getmapping for specific studentid and assignmentid
	@GetMapping("/student/{studentId}/assignment/{assignmentId}")
    public GradeDTO getByStudentAndAssignment
    		(	
	            @PathVariable Integer studentId, 
	            @PathVariable Integer assignmentId
    		)
    {
        return gradeBookService.findGradeByStudentAndAssignment(studentId, assignmentId)
                .orElseThrow(() -> new RuntimeException(
                    "Could not find grade for student " + studentId + " and assignment " + assignmentId));
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
	
	//putMapping
	@PutMapping("/{id}")
	public GradeDTO updateGrade(@PathVariable Integer id, @RequestBody GradeDTO grade)
	{
		return gradeBookService.updateGrade(
				id,
				grade.getScore(),
				grade.getComments()
		);
	}

	
	//deletemapping
	@DeleteMapping("/{id}")
	public void deleteGrade(@PathVariable Integer id)
	{
		gradeBookService.deleteGrade(id);
	}
}

	

	
	