package com.kanboo.www.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.BoardReport;
import com.kanboo.www.domain.entity.board.Comment;
import com.kanboo.www.domain.entity.board.Likes;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.global.CodeDetailDto;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long boardIdx;
    private MemberDTO member;
    private String boardCn;
    private LocalDateTime boardDate;
    private String delAt;
    private CodeDetailDto codeDetail;
    private String fileAt;
    private int totalComments;
    private int totalLikes;
    private BoardFileDTO boardFileDTO;
    private boolean isLike;
    private List<CommentDTO> commentList = new ArrayList<>();

    public void changeMember(long memberIdx){
        member = new MemberDTO();
        member.changeMemberIdx(memberIdx);
    }


    public Board dtoToEntity() {
        return Board.builder()
                .boardIdx(boardIdx)
                .member(member.dtoToEntity())
                .boardCn(boardCn)
                .boardDate(boardDate)
                .delAt(delAt)
                .codeDetail(codeDetail.dtoToEntity())
                .fileAt(fileAt)
                .totalComments(totalComments)
                .totalLikes(totalLikes)
                .boardFile(boardFileDTO.dtoToEntity())
                .build();
    }
}
