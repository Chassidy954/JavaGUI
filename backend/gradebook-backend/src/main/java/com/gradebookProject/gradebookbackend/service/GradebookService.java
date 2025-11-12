package com.gradebookProject.gradebookbackend.service;

import java.util.List;
import java.util.Optional;

import com.gradebookProject.gradebookbackend.dto.*;

public interface GradebookService {

	// student methods
	List<StudentDTO> findAllStudents();
	Optional<StudentDTO> findStudentById(Integer id);
	StudentDTO saveStudent(StudentDTO student);
	void deleteStudent(Integer id);
	
	// student enrollment methods
	StudentEnrollmentDTO saveStudentEnrollment(StudentEnrollmentDTO dto);
	void deleteStudentEnrollment(Integer id);
	List<StudentEnrollmentDTO> findAllStudentEnrollments();
	Optional<StudentEnrollmentDTO> findStudentEnrollmentById(Integer studentId);
	StudentDTO updateStudent(Integer studentId, StudentDTO dto);
	
	// attendance methods
	List<AttendanceDTO> findAllAttendance();
	List<AttendanceDTO> findAttendanceByStudentId(Integer studentId);
	AttendanceDTO saveAttendance(AttendanceDTO attendance);
	void deleteAttendance(Integer attendanceId);
	
	// assignment methods
	List<AssignmentDTO> findAllAssignments();
	AssignmentDTO saveAssignment(AssignmentDTO assignment);
	void deleteAssignment(Integer assignmentId);
	Optional<AssignmentDTO> findAssignmentById(Integer assignmentId);
	
	// grade methods
	GradeDTO submitGrade(Integer studentId, Integer assignmentId, Double score);
	List<GradeDTO> findGradesByStudentId(Integer studentId);
	List<GradeDTO> findGradesByAssignment(Integer assignmentId);
	List<GradeDTO> findGradesBySection(Integer sectionId);
	Optional<GradeDTO> findGradeByStudentAndAssignment(Integer studentId, Integer assignmentId);
	GradeDTO updateGrade(Integer gradeId, Double score, String comments);
	void deleteGrade(Integer gradeId);
	
	// course methods
	CourseDTO saveCourse(CourseDTO course);
	void deleteCourse(Integer courseId);
	List<CourseDTO> findAllCourses();
	Optional<CourseDTO> findCourseById(Integer courseId);
	
	// section methods
	SectionDTO saveSection(SectionDTO section);
	void deleteSection(Integer sectionId);
	List<SectionDTO> findAllSections();
	Optional<SectionDTO> findSectionById(Integer sectionId);
	
	// teacher methods
	TeacherDTO saveTeacher(TeacherDTO teacher);
	void deleteTeacher(Integer teacherId);
	List<TeacherDTO> findAllTeachers();
	Optional<TeacherDTO> findTeacherById(Integer teacherId);
	
	// teacher enrollment methods
	TeacherEnrollmentDTO saveTeacherEnrollment(TeacherEnrollmentDTO dto);
	void deleteTeacherEnrollment(Integer teacherId);
	List<TeacherEnrollmentDTO> findAllTeacherEnrollments();
	Optional<TeacherEnrollmentDTO> findTeacherEnrollmentById(Integer teacherId);

	// analytical methods
	Double calculateAssignmentAverage(Integer assignmentId);
	Double calculateStudentAverage(Integer studentId);
	
}
