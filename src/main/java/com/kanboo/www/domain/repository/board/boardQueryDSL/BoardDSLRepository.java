package com.kanboo.www.domain.repository.board.boardQueryDSL;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.Comment;
import com.kanboo.www.dto.board.BoardDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDSLRepository {

    List<BoardDTO> getAllList(String selected, String key, int articleOnvView, String codeDetail, String memId);

    long getArticleNum(String keyword, String selected, String codeDetails);

    List<Comment> getComments(long boardIdx, int commentsOnView);

    List<Board> findByPrjctIdxOnFive(Long projectIdx);

    List<Board> getAllQnaList(String selected, String key ,int articleOnView, String codeDetail);
}
