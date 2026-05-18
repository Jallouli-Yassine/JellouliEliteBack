package tn.jallouli.elite.modules.course.dto;

import lombok.Getter;
import tn.jallouli.elite.modules.sections.dto.SectionRequest;

import java.util.ArrayList;
import java.util.List;


@Getter
public class CourseRequest {

    private String title;
    private String description;
    private String thumbnail;
    private String price;
    private String level;
    private String duration;
    private String published;


    private List<SectionRequest> sections = new ArrayList<>();
}
