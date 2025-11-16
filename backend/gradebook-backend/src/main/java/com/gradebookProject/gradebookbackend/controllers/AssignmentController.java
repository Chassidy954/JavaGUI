//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.dto.AssignmentDTO;
import com.gradebookProject.gradebookbackend.dto.StudentDTO;
import com.gradebookProject.gradebookbackend.service.GradebookService;

import java.util.List;
import java.util.Optional;

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
	
	//getmapping for id
	@GetMapping("/{id}")
	public AssignmentDTO getById(@PathVariable Integer id)
	{
		return gradeBookService.findAssignmentById(id)
				.orElseThrow(() -> new RuntimeException("Could not find Assignment with id: " + id));
	}
	
	//putMapping
	@PutMapping("/{id}")
	public AssignmentDTO updateAssignment(@PathVariable Integer id, @RequestBody AssignmentDTO dto)
	{
		return gradeBookService.updateAssignment(id, dto);
	}
	
	@PostMapping //create new assignment
	public AssignmentDTO createAssignment(@RequestBody AssignmentDTO dto)
	{
		return gradeBookService.saveAssignment(dto);
	}
	
	//deletemapping
	@DeleteMapping("/{id}")
	public void deleteAssignment(@PathVariable Integer id)
	{
		gradeBookService.deleteAssignment(id);
	}
}
