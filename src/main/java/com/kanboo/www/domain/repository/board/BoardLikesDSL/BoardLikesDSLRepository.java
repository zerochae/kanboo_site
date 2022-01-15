package com.kanboo.www.domain.repository.board.BoardLikesDSL;

import com.kanboo.www.domain.entity.board.Likes;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikesDSLRepository {
    Likes checkLikes(long boardIdx, long memIdx);
}
