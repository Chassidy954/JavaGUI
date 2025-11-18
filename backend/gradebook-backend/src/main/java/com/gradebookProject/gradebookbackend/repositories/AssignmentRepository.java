//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
	List<Assignment> findBySectionSectionId(Integer id);
}
