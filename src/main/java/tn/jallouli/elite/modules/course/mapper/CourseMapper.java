package tn.jallouli.elite.modules.course.mapper;

import org.springframework.stereotype.Component;
import tn.jallouli.elite.modules.course.dto.CourseRequest;
import tn.jallouli.elite.modules.course.entity.Course;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setLevel(request.getLevel());
        course.setDuration(request.getDuration());
        course.setPublished(request.getPublished());
        course.setThumbnail(request.getThumbnail());
        return course;
    }
}
