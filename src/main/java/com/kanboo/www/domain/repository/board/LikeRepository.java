package com.kanboo.www.domain.repository.board;

import com.kanboo.www.domain.entity.board.Likes;
import com.kanboo.www.domain.repository.board.BoardLikesDSL.BoardLikesDSLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long>, BoardLikesDSLRepository {
    int deleteByMember_MemIdxAndAndBoard_BoardIdx(Long memIdx, Long boardIdx);
}
