//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.SectionRepository;
import com.gradebookProject.gradebookbackend.service.GradebookService;
import com.gradebookProject.gradebookbackend.dto.SectionDTO;
import com.gradebookProject.gradebookbackend.entities.Section;

import java.util.List;

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
	
	@PostMapping //create a new section
	public SectionDTO createSection(@RequestBody SectionDTO dto)
	{
		return gradeBookService.saveSection(dto);
	}
}
