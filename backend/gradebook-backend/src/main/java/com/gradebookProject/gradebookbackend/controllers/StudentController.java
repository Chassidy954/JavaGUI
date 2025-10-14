//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.repositories.StudentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping //get all students
	public List<Student> getAllStudents()
	{
		return studentRepository.findAll();
	}
	
	@PostMapping //create new student
	public Student createStudent(@RequestBody Student student)
	{
		return studentRepository.save(student);
	}
	
}
