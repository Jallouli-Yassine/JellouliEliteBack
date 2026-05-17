package tn.jallouli.elite.modules.sections.service;


import tn.jallouli.elite.modules.sections.dto.SectionRequest;

public interface SectionInterface {
    void createSection(SectionRequest sectionRequest, Long idCourse);
    void updateSection(Long idSection,SectionRequest sectionRequest);
    void deleteSection(Long idSection);
}
