package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.dto.AssignmentDTO;
import com.gradebookProject.gradebookbackend.dto.AttendanceDTO;
import com.gradebookProject.gradebookbackend.service.GradebookService;

import java.util.List;
import java.util.Optional;

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
	
	//getmapping for id
	@GetMapping("/{id}")
	public AttendanceDTO getById(@PathVariable Integer id)
	{
		return gradeBookService.findAttendanceById(id)
				.orElseThrow(() -> new RuntimeException("Could not find Attendance with id: " + id));
	}
	
	// Get by student ID - DIFFERENT PATH
	@GetMapping("/student/{studentId}")
	public List<AttendanceDTO> getByStudentId(@PathVariable Integer studentId)
	{
	    return gradeBookService.findAttendanceByStudentId(studentId);
	}
	
	//putmapping 
	@PutMapping("/{id}")
	public AttendanceDTO updateAttendance(@PathVariable Integer id, @RequestBody AttendanceDTO dto)
	{
		return gradeBookService.updateAttendance(id, dto);
	}
	
	@PostMapping
	public AttendanceDTO createAttendance(@RequestBody AttendanceDTO dto)
	{
		return gradeBookService.saveAttendance(dto);
	}
	
	//deletemapping
	@DeleteMapping("/{id}")
	public void deleteAttendance(@PathVariable Integer id)
	{
		gradeBookService.deleteAttendance(id);
	}
}

