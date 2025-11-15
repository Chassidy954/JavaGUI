package com.gradebookProject.gradebookbackend.repositories;

import com.gradebookProject.gradebookbackend.entities.Grade;
import com.gradebookProject.gradebookbackend.entities.Student;
import com.gradebookProject.gradebookbackend.entities.Assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    
    // Basic find methods
    List<Grade> findByStudent(Student student);
    List<Grade> findByAssignment(Assignment assignment);
    
    // Find by IDs
    List<Grade> findByStudentStudentId(Integer studentId);
    List<Grade> findByAssignmentId(Integer assignmentId);
    
    // Find specific grade
    Optional<Grade> findByStudentStudentIdAndAssignmentId(Integer studentId, Integer assignmentId);
    
    // Find grades for a section
    List<Grade> findByAssignmentSectionSectionId(Integer sectionId);
    
    // Analytics queries
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.assignment.id = :assignmentId")
    Double findAverageScoreByAssignmentId(@Param("assignmentId") Integer assignmentId);
    
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student.id = :studentId")
    Double findAverageScoreByStudentId(@Param("studentId") Integer studentId);
    
    @Query("SELECT COUNT(g) FROM Grade g WHERE g.assignment.id = :assignmentId AND g.score >= :minScore")
    Integer countByAssignmentIdAndMinScore(@Param("assignmentId") Integer assignmentId, @Param("minScore") Double minScore);
}