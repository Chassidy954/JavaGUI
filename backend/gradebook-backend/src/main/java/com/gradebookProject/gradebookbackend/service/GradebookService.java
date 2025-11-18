package com.gradebookProject.gradebookbackend.service;

import java.util.List;
import java.util.Optional;

import com.gradebookProject.gradebookbackend.dto.*;

public interface GradebookService {

	// student CRUD methods
	List<StudentDTO> findAllStudents();
	Optional<StudentDTO> findStudentById(Integer id);
	StudentDTO saveStudent(StudentDTO student);
	StudentDTO updateStudent(Integer studentId, StudentDTO dto);
	void deleteStudent(Integer id);
	
	// student enrollment CRUD methods
	List<StudentEnrollmentDTO> findAllStudentEnrollments();
	Optional<StudentEnrollmentDTO> findStudentEnrollmentById(Integer studentId);
	List<StudentEnrollmentDTO> findStudentEnrollmentsBySectionId(Integer sectionId);
	StudentEnrollmentDTO saveStudentEnrollment(StudentEnrollmentDTO dto);
	void deleteStudentEnrollment(Integer id);
	
	// attendance CRUD methods
	List<AttendanceDTO> findAllAttendance();
	Optional<AttendanceDTO> findAttendanceById(Integer id);
	List<AttendanceDTO> findAttendanceByStudentId(Integer studentId);
	AttendanceDTO saveAttendance(AttendanceDTO attendance);
	AttendanceDTO updateAttendance(Integer id, AttendanceDTO dto);
	void deleteAttendance(Integer attendanceId);
	
	// assignment CRUD methods
	List<AssignmentDTO> findAllAssignments();
	Optional<AssignmentDTO> findAssignmentById(Integer id);
	List<AssignmentDTO> findAssignmentsBySectionId(Integer id);
	AssignmentDTO saveAssignment(AssignmentDTO assignment);
	AssignmentDTO updateAssignment(Integer id, AssignmentDTO dto);
	void deleteAssignment(Integer assignmentId);
	
	// grade CRUD methods
	Optional<GradeDTO> findGradeById(Integer id);
	List<GradeDTO> findGradesByStudentId(Integer studentId);
	List<GradeDTO> findGradesByAssignment(Integer assignmentId);
	List<GradeDTO> findGradesBySection(Integer sectionId);
	Optional<GradeDTO> findGradeByStudentAndAssignment(Integer studentId, Integer assignmentId);
	GradeDTO submitGrade(Integer studentId, Integer assignmentId, Double score);
	GradeDTO updateGrade(Integer gradeId, Double score, String comments);
	void deleteGrade(Integer gradeId);
	
	// course CRUD methods
	List<CourseDTO> findAllCourses();
	Optional<CourseDTO> findCourseById(Integer courseId);
	CourseDTO saveCourse(CourseDTO course);
	CourseDTO updateCourse(Integer id, CourseDTO dto);
	void deleteCourse(Integer courseId);
	
	// section CRUD methods
	List<SectionDTO> findAllSections();
	Optional<SectionDTO> findSectionById(Integer sectionId);
	SectionDTO saveSection(SectionDTO section);
	SectionDTO updateSection(Integer id, SectionDTO dto);
	void deleteSection(Integer sectionId);
	
	// teacher CRUD methods
	List<TeacherDTO> findAllTeachers();
	Optional<TeacherDTO> findTeacherById(Integer teacherId);
	TeacherDTO saveTeacher(TeacherDTO teacher);
	TeacherDTO updateTeacher(Integer id, TeacherDTO dto);
	void deleteTeacher(Integer teacherId);
	
	// teacher enrollment CRUD methods
	List<TeacherEnrollmentDTO> findAllTeacherEnrollments();
	Optional<TeacherEnrollmentDTO> findTeacherEnrollmentById(Integer teacherId);
	List<TeacherEnrollmentDTO> findEnrollmentsByTeacherId(Integer teacherId);
	TeacherEnrollmentDTO saveTeacherEnrollment(TeacherEnrollmentDTO dto);
	void deleteTeacherEnrollment(Integer teacherId);

	// analytical methods
	Double calculateAssignmentAverage(Integer assignmentId);
	Double calculateStudentAverage(Integer studentId);
	
}
