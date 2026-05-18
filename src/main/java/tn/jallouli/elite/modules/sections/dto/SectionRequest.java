package tn.jallouli.elite.modules.sections.dto;

import lombok.Getter;
import tn.jallouli.elite.modules.lesson.dto.LessonRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SectionRequest {

    private String title;
    private String description;
    private Integer position;

    private List<LessonRequest> lessons = new ArrayList<>();


}
