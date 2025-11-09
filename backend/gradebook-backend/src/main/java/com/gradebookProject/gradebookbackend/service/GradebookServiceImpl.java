package com.gradebookProject.gradebookbackend.service;

import com.gradebookProject.gradebookbackend.entities.*;
import com.gradebookProject.gradebookbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradebookServiceImpl implements GradebookService {

	//autowired applies dependencies where labeled to so, student, assignment, and grade repo
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private GradeRepository gradeRepository;
	
	@Override
	public List<Student> findAllStudents() {
		// TODO Auto-generated method stub
		return studentRepository.findAll();
	}

	@Override
	public Optional<Student> findStudentById(Integer id) {
		// TODO Auto-generated method stub
		return studentRepository.findById(id);
	}

	@Override
	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
		
		//validation
		if(student.getFirstName() == null || student.getFirstName().trim().isEmpty())
		{
	            throw new IllegalArgumentException("First name is required!");
        }
        if(student.getLastName() == null || student.getLastName().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Last name is required!");
        }
    
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudent(Integer id) {
		// TODO Auto-generated method stub
		//check if student exists
		if(!studentRepository.existsById(id))
		{
			throw new RuntimeException("Student not found with id: " + id);
		}
		
		//delete grades first then student
		List<Grade> studentGrades = gradeRepository.findByStudentStudentId(id); 
		gradeRepository.deleteAll(studentGrades);
		studentRepository.deleteById(id);
	}
	
	// attendance
	public List<Attendance> findAttendanceByStudentId(Integer studentId)
	{
		return attendanceRepository.findByStudentStudentId(studentId);
	}

	//assignments
	
	@Override
	public List<Assignment> findAllAssignments() {
		// TODO Auto-generated method stub
		return assignmentRepository.findAll();
	}

	@Override
	public Assignment saveAssignment(Assignment assignment) {
		// TODO Auto-generated method stub
		//validation
		if(assignment.getId() == null) { //assignment class only has private AssignmentId id; and private String assignmentType;

            throw new IllegalArgumentException("Assignment ID (sectionId and assignmentName is required!");
        }
		if(assignment.getAssignmentName() == null || assignment.getAssignmentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Assignment name is required!");
        }
        if(assignment.getMaxScore() == null || assignment.getMaxScore() <= 0) {
            throw new IllegalArgumentException("Maximum score must be greater than 0!");
        }
		
		return assignmentRepository.save(assignment);
	}
	
	@Override
    public void deleteAssignment(Integer assignmentId) 
	{
        //check if assignment exists first
        if (!assignmentRepository.existsById(assignmentId)) 
        {
            throw new RuntimeException("Assignment not found " + assignmentId);
        }
        
        // Delete grades first then assignment
        List<Grade> assignmentGrades = gradeRepository.findByAssignmentId(assignmentId);
        
        gradeRepository.deleteAll(assignmentGrades);
        assignmentRepository.deleteById(assignmentId); 
    }
	
	@Override
    public Optional<Assignment> findAssignmentById(Integer assignmentId) {
        return assignmentRepository.findById(assignmentId);
	}
	
	//grades

	@Override
	public Optional<Grade> findGradeByStudentAndAssignment(Integer studentId, Integer assignmentId) {
		// TODO Auto-generated method stub
		return gradeRepository.findByStudentStudentIdAndAssignmentId(studentId, assignmentId);

	}

	@Override
	public Grade updateGrade(Integer gradeId, Double score, String comments) {
		// TODO Auto-generated method stub
		Grade grade = gradeRepository.findById(gradeId)
		        .orElseThrow(() -> new RuntimeException("Grade not found with id: " + gradeId));
		    
		    // Update score if provided
		    if (score != null) {
		        // Validate score against assignment max score
		        if (score < 0) {
		            throw new IllegalArgumentException("Score cannot be negative");
		        }
		        if (grade.getAssignment().getMaxScore() != null && score > grade.getAssignment().getMaxScore()) {
		            throw new IllegalArgumentException("Score exceeds maximum allowed score");
		        }
		        grade.setScore(score);
		    }
		    
		    // Update comments if provided
		    if (comments != null) {
		        grade.setComments(comments);
		    }
		    
		    return gradeRepository.save(grade);
		}

	@Override
	public void deleteGrade(Integer gradeId) {
		// TODO Auto-generated method stub
		if (!gradeRepository.existsById(gradeId)) {
	        throw new RuntimeException("Grade not found with id: " + gradeId);
	    }
	    gradeRepository.deleteById(gradeId);
	}

	@Override
	public List<Grade> findGradesByAssignment(Integer assignmentId) {
		// TODO Auto-generated method stub
		return gradeRepository.findByAssignmentId(assignmentId);
	}
	
	@Override
	public Grade submitGrade(Integer studentId, Integer assignmentId, Double score) {
		// TODO Auto-generated method stub
		Student student = studentRepository.findById(studentId) //Type mismatch: cannot convert from Optional<Student> to Student
			.orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
			
		Assignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new RuntimeException("Assignment not found with id: " + assignmentId));  //both .orelsethrows are telling me to delete the '.' token but then tell me that function doesnt exist
		
		//validation
		if(score < 0)
		{
			throw new IllegalArgumentException("Score can't be negative.");
		}
		if(assignment.getMaxScore() != null && score > assignment.getMaxScore())
		{
			throw new  IllegalArgumentException("Score" + score + " exceeds maximum score limit of " + assignment.getMaxScore());
		}
		
		
		//check if grade exists
		
		Optional<Grade> existingGrade = gradeRepository.findByStudentStudentIdAndAssignmentId(studentId, assignmentId);
			
		Grade grade;
		if(existingGrade.isPresent())
		{
			grade = existingGrade.get();
			grade.setScore(score);
		}
		else
		{
			grade = new Grade();
			grade.setStudent(student);
			grade.setAssignment(assignment);
			grade.setScore(score);
		}
		
		return gradeRepository.save(grade);
	}

	@Override
	public List<Grade> findGradesByStudentId(Integer studentId) {
		// TODO Auto-generated method stub
		return gradeRepository.findByStudentStudentId(studentId); 
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
		// TODO Auto-generated method stub
		Double average = gradeRepository.findAverageScoreByAssignmentId(assignmentId);
	    return average != null ? average : 0.0;
	}



}
