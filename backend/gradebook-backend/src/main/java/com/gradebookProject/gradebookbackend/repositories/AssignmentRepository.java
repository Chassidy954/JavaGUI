//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.Assignment;
import com.gradebookProject.gradebookbackend.entities.AssignmentId;

public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentId> {

}
