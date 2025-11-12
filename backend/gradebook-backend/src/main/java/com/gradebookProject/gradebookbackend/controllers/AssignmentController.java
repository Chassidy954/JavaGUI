//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.dto.AssignmentDTO;
import com.gradebookProject.gradebookbackend.service.GradebookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping //get all assignments
	public List<AssignmentDTO> getAllAssignments()
	{
		return gradeBookService.findAllAssignments();
	}
	
	@PostMapping //create new assignment
	public AssignmentDTO createAssignment(@RequestBody AssignmentDTO dto)
	{
		return gradeBookService.saveAssignment(dto);
	}
	
}
