package com.kanboo.www.domain.repository.board.BoardLikesDSL;

import com.kanboo.www.domain.entity.board.Likes;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class BoardLikesDSLRepositoryImpl implements BoardLikesDSLRepository {
    private final EntityManager em;

    @Override
    public Likes checkLikes(long boardIdx, long memIdx) {

        return null;
    }
}
