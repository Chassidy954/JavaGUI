//controllers are used for business logic and API endpoints

package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.repositories.SectionRepository;
import com.gradebookProject.gradebookbackend.entities.Section;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sections")
public class SectionController {
	@Autowired
	private SectionRepository sectionRepository;
	
	@GetMapping //return all sections
	public List<Section> getAllSections()
	{
		return sectionRepository.findAll();
	}
	
	@PostMapping //create a new section
	public Section createSection(@RequestBody Section section)
	{
		return sectionRepository.save(section);
	}
}
