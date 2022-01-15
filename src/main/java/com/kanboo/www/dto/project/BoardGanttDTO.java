package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.BoardGantt;
import com.kanboo.www.dto.board.ProjectBoardDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardGanttDTO {

    private GanttDTO gantt;
    private ProjectBoardDTO projectBoard;

    public BoardGantt dtoToEntity() {
        return BoardGantt.builder()
                .gantt(gantt.dtoToEntity())
                .projectBoard(projectBoard.dtoToEntity())
                .build();
    }
}
