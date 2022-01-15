package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.ProjectBoard;
import com.kanboo.www.domain.entity.project.idclass.BoardGanttId;
import com.kanboo.www.dto.project.BoardGanttDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BoardGanttId.class)
public class BoardGantt {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gt_idx")
    private Gantt gantt;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "project", referencedColumnName = "prjct_idx"),
            @JoinColumn(name = "board", referencedColumnName = "board_idx")
    })
    private ProjectBoard projectBoard;

    public BoardGanttDTO entityToDto() {
        return BoardGanttDTO.builder()
                .gantt(gantt.entityToDto())
                .projectBoard(projectBoard.entityToDto())
                .build();
    }
}
