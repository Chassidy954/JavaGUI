//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.StudentEnrollment;
import org.springframework.stereotype.Repository;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Integer> {
	List<StudentEnrollment> findBySectionSectionId(Integer sectionId);
}
