package tn.jallouli.elite.modules.lesson.service;

import tn.jallouli.elite.modules.lesson.dto.LessonRequest;

public interface LessonInterface {
    void createLesson(LessonRequest lessonRequest, Long idSection);
    void updateLesson(Long idLesson, LessonRequest lessonRequest);
    void deleteLesson(Long idLesson);
}
