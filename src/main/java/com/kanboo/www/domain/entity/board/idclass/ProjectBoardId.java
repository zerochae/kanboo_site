package com.kanboo.www.domain.entity.board.idclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProjectBoardId implements Serializable {

    private Long board;
    private Long project;
}
