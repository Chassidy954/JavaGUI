package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.TeacherRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gradebookProject.gradebookbackend.entities.Teacher;


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
