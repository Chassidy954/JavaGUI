package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.dto.AttendanceDTO;
import com.gradebookProject.gradebookbackend.service.GradebookService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
	
	@Autowired
	private GradebookService gradeBookService;
	
	@GetMapping
	public List<AttendanceDTO> getAllAttendances()
	{
		return gradeBookService.findAllAttendance();
	}
	
	@PostMapping
	public AttendanceDTO createAttendance(@RequestBody AttendanceDTO dto)
	{
		return gradeBookService.saveAttendance(dto);
	}
}
