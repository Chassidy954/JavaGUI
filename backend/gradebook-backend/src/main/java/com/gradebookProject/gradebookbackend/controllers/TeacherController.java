//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.TeacherRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.SectionDTO;
import com.gradebookProject.gradebookbackend.dto.TeacherDTO;
import com.gradebookProject.gradebookbackend.entities.Teacher;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	
	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping
	public List<TeacherDTO> getAllTeachers()
	{
		return gradeBookService.findAllTeachers();
	}
	
	
	//getmapping for id
	@GetMapping("/{id}")
	public TeacherDTO getById(@PathVariable Integer id)
	{
		return gradeBookService.findTeacherById(id)
				.orElseThrow(() -> new RuntimeException("Could not find Teacher with id: " + id));
	}
	
	
	@PostMapping
	public TeacherDTO createTeacher(@RequestBody TeacherDTO teacher)
	{
		return gradeBookService.saveTeacher(teacher);
	}
	
	
	//putMapping
	@PutMapping("/{id}")
	public TeacherDTO updateTeacher(@PathVariable Integer id, @RequestBody TeacherDTO dto)
	{
		return gradeBookService.updateTeacher(id, dto);
	}
	
	
	//deletemapping
	@DeleteMapping("/{id}")
	public void deleteTeacher(@PathVariable Integer id)
	{
		gradeBookService.deleteTeacher(id);
	}
}
	


