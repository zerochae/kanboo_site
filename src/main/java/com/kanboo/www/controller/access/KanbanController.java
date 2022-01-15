package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.KanbanItemDTO;
import com.kanboo.www.service.inter.project.KanbanItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kanban")
public class KanbanController {

    private final KanbanItemService kanbanItemService;

    @PostMapping("/insert")
    public KanbanItemDTO insertKanbanItem(KanbanItemDTO kanbanItemDTO){
        return kanbanItemService.insertKanbanItem(kanbanItemDTO);
    }

    @GetMapping("/getAll")
    public List<KanbanItemDTO> getAllKanbanItemsByMemIdxAndPrjctIdx(KanbanItemDTO kanbanItemDTO){
        return kanbanItemService.getAllKanbanItemsByMemIdxAndPrjctIdx(kanbanItemDTO);
    }

    @PostMapping("/update")
    public void updateKanbanItem(KanbanItemDTO kanbanItemDTO){
        kanbanItemService.updateKanbanItem(kanbanItemDTO);
    }

    @PostMapping("/delete")
    public void deleteKanbanItem(Long kbItmIdx){
        kanbanItemService.deleteKanbanItem(kbItmIdx);
    }

    @PostMapping("/save")
    public void saveKanbanItem(@RequestBody KanbanItemDTO kanbanItemDTO){

        kanbanItemService.saveKanbanItem(kanbanItemDTO);

    }
}
