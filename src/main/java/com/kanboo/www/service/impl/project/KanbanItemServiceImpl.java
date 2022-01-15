package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Kanban;
import com.kanboo.www.domain.entity.project.KanbanItem;
import com.kanboo.www.domain.repository.project.KanbanItemRepository;
import com.kanboo.www.domain.repository.project.KanbanRepository;
import com.kanboo.www.dto.project.KanbanItemDTO;
import com.kanboo.www.service.inter.project.KanbanItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KanbanItemServiceImpl implements KanbanItemService {

    private final KanbanItemRepository kanbanItemRepository;
    private final KanbanRepository kanbanRepository;

    @Override
    public KanbanItemDTO insertKanbanItem(KanbanItemDTO itemDTO) {
        Kanban kanban = kanbanRepository.findByProjectPrjctIdx(itemDTO.getKanban().getProject().getPrjctIdx());
        Member member = Member.builder().memIdx(itemDTO.getMember().getMemIdx()).build();

        KanbanItem kanbanItem = KanbanItem.builder()
                .kbBadge(itemDTO.getKbBadge())
                .kbCn(itemDTO.getKbCn())
                .kbColor(itemDTO.getKbColor())
                .kbStartDate(itemDTO.getKbStartDate())
                .kbEndDate(itemDTO.getKbEndDate())
                .kbItmNum(itemDTO.getKbItmNum())
                .member(member)
                .kanban(kanban)
                .build();
        KanbanItem save = kanbanItemRepository.save(kanbanItem);
        itemDTO.setKbItmIdx(save.getKbItmIdx());
        return itemDTO;
    }

    @Override
    public List<KanbanItemDTO> getAllKanbanItemsByMemIdxAndPrjctIdx(KanbanItemDTO kanbanItemDTO) {
        List<KanbanItem> list = kanbanItemRepository.getAllItemsByPrjctIdx(kanbanItemDTO.getKanban().getProject().getPrjctIdx());
        List<KanbanItemDTO> dtoList = new ArrayList<>();
        for (KanbanItem item : list) {
            dtoList.add(item.entityToDto());
        }
        return dtoList;
    }

    @Override
    @Transactional
    public void updateKanbanItem(KanbanItemDTO kanbanItemDTO) {
        KanbanItem kanbanItem = kanbanItemRepository.findByKbItmIdx(kanbanItemDTO.getKbItmIdx());
        kanbanItem.changeKbBadge(kanbanItemDTO.getKbBadge());
        kanbanItem.changeKbCn(kanbanItemDTO.getKbCn());
        kanbanItem.changeKbColor(kanbanItemDTO.getKbColor());
        kanbanItem.changeKbStartDate(kanbanItemDTO.getKbStartDate());
        kanbanItem.changeKbEndDate(kanbanItemDTO.getKbEndDate());
        kanbanItem.changeKbItmNum(kanbanItemDTO.getKbItmNum());
    }

    @Override
    public void deleteKanbanItem(Long kbItmIdx) {
        kanbanItemRepository.deleteById(kbItmIdx);
    }

    @Override
    public void saveKanbanItem(KanbanItemDTO kanbanItemDTO) {

        KanbanItem kanbanItem = kanbanItemRepository.findByKbItmIdx(kanbanItemDTO.getKbItmIdx());

        kanbanItem.changeKbItmNum(kanbanItemDTO.getKbItmNum());

        kanbanItemRepository.save(kanbanItem);
    }

}
