package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Kanban;
import com.kanboo.www.domain.entity.project.KanbanItem;
import com.kanboo.www.domain.repository.project.dslsupport.KanbanDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanItemRepository extends JpaRepository<KanbanItem, Long>, KanbanDslRepository {
    KanbanItem findByKbItmIdx(Long kbItmIdx);
}
