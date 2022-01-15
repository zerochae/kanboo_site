package com.kanboo.www.dto.board;

import com.kanboo.www.domain.entity.board.Comment;
import com.kanboo.www.domain.entity.board.CommentReport;
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
public class CommentReportDTO {

    private Long answerReportIdx;
    private CommentDTO comment;
    private MemberDTO member;
    private String answerReportResn;

    public CommentReport dtoToEntity() {
        return CommentReport.builder()
                .answerReportIdx(answerReportIdx)
                .comment(comment.dtoToEntity())
                .member(member.dtoToEntity())
                .answerReportResn(answerReportResn)
                .build();
    }
}
