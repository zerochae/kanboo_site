package com.kanboo.www.domain.entity.project.idclass;

import com.kanboo.www.domain.entity.board.idclass.ProjectBoardId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardGanttId implements Serializable {

    private Long gantt;
    private ProjectBoardId projectBoard;
}
