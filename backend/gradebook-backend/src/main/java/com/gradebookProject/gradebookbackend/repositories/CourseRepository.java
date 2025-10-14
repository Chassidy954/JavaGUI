//repositories are used for data access

package com.gradebookProject.gradebookbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gradebookProject.gradebookbackend.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
