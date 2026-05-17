package tn.jallouli.elite.modules.lesson.dto;

import lombok.Getter;

@Getter
public class LessonRequest {

    private String title;
    private String description;
    private String videoUrl;
    private String duration;
    private boolean preview;
    private int position;
}