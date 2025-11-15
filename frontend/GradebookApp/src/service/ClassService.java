package service;

import model.TeacherEnrollment;
import model.Section;
import model.Student;
import model.StudentEnrollment;

import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service to handle business logic related to classes, students, and sections.
 * Currently returns simulated data, ready for replacement with API calls.
 */
public class ClassService {
	
	private final HttpClient client = HttpClient.newHttpClient();
	private final ObjectMapper mapper = new ObjectMapper();
	
    // Fetch all classes taught by a teacher
    public List<Section> getClassesForTeacher(Integer teacherId)
    {
    	HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("http://localhost:8080/api/teacherenrollments/teacher/" + teacherId))
    			.GET()
    			.build();
    	
    	try {
    		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    		if (response.statusCode() == 200)
    		{
    			// Parse the JSON string into a list of enrollment objects
    			List<TeacherEnrollment> enrollments = mapper.readValue(response.body(), new TypeReference<List<TeacherEnrollment>>() {});
    			return enrollments.stream()
    					.map(enrollment -> {
    						Section section = new Section();
    						section.setId(enrollment.getId());
    						section.setCourseId(1);
    						section.setCourseName("PLACEHOLDER");
    						section.setSectionName(enrollment.getSectionName());
    						
    						return section;
    					})
    					.collect(Collectors.toList());
    		}
    		else 
    		{
    			System.out.println("Error: Status code " + response.statusCode());
    			return List.of();
    		}
    		
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    		return List.of();
    	}
    }
    
    // Fetch the roster data for a given class
    public List<Student> getRosterForClass(Integer classId)
    {
    	HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("http://localhost:8080/api/studentenrollments/sections/" + classId))
    			.GET()
    			.build();
    	try {
    		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    		if (response.statusCode() == 200)
    		{
    			List<StudentEnrollment> enrollments = mapper.readValue(response.body(), new TypeReference<List<StudentEnrollment>>() {});
    			return enrollments.stream()
    					.map(enrollment -> {
    						Student student = new Student();
    						student.setId(enrollment.getStudentId());
    						String[] nameData = enrollment.getStudentName().split(" ");
    						student.setFirstName(nameData[0]);
    						student.setLastName(nameData[1]);
    						return student;
    					})
    					.collect(Collectors.toList());
    		}
    		else
    		{
    			System.out.println("Error: Status code " + response.statusCode());
    			return List.of();
    		}
    		
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    		return List.of();
    	}
    }
    
    // Simulates fetching all assignments for a class
    public List<Assignment> getAssignmentsForSection(int sectionId) {
        // Assignment constructor: (sectionId, assignmentName, assignmentType, maxPoints)
        return Arrays.asList(
            new Assignment(sectionId, "Homework 1", "HOMEWORK", 100.0),
            new Assignment(sectionId, "Final Exam", "FINAL_EXAM", 200.0),
            new Assignment(sectionId, "Midterm Exam", "EXAM", 150.0)
        );
    }
    
    // Simulates fetching all grades for a specific section
    public List<Grade> getGradesForSection(int sectionId) {
        List<Grade> grades = new ArrayList<>();
        
        // Simulating different grades based on sectionId for realistic results
        if (sectionId == 1101) { // Algebra I: High Scores
            grades.add(new Grade(1, 2001, "Homework 1", 95.0, 100.0));
            grades.add(new Grade(2, 2001, "Final Exam", 185.0, 200.0));
            grades.add(new Grade(3, 2002, "Homework 1", 100.0, 100.0));
            grades.add(new Grade(4, 2003, "Midterm Exam", 145.0, 150.0));
            
        } else if (sectionId == 1102) { // CSCI 1102: Mixed Scores
            grades.add(new Grade(5, 2001, "Homework 1", 60.0, 100.0));
            grades.add(new Grade(6, 2002, "Final Exam", 125.0, 200.0));
            grades.add(new Grade(7, 2003, "Homework 1", 85.0, 100.0));
            grades.add(new Grade(8, 2004, "Midterm Exam", 90.0, 150.0));

        } else if (sectionId == 1103) { // Hospitality: Low Scores
            grades.add(new Grade(9, 2001, "Homework 1", 50.0, 100.0));
            grades.add(new Grade(10, 2004, "Final Exam", 100.0, 200.0));
            
        }
        
        return grades;
    }
    
    /**
     * Calculates the unweighted average score for a student based on their grades.
     * Defaults to 0.0 if no grades are found.
     */
    public double calculateUnweightedAverage(List<Grade> studentGrades) {
        if (studentGrades == null || studentGrades.isEmpty()) {
            return 0.0;
        }

        double totalPointsEarned = 0;
        double totalMaxPoints = 0;

        for (Grade grade : studentGrades) {
            totalPointsEarned += grade.getScore();
            totalMaxPoints += grade.getMaxScore();
        }

        if (totalMaxPoints <= 0) {
            return 0.0;
        }

        return (totalPointsEarned / totalMaxPoints) * 100.0;
    }
    
    // Simulates fetching attendance rate
    public double getAttendanceRate(int studentId) {
        return 92.5; 
    }
}