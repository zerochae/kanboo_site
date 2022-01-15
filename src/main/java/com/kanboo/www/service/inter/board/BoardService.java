package com.kanboo.www.service.inter.board;


import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.Comment;
import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.board.CommentDTO;

import java.util.List;
import java.util.Map;

public interface BoardService {
    List<BoardDTO> getAllList(String selected, String key, int articleOnvView, String codeDetail, String memTag);

    long getArticleNum(String keyword, String selected, String codeDetails);

    List<CommentDTO> getComments(long boardIdx, int commentsOnView);

    boolean deleteBoard(long boardIdx);

    BoardDTO insertBoard(BoardDTO boardDTO, long memIdx);

    BoardDTO updateBoard(Map<String, Object> map);

    BoardDTO updateLikes(long boardIdx);

    BoardDTO decreaseLikesNum(long boardIdx);

    BoardDTO increaseTotalComments(long boardIdx);

    List<BoardDTO> getProjectLastest(Long projectIdx);

    List<BoardDTO> getAllQnaList(String selected, String key ,int articleOnView, String codeDetail);
}
