package com.gradebookProject.gradebookbackend.service;

import java.util.List;
import java.util.Optional;

import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.entities.Attendance;
import com.gradebookProject.gradebookbackend.entities.Grade;
import com.gradebookProject.gradebookbackend.entities.Assignment;

public interface GradebookService {

	// student methods
	List<Student> findAllStudents();
	Optional<Student> findStudentById(Integer id);
	Student saveStudent(Student student);
	void deleteStudent(Integer id);
	
	// attendance methods
	List<Attendance> findAttendanceByStudentId(Integer studentId);
	
	// assignment methods
	List<Assignment> findAllAssignments();
	Assignment saveAssignment(Assignment assignment);
	void deleteAssignment(Integer assignmentId);
	Optional<Assignment> findAssignmentById(Integer assignmentId);
	
	// grade methods
	Grade submitGrade(Integer studentId, Integer assignmentId, Double score);
	List<Grade> findGradesByStudentId(Integer studentId);
	List<Grade> findGradesByAssignment(Integer assignmentId);
	Optional<Grade> findGradeByStudentAndAssignment(Integer studentId, Integer assignmentId);
	Grade updateGrade(Integer gradeId, Double score, String comments);
	void deleteGrade(Integer gradeId);

	// analytical methods
	Double calculateAssignmentAverage(Integer assignmentId);
	Double calculateStudentAverage(Integer studentId);
	
}
