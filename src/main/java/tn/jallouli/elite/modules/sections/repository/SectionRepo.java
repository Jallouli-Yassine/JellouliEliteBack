package tn.jallouli.elite.modules.sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.jallouli.elite.modules.sections.entity.Section;

public interface SectionRepo extends JpaRepository<Section,Long> {
}
