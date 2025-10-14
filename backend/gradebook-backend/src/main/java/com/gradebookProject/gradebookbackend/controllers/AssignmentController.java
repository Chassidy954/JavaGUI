//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.entities.Assignment;
import com.gradebookProject.gradebookbackend.repositories.AssignmentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@GetMapping //get all assignments
	public List<Assignment> getAllAssignments()
	{
		return assignmentRepository.findAll();
	}
	
	@PostMapping //create new assignment
	public Assignment createAssignment(@RequestBody Assignment assignment)
	{
		return assignmentRepository.save(assignment);
	}
	
}
