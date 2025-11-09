package com.gradebookProject.gradebookbackend.controllers;

import com.gradebookProject.gradebookbackend.entities.Attendance;
import com.gradebookProject.gradebookbackend.repositories.AttendanceRepository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@GetMapping
	public List<Attendance> getAllAttendances()
	{
		return attendanceRepository.findAll();
	}
	
	@PostMapping
	public Attendance createAttendance(@RequestBody Attendance attendance)
	{
		return attendanceRepository.save(attendance);
	}
}
