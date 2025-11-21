package service;

import model.TeacherEnrollment;
import model.Assignment;
import model.Grade;
import model.Section;
import model.Student;
import model.StudentEnrollment;
import model.Attendance;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ClassService {
    
    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8080/api"; 
    
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    
    public List<Section> getClassesForTeacher(Integer teacherId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/teacherenrollments/teacher/" + teacherId))
                .GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<TeacherEnrollment> enrollments = mapper.readValue(response.body(), new TypeReference<List<TeacherEnrollment>>() {});
                return enrollments.stream().map(enrollment -> {
                            Section section = new Section();
                            section.setId(enrollment.getId());
                            section.setCourseId(1);
                            section.setCourseName("PLACEHOLDER");
                            section.setSectionName(enrollment.getSectionName());
                            return section;
                        }).collect(Collectors.toList());
            } else {
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Section> getClassesForStudent(Integer studentId) {
        List<Attendance> records = getAttendanceForStudent(studentId);
        Map<Integer, Section> uniqueSections = new HashMap<>();
        for (Attendance att : records) {
            if (att.getSectionId() != null && !uniqueSections.containsKey(att.getSectionId())) {
                Section s = new Section();
                s.setId(att.getSectionId());
                s.setSectionName(att.getSectionName());
                uniqueSections.put(att.getSectionId(), s);
            }
        }
        return new ArrayList<>(uniqueSections.values());
    }
    
    public List<Student> getRosterForClass(Integer classId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/studentenrollments/sections/" + classId))
                .GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<StudentEnrollment> enrollments = mapper.readValue(response.body(), new TypeReference<List<StudentEnrollment>>() {});
                return enrollments.stream().map(enrollment -> {
                            Student student = new Student();
                            student.setId(enrollment.getStudentId());
                            String[] nameData = enrollment.getStudentName().split(" ");
                            student.setFirstName(nameData[0]);
                            student.setLastName(nameData.length > 1 ? nameData[1] : "");
                            return student;
                        }).collect(Collectors.toList());
            } else {
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public List<Assignment> getAssignmentsForSection(int sectionId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/assignments/section/" + sectionId))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Assignment>>() {});
            } else {
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    // Get assignment by ID
    public Assignment getAssignmentById(int assignmentId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/assignments/" + assignmentId))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Assignment.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private List<Grade> fetchGradesByUrl(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Grade>>() {});
            } else {
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Grade> getGradesForStudent(int studentId) {
        return fetchGradesByUrl(BASE_URL + "/grades/student/" + studentId);
    }
    
    public List<Attendance> getAttendanceForStudent(int studentId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/attendance/student/" + studentId))
                .GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Attendance>>() {}); 
            } else {
                return List.of();
            }
        } catch (Exception e) {
            return List.of();
        }
    }

    public double calculateUnweightedAverage(List<Grade> studentGrades) {
        if (studentGrades == null || studentGrades.isEmpty()) return 0.0;
        double totalPointsEarned = 0;
        double totalMaxPoints = 0;
        for (Grade grade : studentGrades) {
            totalPointsEarned += grade.getScore();
            if (grade.getPercentage() > 0) {
                totalMaxPoints += (grade.getScore() / grade.getPercentage()); 
            }
        }
        if (totalMaxPoints <= 0) return 0.0;
        return (totalPointsEarned / totalMaxPoints) * 100.0;
    }

    public boolean deleteGrade(Integer gradeId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/grades/" + gradeId))
                .DELETE()
                .build();
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 || response.statusCode() == 204) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Assignment addAssignment(Assignment newAssignment) {
        try {
            String json = mapper.writeValueAsString(newAssignment);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/assignments"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return mapper.readValue(response.body(), Assignment.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Grade addGrade(Grade newGrade) {
        try {
            String json = mapper.writeValueAsString(newGrade);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/grades"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return mapper.readValue(response.body(), Grade.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean updateGrade(Grade grade) {
        try {
            String json = mapper.writeValueAsString(grade);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/grades/" + grade.getId())) 
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return (response.statusCode() == 200);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}