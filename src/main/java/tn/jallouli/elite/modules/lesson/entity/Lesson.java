package tn.jallouli.elite.modules.lesson.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.jallouli.elite.modules.sections.entity.Section;

@Getter
@Setter
@Table(name = "lessons")
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String title;
    private String description;
    private String videoUrl;
    private String duration;
    private boolean isPreview;
    private int position;

    @ManyToOne
    private Section section;



}
