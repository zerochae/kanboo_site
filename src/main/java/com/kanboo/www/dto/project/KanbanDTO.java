package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.Kanban;
import com.kanboo.www.domain.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KanbanDTO {

    private Long kbIdx;
    private ProjectDTO project;

    public Kanban dtoToEntity() {
        return Kanban.builder()
                .kbIdx(kbIdx)
                .project(project.dtoToEntity())
                .build();
    }
}
