//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.SectionRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.CourseDTO;
import com.gradebookProject.gradebookbackend.dto.SectionDTO;
import com.gradebookProject.gradebookbackend.entities.Section;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sections")
public class SectionController {
	
	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping //return all sections
	public List<SectionDTO> getAllSections()
	{
		return gradeBookService.findAllSections();
	}
	
	//getmapping for id
	@GetMapping("/{id}")
	public SectionDTO getById(@PathVariable Integer id)
	{
		return gradeBookService.findSectionById(id)
				.orElseThrow(() -> new RuntimeException("Could not find Section with id: " + id));
	}
	
	@PostMapping //create a new section
	public SectionDTO createSection(@RequestBody SectionDTO dto)
	{
		return gradeBookService.saveSection(dto);
	}
	
	//putMapping
	@PutMapping("/{id}")
	public SectionDTO updateSection(@PathVariable Integer id, @RequestBody SectionDTO dto)
	{
		return gradeBookService.updateSection(id, dto);
	}
	
	//deletemapping
	@DeleteMapping("/{id}")
	public void deleteSection(@PathVariable Integer id)
	{
		gradeBookService.deleteSection(id);
	}
}