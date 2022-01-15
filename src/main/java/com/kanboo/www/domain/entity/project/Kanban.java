package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.KanbanDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kanban")
@Builder
public class Kanban {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kbIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    public KanbanDTO entityToDto() {
        return KanbanDTO.builder()
                .kbIdx(kbIdx)
                .project(project.entityToDto())
                .build();
    }
}
