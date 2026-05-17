package tn.jallouli.elite.modules.sections.mapper;

import org.springframework.stereotype.Component;
import tn.jallouli.elite.modules.sections.dto.SectionRequest;
import tn.jallouli.elite.modules.sections.entity.Section;

@Component
public class SectionMapper {

    public Section toEntity(SectionRequest sectionRequest) {
        Section section = new Section();
        section.setTitle(sectionRequest.getTitle());
        section.setDescription(sectionRequest.getDescription());
        section.setPosition(sectionRequest.getPosition());
        return section;

    }
}
