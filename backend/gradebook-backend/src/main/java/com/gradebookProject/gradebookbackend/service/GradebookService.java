package com.gradebookProject.gradebookbackend.service;

import java.util.List;
import java.util.Optional;

import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.entities.Grade;
import com.gradebookProject.gradebookbackend.entities.Assignment;
import com.gradebookProject.gradebookbackend.entities.AssignmentId;

public interface GradebookService {

	//student methods
	List<Student> findAllStudents();
	Optional<Student> findStudentById(Integer id);
	Student saveStudent(Student student);
	void deleteStudent(Integer id);
	
	//assignment methods
	List<Assignment> findAllAssignments();
	Assignment saveAssignment(Assignment assignment);
	void deleteAssignment(AssignmentId assignmentId);
	Optional<Assignment> findAssignmentById(AssignmentId assignmentId);
	
	//grade methods
	Grade submitGrade(Integer studentId, AssignmentId assignmentId, Double score);
	List<Grade> findGradesByStudentId(Integer studentId);
	List<Grade> findGradesByAssignment(AssignmentId assignmentId);
	Optional<Grade> findGradeByStudentAndAssignment(Integer studentId, AssignmentId assignmentId);
	Grade updateGrade(Long gradeId, Double score, String comments);
	void deleteGrade(Long gradeId);

	//analytical methods
	Double calculateAssignmentAverage(AssignmentId assignmentId);
	Double calculateStudentAverage(Integer studentId);
	
}
