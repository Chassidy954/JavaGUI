package service;

import model.TeacherEnrollment;
import model.Assignment;
import model.Grade;
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
        HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create("http://localhost:8080/api/assignments/sections/" + sectionId))
        		.GET()
        		.build();
        try {
        	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        	if (response.statusCode() == 200)
        	{
        		List<Assignment> assignments = mapper.readValue(response.body(), new TypeReference<List<Assignment>>() {});
        		return assignments.stream()
        				.map(assignment -> {
        					Assignment asnm = new Assignment();
        					asnm.setId(assignment.getId());
        					asnm.setAssignmentName(assignment.getAssignmentName());
        					asnm.setAssignmentType(assignment.getAssignmentType());
        					asnm.setSectionId(assignment.getSectionId());
        					asnm.setSectionName(assignment.getSectionName());
        					asnm.setMaxScore(assignment.getMaxScore());
        					return asnm;
        				})
        				.collect(Collectors.toList());
        	}
        	else
        	{
        		System.out.println("Error: status code " + response.statusCode());
        		return List.of();
        	}
        } catch (Exception e)
        {
        	e.printStackTrace();
        	return List.of();
        }
    }
    
    
    private List<Grade> fetchGradesByUrl(String url)
    {
    	HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create(url))
        		.GET()
        		.build();
        try {
        	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        	if (response.statusCode() == 200)
        	{
        		List<Grade> myGrades = mapper.readValue(response.body(), new TypeReference<List<Grade>>() {});
        		return myGrades.stream()
        				.map(oldGrade -> {
        					Grade grade = new Grade();
        					grade.setId(oldGrade.getId());
        					grade.setLetterGrade(oldGrade.getLetterGrade());
        					grade.setPercentage(oldGrade.getPercentage());
        					grade.setScore(oldGrade.getScore());
        					grade.setComments(oldGrade.getComments());
        					grade.setAssignmentId(oldGrade.getAssignmentId());
        					grade.setAssignmentName(oldGrade.getAssignmentName());
        					grade.setStudentId(oldGrade.getStudentId());
        					grade.setStudentName(oldGrade.getStudentName());
        					return grade;
        				})
        				.collect(Collectors.toList());
        	}
        	else
        	{
        		System.out.println("Error: status code " + response.statusCode());
        		return List.of();
        	}
        } catch (Exception e)
        {
        	e.printStackTrace();
        	return List.of();
        }
    }
    // Simulates fetching all grades for a specific section
    public List<Grade> getGradesForSection(int sectionId) {
        String url = "http://localhost:8080/api/grades/section/" + sectionId;
        return fetchGradesByUrl(url);
    }
    
    // Fetch all grades for a given student
    public List<Grade> getGradesForStudent(int studentId)
    {
    	String url = "http://localhost:8080/api/grades/student/" + studentId;
    	return fetchGradesByUrl(url);
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
            totalMaxPoints += (grade.getScore() / grade.getPercentage());
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