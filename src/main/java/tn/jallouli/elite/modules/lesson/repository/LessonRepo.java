package tn.jallouli.elite.modules.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.jallouli.elite.modules.lesson.entity.Lesson;

public interface LessonRepo extends JpaRepository<Lesson,Long> {
}
