package com.gradebookProject.gradebookbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	List<Attendance> findByStudentStudentId(Integer studentId);
}
