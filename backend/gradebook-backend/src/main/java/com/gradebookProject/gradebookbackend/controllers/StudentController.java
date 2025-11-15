//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.dto.StudentDTO;
import com.gradebookProject.gradebookbackend.service.GradebookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/students")
public class StudentController {

	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping //get all students
	public List<StudentDTO> getAllStudents()
	{
		return gradeBookService.findAllStudents();
	}
	
	@GetMapping("/{id}")
	public StudentDTO getStudentById(@PathVariable Integer id)
	{
		return gradeBookService.findStudentById(id)
				.orElseThrow(() -> new RuntimeException("Could not find student with id: " + id));
	}
	
	@PutMapping("/{id}")
	public StudentDTO updateStudent(@PathVariable Integer id, @RequestBody StudentDTO dto)
	{
		return gradeBookService.updateStudent(id, dto);
	}
	
	@PostMapping //create new student
	public StudentDTO createStudent(@RequestBody StudentDTO dto)
	{
		return gradeBookService.saveStudent(dto);
	}
	
	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable Integer id)
	{
		gradeBookService.deleteStudent(id);
	}
	
}
