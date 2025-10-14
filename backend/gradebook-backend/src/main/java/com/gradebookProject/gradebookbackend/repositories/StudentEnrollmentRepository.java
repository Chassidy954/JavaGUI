//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.StudentEnrollment;
import com.gradebookProject.gradebookbackend.entities.StudentEnrollmentId;


public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, StudentEnrollmentId> {

}
