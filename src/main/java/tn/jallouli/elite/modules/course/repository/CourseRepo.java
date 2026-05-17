package tn.jallouli.elite.modules.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.jallouli.elite.modules.course.entity.Course;

public interface CourseRepo extends JpaRepository<Course,Long> {
}
