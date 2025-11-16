package com.gradebookProject.gradebookbackend.service;

import com.gradebookProject.gradebookbackend.dto.*;
import com.gradebookProject.gradebookbackend.mappers.*;
import com.gradebookProject.gradebookbackend.entities.*;
import com.gradebookProject.gradebookbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradebookServiceImpl implements GradebookService {

	// autowired applies dependencies where labeled to so, student, assignment, and grade repo
	// Repositories
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private SectionRepository sectionRepository;
	@Autowired
	private StudentEnrollmentRepository studentEnrollmentRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private TeacherEnrollmentRepository teacherEnrollmentRepository;
	
	// Mappers
	@Autowired
	private AssignmentMapper assignmentMapper;
	@Autowired
	private AttendanceMapper attendanceMapper;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private SectionMapper sectionMapper;
	@Autowired 
	private StudentMapper studentMapper;
	@Autowired
	private StudentEnrollmentMapper studentEnrollmentMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private TeacherEnrollmentMapper teacherEnrollmentMapper;
	
	//students
	
	@Override
	public List<StudentDTO> findAllStudents() {
		return studentRepository.findAll().stream()
				.map(studentMapper::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<StudentDTO> findStudentById(Integer id) {
		return studentRepository.findById(id)
				.map(studentMapper::convertToDTO);
	}

	@Override
	public StudentDTO saveStudent(StudentDTO dto) {
		Student student = studentMapper.convertToEntity(dto);
		if (student.getFirstName() == null || student.getFirstName().trim().isEmpty())
			throw new IllegalArgumentException("First name is required!");
		else if (student.getLastName() == null || student.getLastName().trim().isEmpty())
			throw new IllegalArgumentException("Last name is required!");
		
		student = studentRepository.save(student);
		return studentMapper.convertToDTO(student);
	}

	@Override
	public void deleteStudent(Integer id) {
		if(!studentRepository.existsById(id))
		{
			throw new RuntimeException("Student not found with id: " + id);
		}
		studentRepository.deleteById(id);
	}
	
	@Override
	public StudentDTO updateStudent(Integer id, StudentDTO dto)
	{
		if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
	        throw new IllegalArgumentException("First name is required!");
	    }
	    if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
	        throw new IllegalArgumentException("Last name is required!");
	    }
		
		Student existingStudent = studentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Could not find student with id: " + id));
		existingStudent.setFirstName(dto.getFirstName());
		existingStudent.setLastName(dto.getLastName());
		existingStudent = studentRepository.save(existingStudent);
		return studentMapper.convertToDTO(existingStudent);
		
	}
	
	// student enrollment methods
	@Override
	public StudentEnrollmentDTO saveStudentEnrollment(StudentEnrollmentDTO dto)
	{
		if (dto.getSectionId() == null || dto.getStudentId() == null)
			throw new IllegalArgumentException("Invalid Section/Student ID on dto");
		
		StudentEnrollment enroll = studentEnrollmentMapper.convertToEntity(dto);
		enroll = studentEnrollmentRepository.save(enroll);
		return studentEnrollmentMapper.convertToDTO(enroll);
	}
	
	@Override
	public void deleteStudentEnrollment(Integer enrollmentId)
	{
		if (!studentEnrollmentRepository.existsById(enrollmentId))
			throw new RuntimeException("Could not find student enrollment with id: " + enrollmentId);
		studentEnrollmentRepository.deleteById(enrollmentId);
	}
	
	@Override
	public List<StudentEnrollmentDTO> findAllStudentEnrollments()
	{
		return studentEnrollmentRepository.findAll().stream()
				.map(studentEnrollmentMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<StudentEnrollmentDTO> findStudentEnrollmentById(Integer enrollmentId)
	{
		return studentEnrollmentRepository.findById(enrollmentId)
				.map(studentEnrollmentMapper::convertToDTO);
	}
	
	@Override
	public List<StudentEnrollmentDTO> findStudentEnrollmentsBySectionId(Integer sectionId)
	{
		return studentEnrollmentRepository.findBySectionSectionId(sectionId).stream()
			.map(studentEnrollmentMapper::convertToDTO)
			.collect(Collectors.toList());
	}
	
	
	// attendance
	@Override
	public List<AttendanceDTO> findAllAttendance()
	{
		return attendanceRepository.findAll().stream()
				.map(attendanceMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<AttendanceDTO> findAttendanceByStudentId(Integer studentId)
	{
		return attendanceRepository.findByStudentStudentId(studentId).stream()
				.map(attendanceMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public Optional<AttendanceDTO> findAttendanceById(Integer id) 
	{
	    return attendanceRepository.findById(id)
	            .map(attendanceMapper::convertToDTO);
	}	
	
	@Override
	public AttendanceDTO saveAttendance(AttendanceDTO dto)
	{
		Attendance attendance = attendanceMapper.convertToEntity(dto);
		if (attendance.getAttendanceDate() == null)
			throw new IllegalArgumentException("Attendance must have a date!");
		
		attendance = attendanceRepository.save(attendance);
		return attendanceMapper.convertToDTO(attendance);
	}
	
	@Override
	public AttendanceDTO updateAttendance(Integer id, AttendanceDTO dto) 
	{
		if (dto.getAttendanceDate() == null) 
		{
	        throw new IllegalArgumentException("Attendance date is required!");
	    }
	    if (dto.getStatus() == null || dto.getStatus().trim().isEmpty())
	    {
	        throw new IllegalArgumentException("Attendance status is required!");
	    }
		
	    Attendance existingAttendance = attendanceRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Could not find attendance with id: " + id));
	    
	    existingAttendance.setAttendanceDate(dto.getAttendanceDate());
	    existingAttendance.setStatus(dto.getStatus());
	    
	    existingAttendance = attendanceRepository.save(existingAttendance);
	    return attendanceMapper.convertToDTO(existingAttendance);
	}
	
	@Override
	public void deleteAttendance(Integer attendanceId)
	{
		if (!attendanceRepository.existsById(attendanceId))
			throw new RuntimeException("Could not find attendance with id: " + attendanceId);
		
		attendanceRepository.deleteById(attendanceId);
	}

	
	//assignments
	
	@Override
	public List<AssignmentDTO> findAllAssignments() {
		return assignmentRepository.findAll().stream()
			.map(assignmentMapper::convertToDTO)
			.collect(Collectors.toList());
	}

	@Override
	public AssignmentDTO saveAssignment(AssignmentDTO dto) {
		Assignment assignment = assignmentMapper.convertToEntity(dto);
		if (dto.getSectionId() == null)
			throw new IllegalArgumentException("Section ID is required!");
		else if (assignment.getAssignmentName() == null || assignment.getAssignmentName().trim().isEmpty())
			throw new IllegalArgumentException("Assignment name is required!");
		else if (assignment.getMaxScore() == null || assignment.getMaxScore() <= 0)
			throw new IllegalArgumentException("Maximum score must be greater than 0!");
		
		assignment = assignmentRepository.save(assignment);
		
		return assignmentMapper.convertToDTO(assignment);
	}
	
	@Override
    public void deleteAssignment(Integer assignmentId) 
	{
        //check if assignment exists first
        if (!assignmentRepository.existsById(assignmentId)) 
        {
            throw new RuntimeException("Assignment not found " + assignmentId);
        }
        assignmentRepository.deleteById(assignmentId); 
    }
	
	@Override
    public Optional<AssignmentDTO> findAssignmentById(Integer assignmentId) {
		return assignmentRepository.findById(assignmentId)
				.map(assignmentMapper::convertToDTO);
	}
	
	@Override
	public AssignmentDTO updateAssignment(Integer id, AssignmentDTO dto) {
		if (dto.getAssignmentName() == null || dto.getAssignmentName().trim().isEmpty()) 
		{
	        throw new IllegalArgumentException("Assignment name is required!");
	    }
	    if (dto.getMaxScore() == null || dto.getMaxScore() <= 0) 
	    {
	        throw new IllegalArgumentException("Maximum score must be greater than 0!");
	    }
		
		Assignment existingAssignment = assignmentRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Could not find assignment with id: " + id));
	    
	    existingAssignment.setAssignmentName(dto.getAssignmentName());
	    existingAssignment.setMaxScore(dto.getMaxScore());
	    
	    existingAssignment = assignmentRepository.save(existingAssignment);
	    return assignmentMapper.convertToDTO(existingAssignment);
	}
	
	//grades

	@Override
	public Optional<GradeDTO> findGradeById(Integer id) 
	{
	    return gradeRepository.findById(id)
	            .map(gradeMapper::convertToDTO);
	}
	
	@Override
	public Optional<GradeDTO> findGradeByStudentAndAssignment(Integer studentId, Integer assignmentId) 
	{
		return gradeRepository.findByStudentStudentIdAndAssignmentId(studentId, assignmentId)
				.map(gradeMapper::convertToDTO);

	}

	@Override
	public GradeDTO updateGrade(Integer gradeId, Double score, String comments) 
	{
		
		Grade grade = gradeRepository.findById(gradeId)
				.orElseThrow(() -> new RuntimeException("Grade not found with id: " + gradeId));
		if (score < 0)
			throw new IllegalArgumentException("Score cannot be negative!");
		else if (grade.getAssignment().getMaxScore() != null && score > grade.getAssignment().getMaxScore())
			throw new IllegalArgumentException("Score: " + score + " cannot exceed max score: " + grade.getAssignment().getMaxScore());
		
		grade.setScore(score);
		
		if (comments != null)
			grade.setComments(comments);
		
		grade = gradeRepository.save(grade);
		
		return gradeMapper.convertToDTO(grade);
	}

	@Override
	public void deleteGrade(Integer gradeId) {
		if (!gradeRepository.existsById(gradeId)) {
	        throw new RuntimeException("Grade not found with id: " + gradeId);
	    }
	    gradeRepository.deleteById(gradeId);
	}

	@Override
	public List<GradeDTO> findGradesByAssignment(Integer assignmentId) {
		return gradeRepository.findByAssignmentId(assignmentId).stream()
				.map(gradeMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<GradeDTO> findGradesBySection(Integer sectionId) {
		return gradeRepository.findByAssignmentSectionSectionId(sectionId).stream()
				.map(gradeMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public GradeDTO submitGrade(Integer studentId, Integer assignmentId, Double score) {
		
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Cannot find student with id: " + studentId));
		Assignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new RuntimeException("Cannot find assignment with id: " + assignmentId));
		
		if (score < 0)
			throw new IllegalArgumentException("Score cannot be negative!");
		else if (assignment.getMaxScore() != null && score > assignment.getMaxScore())
			throw new IllegalArgumentException("Score: " + score + " cannot exceed max score: " + assignment.getMaxScore());
		
		Optional<Grade> existingGrade = gradeRepository.findByStudentStudentIdAndAssignmentId(studentId, assignmentId);
		Grade newGrade;
		if (existingGrade.isPresent())
		{
			newGrade = existingGrade.get();
			newGrade.setScore(score);
		}
		else
		{
			newGrade = new Grade();
			newGrade.setStudent(student);
			newGrade.setAssignment(assignment);
			newGrade.setScore(score);
		}
		
		newGrade = gradeRepository.save(newGrade);
		
		return gradeMapper.convertToDTO(newGrade);
	}

	@Override
	public List<GradeDTO> findGradesByStudentId(Integer studentId) {
		return gradeRepository.findByStudentStudentId(studentId).stream()
				.map(gradeMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	// courses
	@Override
	public CourseDTO saveCourse(CourseDTO dto)
	{
		Course course = courseMapper.convertToEntity(dto);
		if (course.getCourseName() == null)
			throw new IllegalArgumentException("Course name must be defined!");
		course = courseRepository.save(course);
		return courseMapper.convertToDTO(course);
	}
	
	@Override
	public void deleteCourse(Integer courseId)
	{
	    if (!courseRepository.existsById(courseId))
	    	throw new RuntimeException("Course not found with id: " + courseId);
	    courseRepository.deleteById(courseId);
	}
	
	@Override
	public List<CourseDTO> findAllCourses()
	{
		return courseRepository.findAll().stream()
				.map(courseMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<CourseDTO> findCourseById(Integer courseId)
	{
		return courseRepository.findById(courseId)
				.map(courseMapper::convertToDTO);
	}
	
	@Override
	public CourseDTO updateCourse(Integer id, CourseDTO dto) 
	{
	    if (dto.getCourseName() == null || dto.getCourseName().trim().isEmpty()) 
	    {
	        throw new IllegalArgumentException("Course name is required!");
	    }
	    
	    Course existingCourse = courseRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Could not find course with id: " + id));
	    
	    existingCourse.setCourseName(dto.getCourseName());
	    
	    existingCourse = courseRepository.save(existingCourse);
	    return courseMapper.convertToDTO(existingCourse);
	}
	
	//sections
	
	@Override
	public SectionDTO saveSection(SectionDTO dto)
	{
		Section section = sectionMapper.convertToEntity(dto);
		if (section.getSectionName() == null)
			throw new IllegalArgumentException("Section name must be defined!");
		section = sectionRepository.save(section);
		return sectionMapper.convertToDTO(section);
	}
	
	@Override
	public void deleteSection(Integer sectionId)
	{
		if (!sectionRepository.existsById(sectionId))
			throw new RuntimeException("Section not found with id: " + sectionId);
		sectionRepository.deleteById(sectionId);
	}
	
	@Override
	public List<SectionDTO> findAllSections()
	{
		return sectionRepository.findAll().stream()
				.map(sectionMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<SectionDTO> findSectionById(Integer sectionId)
	{
		return sectionRepository.findById(sectionId)
				.map(sectionMapper::convertToDTO);
	}
	
	@Override
	public SectionDTO updateSection(Integer id, SectionDTO dto) {
		
	    if (dto.getSectionName() == null || dto.getSectionName().trim().isEmpty()) 
	    {
	        throw new IllegalArgumentException("Section name is required!");
	    }
	    
	    Section existingSection = sectionRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Could not find section with id: " + id));
	    
	    existingSection.setSectionName(dto.getSectionName());
	 
	    
	    existingSection = sectionRepository.save(existingSection);
	    return sectionMapper.convertToDTO(existingSection);
	}
	
	
	// teacher methods
	@Override
	public TeacherDTO saveTeacher(TeacherDTO dto)
	{
		Teacher teacher = teacherMapper.convertToEntity(dto);
		if (teacher.getFirstName() == null || teacher.getLastName() == null)
			throw new IllegalArgumentException("Invalid teacher name!");
		
		teacher = teacherRepository.save(teacher);
		return teacherMapper.convertToDTO(teacher);
	}
	
	@Override
	public void deleteTeacher(Integer teacherId)
	{
		if (!teacherRepository.existsById(teacherId))
			throw new RuntimeException("Could not find teacher with id: " + teacherId);
		teacherRepository.deleteById(teacherId);
	}
	
	@Override
	public List<TeacherDTO> findAllTeachers()
	{
		return teacherRepository.findAll().stream()
				.map(teacherMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<TeacherDTO> findTeacherById(Integer teacherId)
	{
		return teacherRepository.findById(teacherId)
				.map(teacherMapper::convertToDTO);
	}
	
	@Override
	public TeacherDTO updateTeacher(Integer id, TeacherDTO dto) 
	{
	    if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty())
	    {
	        throw new IllegalArgumentException("First name is required!");
	    }
	    if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) 
	    {
	        throw new IllegalArgumentException("Last name is required!");
	    }
	    
	    Teacher existingTeacher = teacherRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Could not find teacher with id: " + id));
	    
	    existingTeacher.setFirstName(dto.getFirstName());
	    existingTeacher.setLastName(dto.getLastName());
	    
	    existingTeacher = teacherRepository.save(existingTeacher);
	    return teacherMapper.convertToDTO(existingTeacher);
	}
	
	// teacher enrollment methods
	@Override
	public TeacherEnrollmentDTO saveTeacherEnrollment(TeacherEnrollmentDTO dto)
	{
		if (dto.getSectionId() == null || dto.getTeacherId() == null)
			throw new IllegalArgumentException("Malformed dto. Section or teacher ID not defined.");
		
		TeacherEnrollment enroll = teacherEnrollmentMapper.convertToEntity(dto);
		enroll = teacherEnrollmentRepository.save(enroll);
		return teacherEnrollmentMapper.convertToDTO(enroll);
	}
	
	@Override
	public void deleteTeacherEnrollment(Integer enrollmentId)
	{
		if (!teacherEnrollmentRepository.existsById(enrollmentId))
			throw new RuntimeException("Could not find teacher enrollment with id: " + enrollmentId);
		teacherEnrollmentRepository.deleteById(enrollmentId);
	}
	
	@Override
	public List<TeacherEnrollmentDTO> findAllTeacherEnrollments()
	{
		return teacherEnrollmentRepository.findAll().stream()
				.map(teacherEnrollmentMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<TeacherEnrollmentDTO> findTeacherEnrollmentById(Integer enrollmentId)
	{
		return teacherEnrollmentRepository.findById(enrollmentId)
				.map(teacherEnrollmentMapper::convertToDTO);
	}
	
	@Override
	public List<TeacherEnrollmentDTO> findEnrollmentsByTeacherId(Integer enrollmentId)
	{
		List<TeacherEnrollment> enrollments = teacherEnrollmentRepository.findByTeacherTeacherId(enrollmentId);
		return enrollments.stream()
				.map(teacherEnrollmentMapper::convertToDTO)
				.collect(Collectors.toList());
	}
	
	//analytical methods
	@Override
	public Double calculateStudentAverage(Integer studentId) {
		List<Grade> grades = gradeRepository.findByStudentStudentId(studentId);
        
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (Grade grade : grades) {
            sum += grade.getScore();
        }
        
        return sum / grades.size();
	}

	@Override
	public Double calculateAssignmentAverage(Integer assignmentId) {
		Double average = gradeRepository.findAverageScoreByAssignmentId(assignmentId);
	    return average != null ? average : 0.0;
	}



}
