package com.kanboo.www.domain.entity.board;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.board.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
@Builder
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    private String answerCn;
    private LocalDateTime answerDate;
    private String answerDelAt;

    public CommentDTO entityToDto() {
        return CommentDTO.builder()
                .answerIdx(answerIdx)
                .board(board.entityToDto())
                .member(member.entityToDto())
                .answerCn(answerCn)
                .answerDate(answerDate)
                .answerDelAt(answerDelAt)
                .build();
    }
}
