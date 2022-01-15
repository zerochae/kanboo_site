package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.KanbanItemDTO;

import java.util.List;

public interface KanbanItemService {
    KanbanItemDTO insertKanbanItem(KanbanItemDTO kanbanItemDTO);

    List<KanbanItemDTO> getAllKanbanItemsByMemIdxAndPrjctIdx(KanbanItemDTO kanbanItemDTO);

    void deleteKanbanItem(Long kbItmIdx);

    void updateKanbanItem(KanbanItemDTO kanbanItemDTO);

    void saveKanbanItem(KanbanItemDTO kanbanItemDTO);
}
