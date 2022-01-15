package com.kanboo.www.domain.repository.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.Comment;
import com.kanboo.www.domain.repository.board.boardQueryDSL.BoardDSLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardDSLRepository {
    long getArticleNum(String keyword, String selected, String codeDetails);

    List<Comment> getComments(long boardIdx, int commentsOnView);

    Board findByBoardIdx(long boardIdx);

    long deleteByBoardIdx(long boardIdx);

    List<Board> findByCodeDetail_CodeDetailIdx(String codeDetail);
}
