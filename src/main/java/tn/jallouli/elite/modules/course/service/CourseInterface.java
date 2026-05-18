package tn.jallouli.elite.modules.course.service;

import tn.jallouli.elite.modules.course.dto.CourseRequest;
import tn.jallouli.elite.modules.course.entity.Course;
import tn.jallouli.elite.modules.lesson.entity.Lesson;
import tn.jallouli.elite.modules.sections.entity.Section;

public interface CourseInterface {
    void createCourse(Long idInstructor, CourseRequest courseRequest);
    void updateCourse(Long idOldCourse,CourseRequest course);
    void deleteCourse(Long idCourse);


}
