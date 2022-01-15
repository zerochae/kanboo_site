package com.kanboo.www.service.impl.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.Comment;
import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.repository.board.BoardRepository;
import com.kanboo.www.domain.repository.board.boardQueryDSL.BoardDSLRepositoryImpl;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.board.CommentDTO;
import com.kanboo.www.service.inter.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<BoardDTO> getAllList(String selected, String key, int articleOnvView, String codeDetail, String memTag) {

        Member member = memberRepository.findByMemTag(memTag);
        if (member == null){
            return null;
        }
        return boardRepository.getAllList(selected, key, articleOnvView, codeDetail, member.getMemId());
    }

    @Override
    public long getArticleNum(String keyword, String selected, String codeDetails) {
        return boardRepository.getArticleNum(keyword, selected, codeDetails);
    }

    @Override
    public List<CommentDTO> getComments(long boardIdx, int commentsOnView) {
        List<Comment> comments = boardRepository.getComments(boardIdx, commentsOnView);
        List<CommentDTO> list = new ArrayList<>();

        for (Comment comment : comments) {
            list.add(comment.entityToDto());
        }
        return list;
    }

    @Transactional
    @Override
    public boolean deleteBoard(long boardIdx) {
        Board board = boardRepository.findByBoardIdx(boardIdx);

        if (board != null) {
            board.changeDelAt("Y");
            return true;
        }

        return false;
    }

    @Override
    public BoardDTO insertBoard(BoardDTO boardDTO, long memIdx) {
        //원래는 이부분에서 token값을 받아서 session에서 조회 한후 dto 꺼내서 memberIdx 꺼냄
        boardDTO.changeMember(memIdx);

        Member member = memberRepository.findByMemIdx(memIdx);
        CodeDetail codeDetail = CodeDetail.builder()
                .codeDetailIdx(boardDTO.getCodeDetail().getCodeDetailIdx())
                .build();

        Board board = Board.builder()
                .member(member)
                .codeDetail(codeDetail)
                .boardCn(boardDTO.getBoardCn())
                .boardDate(LocalDateTime.now())
                .delAt(boardDTO.getDelAt())
                .fileAt(boardDTO.getFileAt())
                .totalComments(boardDTO.getTotalComments())
                .totalLikes(boardDTO.getTotalLikes())
                .likesList(new ArrayList<>())
                .build();

        Board savedBoard = boardRepository.save(board);
        return savedBoard.entityToDto();
    }

    @Transactional
    @Override
    public BoardDTO updateBoard(Map<String, Object> map) {
        long boardIdx = Long.parseLong(String.valueOf(map.get("boardIdx")));
        Board board = boardRepository.findByBoardIdx(boardIdx);

        if(board != null){
            board.changeBoardCn((String)map.get("boardCn"));
            board.changeFileAt((String)map.get("fileAt"));
            Board updatedBoard = boardRepository.save(board);
            return updatedBoard.entityToDto();
        }
        return null;
    }

    @Override
    public BoardDTO updateLikes(long boardIdx) {
        Board caughtBoard = boardRepository.findByBoardIdx(boardIdx);
        if(caughtBoard != null){
            caughtBoard.increaseLikes();
            Board save = boardRepository.save(caughtBoard);
            return save.entityToDto();
        }
        return null;
    }

    @Override
    public BoardDTO decreaseLikesNum(long boardIdx) {
        Board board = boardRepository.findByBoardIdx(boardIdx);
        if(board != null){
            board.decreaseLikes();
            Board save = boardRepository.save(board);
            return save.entityToDto();
        }
        return null;
    }

    @Override
    public BoardDTO increaseTotalComments(long boardIdx) {
        Board byBoardIdx = boardRepository.findByBoardIdx(boardIdx);
        if(byBoardIdx != null){
            byBoardIdx.increaseTotalComments();
            Board save = boardRepository.save(byBoardIdx);
            return save.entityToDto();
        }
        return null;
    }

    @Override
    public List<BoardDTO> getProjectLastest(Long projectIdx) {
        List<Board> boards = boardRepository.findByPrjctIdxOnFive(projectIdx);
        List<BoardDTO> boardDTOS = new ArrayList<>();
        boards.forEach(item -> {
            boardDTOS.add(item.entityToDto());
        });

        return boardDTOS;
    }

    @Override
    public List<BoardDTO> getAllQnaList(String selected, String key ,int articleOnView, String codeDetail) {

        List<Board> all = boardRepository.getAllQnaList(selected,key,articleOnView,codeDetail);
        List<BoardDTO> result = new ArrayList<>();
        for(Board b : all){
            BoardDTO board = BoardDTO.builder()
                    .boardIdx(b.getBoardIdx())
                    .member(b.getMember().entityToDto())
                    .boardCn(b.getBoardCn())
                    .boardDate(b.getBoardDate())
                    .delAt(b.getDelAt())
                    .commentList(new ArrayList<>())
                    .build();

            List<Comment> commentList = b.getCommentList();
            if(commentList.size() > 0) {
                commentList.forEach(item -> {
                    CommentDTO comment = CommentDTO.builder()
                            .answerIdx(item.getAnswerIdx())
                            .answerCn(item.getAnswerCn())
                            .answerDate(item.getAnswerDate())
                            .answerDelAt(item.getAnswerDelAt())
                            .build();
                    board.getCommentList().add(comment);
                });
            }
            result.add(board);
        }
        return result;
    }
}
