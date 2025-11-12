package com.gradebookProject.gradebookbackend.config;

import com.gradebookProject.gradebookbackend.entities.*;
import com.gradebookProject.gradebookbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.time.LocalDate;

/**
 * This class runs on application startup.
 * It checks if the database is empty, and if so,
 * populates it with initial test data.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentEnrollmentRepository studentEnrollmentRepository;
    @Autowired
    private TeacherEnrollmentRepository teacherEnrollmentRepository;
    @Autowired
    private GradeRepository gradeRepository;

    // Spring Boot runs this automatically
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    // Seeding logic
    private void loadData() {
    	
        // Check if data already exists
        if (studentRepository.count() == 0) {
            System.out.println("Database is empty. Seeding data...");

            // Handle independent entities first
            
            // Create Students
            Student s1 = new Student("Montgomery", "Placeholder");
            Student s2 = new Student("Isaac", "Nerdton");
            Student s3 = new Student("JimJam", "FlimFlam");
            studentRepository.saveAll(List.of(s1, s2, s3));

            // Create Teachers
            Teacher t1 = new Teacher("Marvin", "McClendontron");
            Teacher t2 = new Teacher("Robert", "Smithington");
            teacherRepository.saveAll(List.of(t1, t2));

            // Create Courses
            Course math = new Course("Math");
            Course compSci = new Course("CompSci");
            Course dining = new Course("Fine Dining and Breathing");
            courseRepository.saveAll(List.of(math, compSci, dining));

            // Handle dependent entities (relies on the IDs above)

            // Create Sections
            Section algebra = new Section("Algebra 1");
            algebra.setCourse(math); // Set the relationship
            sectionRepository.save(algebra);

            Section csci101 = new Section("CSCI 101");
            csci101.setCourse(compSci);
            sectionRepository.save(csci101);
            
            Section hospitality = new Section("Hospitality And Hospitals");
            hospitality.setCourse(dining);
            sectionRepository.save(hospitality);

            // Create Assignments
            Assignment hw1 = new Assignment("Homework 1", "HOMEWORK", algebra, 100);
            Assignment test1 = new Assignment("Test 1", "TEST", algebra, 100);
            Assignment hw1_csci = new Assignment("Homework 1", "HOMEWORK", csci101, 100);
            Assignment cla1 = new Assignment("Forgetting HR", "CLA", hospitality, 100);
            assignmentRepository.saveAll(List.of(hw1, test1, hw1_csci, cla1));

            // Create Enrollments (the junction)
            StudentEnrollment enroll1 = new StudentEnrollment(s1, algebra);
            StudentEnrollment enroll2 = new StudentEnrollment(s2, algebra);
            StudentEnrollment enroll3 = new StudentEnrollment(s3, hospitality);
            studentEnrollmentRepository.saveAll(List.of(enroll1, enroll2, enroll3));
            
            TeacherEnrollment tEn1 = new TeacherEnrollment(t1, algebra);
            TeacherEnrollment tEn2 = new TeacherEnrollment(t2, csci101);
            TeacherEnrollment tEn2_alt = new TeacherEnrollment(t2, hospitality);
            
            teacherEnrollmentRepository.saveAll(List.of(tEn1, tEn2, tEn2_alt));

            // Create Grades
            Grade g1 = new Grade(s1, hw1, 95.0, "Good work");
            Grade g2 = new Grade(s2, hw1, 82.0, "Needs improvement");
            Grade g3 = new Grade(s3, cla1, 99.5, "You're a natural!");
            gradeRepository.saveAll(List.of(g1, g2, g3));
            
            // Create Attendance
            Attendance at1 = new Attendance(s1, algebra, LocalDate.now(), "PRESENT");
            Attendance at2 = new Attendance(s2, csci101, LocalDate.now(), "EXCUSED");
            Attendance at3 = new Attendance(s3, hospitality, LocalDate.now(), "TARDY");
            attendanceRepository.saveAll(List.of(at1, at2, at3));

            System.out.println("Database seeding complete.");
        } else {
            System.out.println("Database is not empty. Skipping seeding.");
        }
    }
}