package tn.jallouli.elite.modules.enrollment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.jallouli.elite.modules.course.entity.Course;
import tn.jallouli.elite.modules.user.entity.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "enrollments")
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    //enrolledAt is the date when the student enrolled in the course
    private LocalDateTime enrolledAt = LocalDateTime.now();
    //progress is the percentage of the course completed by the student
    private Integer progress = 0;
    //completed is a boolean value indicating whether the student has completed the course
    private Boolean completed = false;

    @ManyToOne
    private UserEntity student;

    @ManyToOne
    private Course course;



}
