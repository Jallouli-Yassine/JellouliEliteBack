package tn.jallouli.elite.modules.lesson.mapper;

import org.springframework.stereotype.Component;
import tn.jallouli.elite.modules.lesson.dto.LessonRequest;
import tn.jallouli.elite.modules.lesson.entity.Lesson;

@Component
public class LessonMapper {
    public Lesson toEntity(LessonRequest request) {
         Lesson lesson = new Lesson();
         lesson.setTitle(request.getTitle());
         lesson.setDescription(request.getDescription());
         lesson.setVideoUrl(request.getVideoUrl());
         lesson.setDuration(request.getDuration());
         lesson.setPreview(request.isPreview());
         lesson.setPosition(request.getPosition());
        return lesson;
    }
}
