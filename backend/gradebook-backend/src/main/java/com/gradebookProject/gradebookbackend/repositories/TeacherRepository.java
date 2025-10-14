//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gradebookProject.gradebookbackend.entities.Teacher;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

}
