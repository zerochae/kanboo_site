package com.kanboo.www.service.impl.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.Comment;
import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.repository.board.CommentRepository;
import com.kanboo.www.dto.board.CommentDTO;
import com.kanboo.www.service.inter.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentDTO insertComment(CommentDTO commentDTO) {
        Member member = Member.builder()
                .memIdx(commentDTO.getMember().getMemIdx())
                .memNick(commentDTO.getMember().getMemNick())
                .build();
        Board board = Board.builder()
                .boardIdx(commentDTO.getBoard().getBoardIdx())
                .likesList(new ArrayList<>())
                .member(new Member())
                .codeDetail(new CodeDetail())
                .build();

        Comment comment = Comment.builder()
                .answerCn(commentDTO.getAnswerCn())
                .answerDate(commentDTO.getAnswerDate())
                .answerDelAt(commentDTO.getAnswerDelAt())
                .board(board)
                .member(member)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return savedComment.entityToDto();
    }
}
