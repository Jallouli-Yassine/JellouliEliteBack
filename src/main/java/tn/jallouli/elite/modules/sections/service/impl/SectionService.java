package tn.jallouli.elite.modules.sections.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.jallouli.elite.exception.ResourceNotFoundException;
import tn.jallouli.elite.modules.course.entity.Course;
import tn.jallouli.elite.modules.course.repository.CourseRepo;
import tn.jallouli.elite.modules.lesson.dto.LessonRequest;
import tn.jallouli.elite.modules.lesson.entity.Lesson;
import tn.jallouli.elite.modules.lesson.mapper.LessonMapper;
import tn.jallouli.elite.modules.sections.dto.SectionRequest;
import tn.jallouli.elite.modules.sections.entity.Section;
import tn.jallouli.elite.modules.sections.mapper.SectionMapper;
import tn.jallouli.elite.modules.sections.repository.SectionRepo;
import tn.jallouli.elite.modules.sections.service.SectionInterface;

@Service
@AllArgsConstructor
public class SectionService implements SectionInterface {
    public final SectionRepo sectionRepo;
    public final SectionMapper sectionMapper;
    public final LessonMapper lessonMapper;
    public final CourseRepo courseRepo;

    @Override
    public void createSection(SectionRequest sectionRequest, Long idCourse) {
        Course c = courseRepo.findById(idCourse).orElseThrow(()-> new ResourceNotFoundException("Course not found with id: " + idCourse));
        Section s =  sectionMapper.toEntity(sectionRequest);
        for(LessonRequest lessonRequest : sectionRequest.getLessons() ){
           Lesson l = lessonMapper.toEntity(lessonRequest);
           l.setSection(s);
           s.getLessons().add(l);
       }
        s.setCourse(c);
        sectionRepo.save(s);

    }

    @Override
    public void updateSection(Long idSection, SectionRequest sectionRequest) {

    }

    @Override
    public void deleteSection(Long idSection) {

    }
}
