package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Kanban;
import com.kanboo.www.domain.entity.project.KanbanItem;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KanbanItemDTO {

    private Long kbItmIdx;
    private KanbanDTO kanban;
    private MemberDTO member;
    private String kbCn;
    private String kbItmNum;

    private String kbBadge;
    private String kbColor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime kbStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime kbEndDate;

    public KanbanItem dtoToEntity() {
        return KanbanItem.builder()
                .kbItmIdx(kbItmIdx)
                .kanban(kanban.dtoToEntity())
                .member(member.dtoToEntity())
                .kbCn(kbCn)
                .kbItmNum(kbItmNum)
                .kbStartDate(kbStartDate)
                .kbEndDate(kbEndDate)
                .kbBadge(kbBadge)
                .kbColor(kbColor)
                .build();
    }
}
