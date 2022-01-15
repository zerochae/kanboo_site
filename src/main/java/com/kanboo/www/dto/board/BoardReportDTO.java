package com.kanboo.www.dto.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.BoardReport;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardReportDTO {

    private Long boardReportIdx;
    private BoardDTO board;
    private MemberDTO member;
    private String boardReportResn;

    public BoardReport dtoToEntity() {
        return BoardReport.builder()
                .boardReportIdx(boardReportIdx)
                .board(board.dtoToEntity())
                .member(member.dtoToEntity())
                .boardReportResn(boardReportResn)
                .build();
    }
}
