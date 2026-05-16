package tn.jallouli.elite.modules.sections.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tn.jallouli.elite.modules.course.entity.Course;
import tn.jallouli.elite.modules.lesson.entity.Lesson;

import java.util.List;

@Entity
@Table(name = "sections")
@Getter
@Setter
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String title;
    private Integer position;
    private String description;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;
}
