package com.kanboo.www.domain.repository.board.boardFileQueryDSL;

import com.kanboo.www.domain.entity.board.BoardFile;
import com.kanboo.www.domain.entity.board.QBoard;
import com.kanboo.www.domain.entity.board.QBoardFile;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class BoardFileDSLRepositoryImpl implements BoardFileDSLRepository{
    private final EntityManager em;

    @Override
    public BoardFile getPresentFile(long boardIdx) {
        QBoard board = QBoard.board;
        QBoardFile boardFile = QBoardFile.boardFile;
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.select(boardFile)
                .from(boardFile)
                .where(boardFile.board.boardIdx.eq(boardIdx))
                .fetchOne();

    }

    @Override
    public long deleteFile(long boardIdx) {
        QBoardFile boardFile = QBoardFile.boardFile;
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.delete(boardFile)
                .where(boardFile.board.boardIdx.eq(boardIdx))
                .execute();
    }


}
