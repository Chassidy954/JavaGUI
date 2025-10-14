//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.TeacherRepository;
import com.gradebookProject.gradebookbackend.entities.Teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	@Autowired
	private TeacherRepository teacherRepository;
	
	@GetMapping
	public List<Teacher> getAllTeachers()
	{
		return teacherRepository.findAll();
	}
	
	@PostMapping
	public Teacher createTeacher(@RequestBody Teacher teacher)
	{
		return teacherRepository.save(teacher);
	}
}
