package com.kanboo.www.dto.board;

import com.kanboo.www.domain.entity.board.Likes;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikesDTO {

    private Long likeIdx;
    private BoardDTO board;
    private MemberDTO member;

    public Likes dtoToEntity() {
        return Likes.builder()
                .likeIdx(likeIdx)
                .board(board.dtoToEntity())
                .member(member.dtoToEntity())
                .build();
    }
}
