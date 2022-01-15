package com.kanboo.www.domain.entity.board;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.board.BoardReportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_report")
@Builder
public class BoardReport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardReportIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    private String boardReportResn;

    public BoardReportDTO entityToDto() {
        return BoardReportDTO.builder()
                .boardReportIdx(boardReportIdx)
                .board(board.entityToDto())
                .member(member.entityToDto())
                .boardReportResn(boardReportResn)
                .build();
    }
}
