package tn.jallouli.elite.modules.course.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.jallouli.elite.exception.ResourceNotFoundException;
import tn.jallouli.elite.modules.course.dto.CourseRequest;
import tn.jallouli.elite.modules.course.entity.Course;
import tn.jallouli.elite.modules.course.mapper.CourseMapper;
import tn.jallouli.elite.modules.course.repository.CourseRepo;
import tn.jallouli.elite.modules.course.service.CourseInterface;
import tn.jallouli.elite.modules.lesson.dto.LessonRequest;
import tn.jallouli.elite.modules.lesson.entity.Lesson;
import tn.jallouli.elite.modules.lesson.mapper.LessonMapper;
import tn.jallouli.elite.modules.sections.dto.SectionRequest;
import tn.jallouli.elite.modules.sections.entity.Section;
import tn.jallouli.elite.modules.sections.mapper.SectionMapper;
import tn.jallouli.elite.modules.user.entity.UserEntity;
import tn.jallouli.elite.modules.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseInterface {

    private final CourseRepo courseRepo;
    private final UserRepository userRepo;
    private final CourseMapper courseMapper;
    private final SectionMapper sectionMapper;
    private final LessonMapper lessonMapper;

    @Override
    public void createCourse(Long idInstructor, CourseRequest courseRequest) {

        UserEntity instructor = userRepo.findById(idInstructor)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Instructor not found with id: " + idInstructor));
        Course c = courseMapper.toEntity(courseRequest);
        c.setInstructor(instructor);
        if(courseRequest.getSections().isEmpty()){
            throw new ResourceNotFoundException("Course must have at least one section");
        }
        for (SectionRequest sectionRequest : courseRequest.getSections()) {
            Section s = sectionMapper.toEntity(sectionRequest);

            for (LessonRequest lessonRequest : sectionRequest.getLessons()) {

                Lesson l = lessonMapper.toEntity(lessonRequest);
                l.setSection(s);
                s.getLessons().add(l);
            }
            s.setCourse(c);
            c.getSections().add(s);
        }
        courseRepo.save(c);
    }

    @Override
    public void updateCourse(Long idOldCourse, Course course) {
        //Course c = courseRepo.findById(idOldCourse)
    }

    @Override
    public void deleteCourse(Long idCourse) {
        if(courseRepo.findById(idCourse).isEmpty()){
            throw new ResourceNotFoundException("Course not found with id: " + idCourse);
        }
        courseRepo.deleteById(idCourse);
    }


}
