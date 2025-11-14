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
 * Service to handle business logic related to classes, students, and subjects.
 * Currently returns simulated data.
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
    
    // Simulates fetching the current subject name
    public String getSubjectName(String classId) {
        if ("C001".equals(classId)) return "Algebra I";
        if ("C002".equals(classId)) return "Geometry";
        return "Unknown Subject";
    }
    
    // Simulates fetching attendance data (TBD)
    public double getAttendanceRate(int studentId) {
        // For now, return a random simulated value
        return 92.5; 
    }
}