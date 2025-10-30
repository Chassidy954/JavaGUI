//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.TeacherEnrollment;
import com.gradebookProject.gradebookbackend.entities.TeacherEnrollmentId;
import org.springframework.stereotype.Repository;

public interface TeacherEnrollmentRepository extends JpaRepository<TeacherEnrollment, TeacherEnrollmentId> {

}
