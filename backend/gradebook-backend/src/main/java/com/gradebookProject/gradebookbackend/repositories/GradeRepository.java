//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.Grade;
import com.gradebookProject.gradebookbackend.entities.GradeId;

public interface GradeRepository extends JpaRepository<Grade, GradeId> {

}
