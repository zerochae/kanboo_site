package com.kanboo.www.domain.entity.board;

import com.kanboo.www.domain.entity.board.idclass.ProjectBoardId;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.dto.board.ProjectBoardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_board")
@IdClass(ProjectBoardId.class)
@Builder
public class ProjectBoard {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    public ProjectBoardDTO entityToDto() {
        return ProjectBoardDTO.builder()
                .board(board.entityToDto())
                .project(project.entityToDto())
                .build();
    }
}
