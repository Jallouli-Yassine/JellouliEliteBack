package tn.jallouli.elite.modules.sections.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.jallouli.elite.modules.sections.dto.SectionRequest;
import tn.jallouli.elite.modules.sections.service.SectionInterface;

@RestController
@RequestMapping("/sections")
public class SectionController {
    private final SectionInterface sectionInterface;

    public SectionController(SectionInterface sectionInterface) {
        this.sectionInterface = sectionInterface;
    }

    @PostMapping("/add/{idCourse}")
    public ResponseEntity<String> addSection(@RequestBody SectionRequest request, @PathVariable Long idCourse) {
        sectionInterface.createSection(request, idCourse);
        return ResponseEntity.ok("Section added successfully");
    }

    @PutMapping("/update/{idSection}")
    public ResponseEntity<String> updateSection(@RequestBody SectionRequest request, @PathVariable Long idSection) {
        sectionInterface.updateSection(idSection, request);
        return ResponseEntity.ok("Section updated successfully");
    }
    @DeleteMapping("/delete/{idSection}")
    public ResponseEntity<String> deleteSection(@PathVariable Long idSection) {
        sectionInterface.deleteSection(idSection);
        return ResponseEntity.ok("Section deleted successfully");
    }

}
