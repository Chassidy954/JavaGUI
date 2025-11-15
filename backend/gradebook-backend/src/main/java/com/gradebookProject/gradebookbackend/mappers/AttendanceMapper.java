package com.gradebookProject.gradebookbackend.mappers;

import com.gradebookProject.gradebookbackend.entities.Attendance;
import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.entities.Section;
import com.gradebookProject.gradebookbackend.dto.AttendanceDTO;
import com.gradebookProject.gradebookbackend.repositories.StudentRepository;
import com.gradebookProject.gradebookbackend.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	SectionRepository sectionRepository;
	
	public AttendanceDTO convertToDTO(Attendance attendance)
	{
		AttendanceDTO dto = new AttendanceDTO();
		dto.setId(attendance.getId());
		dto.setSectionId(attendance.getSection().getSectionId());
		dto.setSectionName(attendance.getSection().getSectionName());
		dto.setStatus(attendance.getStatus());
		dto.setStudentId(attendance.getStudent().getStudentId());
		dto.setStudentName(attendance.getStudent().getFirstName() + " " + attendance.getStudent().getLastName());
		dto.setAttendanceDate(attendance.getAttendanceDate());
		return dto;
	}
	
	public Attendance convertToEntity(AttendanceDTO dto)
	{
		Attendance attendance = new Attendance();
		Section section = sectionRepository.findById(dto.getSectionId())
				.orElseThrow(() -> new RuntimeException("Couldn't find section with id: " + dto.getSectionId()));
		attendance.setSection(section);
		
		Student student = studentRepository.findById(dto.getStudentId())
				.orElseThrow(() -> new RuntimeException("Couldn't find student with id: " + dto.getStudentId()));
		attendance.setStudent(student);
		attendance.setAttendanceDate(dto.getAttendanceDate());
		attendance.setStatus(dto.getStatus());
		return attendance;
	}
}
