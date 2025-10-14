//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.GradeRepository;
import com.gradebookProject.gradebookbackend.entities.Grade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/grades")
public class GradeController {
	@Autowired
	private GradeRepository gradeRepository;
	
	@GetMapping //returns all grades
	public List<Grade> getAllGrades()
	{
		return gradeRepository.findAll();
	}
	
	@PostMapping //creates a new grade
	public Grade createGrade(@RequestBody Grade grade)
	{
		return gradeRepository.save(grade);
	}
}
