package tn.jallouli.elite.modules.lesson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.jallouli.elite.modules.lesson.dto.LessonRequest;
import tn.jallouli.elite.modules.lesson.service.LessonInterface;
import tn.jallouli.elite.modules.lesson.service.LessonService;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonInterface lessonInterface;

    public LessonController(LessonInterface lessonInterface) {
        this.lessonInterface = lessonInterface;
    }

    @PostMapping("add/{idSection}")
    public ResponseEntity<String> addLesson(@PathVariable Long idSection, @RequestBody LessonRequest lessonRequest) {
        lessonInterface.createLesson(lessonRequest, idSection);
        return ResponseEntity.ok("Lesson added successfully");
    }

    @PutMapping("/update/{idLesson}")
    public ResponseEntity<String> updateLesson(@PathVariable Long idLesson, @RequestBody LessonRequest lessonRequest) {
        lessonInterface.updateLesson(idLesson, lessonRequest);
        return ResponseEntity.ok("Lesson updated successfully");
    }

    @DeleteMapping("/delete/{idLesson}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long idLesson) {
        lessonInterface.deleteLesson(idLesson);
        return ResponseEntity.ok("Lesson deleted successfully");
    }


}
