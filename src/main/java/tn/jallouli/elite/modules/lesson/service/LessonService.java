package tn.jallouli.elite.modules.lesson.service;

import org.springframework.stereotype.Service;
import tn.jallouli.elite.exception.ResourceNotFoundException;
import tn.jallouli.elite.modules.lesson.dto.LessonRequest;
import tn.jallouli.elite.modules.lesson.entity.Lesson;
import tn.jallouli.elite.modules.lesson.mapper.LessonMapper;
import tn.jallouli.elite.modules.lesson.repository.LessonRepo;
import tn.jallouli.elite.modules.sections.entity.Section;
import tn.jallouli.elite.modules.sections.repository.SectionRepo;

@Service
public class LessonService implements LessonInterface {
    private final LessonMapper lessonMapper;
    private final LessonRepo lessonRepo;
    private final SectionRepo sectionRepo;

    public LessonService(LessonMapper lessonMapper, LessonRepo lessonRepo, SectionRepo sectionRepo) {
        this.lessonMapper = lessonMapper;
        this.lessonRepo = lessonRepo;
        this.sectionRepo = sectionRepo;
    }

    @Override
    public void createLesson(LessonRequest lessonRequest, Long idSection) {
        Section s = sectionRepo.findById(idSection).orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + idSection));
        Lesson l = lessonMapper.toEntity(lessonRequest);
        l.setSection(s);
        s.getLessons().add(l);
        lessonRepo.save(l);
    }

    @Override
    public void updateLesson(Long idLesson, LessonRequest lessonRequest) {
        Lesson existingLesson = lessonRepo.findById(idLesson).orElseThrow(()-> new ResourceNotFoundException("Lesson not found with id: " + idLesson));
        if (lessonRequest.getTitle() != null) existingLesson.setTitle(lessonRequest.getTitle());
        if (lessonRequest.getDescription() != null) existingLesson.setDescription(lessonRequest.getDescription());
        if (lessonRequest.getVideoUrl() != null) existingLesson.setVideoUrl(lessonRequest.getVideoUrl());
        if (lessonRequest.getDuration() != null) existingLesson.setDuration(lessonRequest.getDuration());
        if (lessonRequest.getPosition() != null) existingLesson.setPosition(lessonRequest.getPosition());
        lessonRepo.save(existingLesson);

    }

    @Override
    public void deleteLesson(Long idLesson) {
        if(lessonRepo.findById(idLesson).isPresent()){
            lessonRepo.deleteById(idLesson);
        }else{
            throw new ResourceNotFoundException("Lesson not found with id: " + idLesson);
        }
    }
}
