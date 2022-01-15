package com.kanboo.www.domain.repository.board.boardQueryDSL;

import com.kanboo.www.domain.entity.board.*;
import com.kanboo.www.domain.entity.global.QCodeDetail;
import com.kanboo.www.domain.entity.global.QMasterCode;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.dto.board.BoardDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BoardDSLRepositoryImpl implements BoardDSLRepository{

    private final EntityManager em;
    private static final Logger logger
            = LoggerFactory.getLogger(BoardDSLRepositoryImpl.class);
//    private JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    @Override
    public List<BoardDTO> getAllList(String selected, String key, int articleOnvView, String codeDetails, String memId) {
        QBoard board = QBoard.board;
        QLikes likes = QLikes.likes;
        QBoardReport reports = QBoardReport.boardReport;
        QMember member = QMember.member;
        QCodeDetail codeDetail = QCodeDetail.codeDetail;
        QBoardFile boardFile = QBoardFile.boardFile;

        JPAQueryFactory query = new JPAQueryFactory(em);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(selected.equals("memNick")) {
            booleanBuilder.and(
                    board.member.memNick.contains(key)
            );
        } else if(selected.equals("boardCN")) {
            booleanBuilder.and(
                    board.boardCn.contains(key)
            );
        } else if(selected.equals("All")){
            booleanBuilder.or(board.member.memNick.contains(key))
                    .or(board.boardCn.contains(key));
        }

        List<Board> boardList = query
                .select(board)
                .from(board)
                .innerJoin(board.member, member)
                .fetchJoin()
                .innerJoin(board.codeDetail, codeDetail)
                .fetchJoin()
                .leftJoin(board.boardFile, boardFile)
                .fetchJoin()
                .where(
                        (JPAExpressions
                                .select(reports.count())
                                .from(reports)
                                .where(reports.board.boardIdx.eq(board.boardIdx))
                        ).lt(5L)
                                .and(board.delAt.eq("N"))
                                .and(board.codeDetail.codeDetailIdx.eq(codeDetails))
                                .and(booleanBuilder)
                )
                .distinct()
                .offset(articleOnvView).limit(5)
                .orderBy(board.boardIdx.desc())
                .fetch();


        List<BoardDTO> resultList = new ArrayList<>();

        boardList.forEach(item -> {
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardIdx(item.getBoardIdx())
                    .member(item.getMember().entityToDto())
                    .boardCn(item.getBoardCn())
                    .boardDate(item.getBoardDate())
                    .delAt(item.getDelAt())
                    .codeDetail(item.getCodeDetail().entityToDto())
                    .fileAt(item.getFileAt())
                    .totalComments(item.getTotalComments())
                    .totalLikes(item.getTotalLikes())
                    .boardFileDTO(item.getBoardFile() == null ? null : item.getBoardFile().entityToDto())
                    .build();

            Likes likeMember = query.selectFrom(likes)
                    .where(
                            likes.member.memId.eq(memId)
                                    .and(likes.board.boardIdx.eq(item.getBoardIdx()))
                    )
                    .fetchOne();
            if(likeMember != null) {
                boardDTO.setLike(true);
            }
            resultList.add(boardDTO);
        });
        return resultList;
    }

    @Override
    public long getArticleNum(String keyword, String selected, String codeDetails){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QBoard board = QBoard.board;
        QBoardReport reports = QBoardReport.boardReport;
        QMember member = QMember.member;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(selected.equals("memNick")){
            booleanBuilder.and(board.member.memNick.contains(keyword));
        } else if(selected.equals("boardCN")){
            booleanBuilder.and(board.boardCn.contains(keyword));
        } else if(selected.equals("All")){
            booleanBuilder.and(board.member.memNick.contains(keyword))
                    .or(board.boardCn.contains(keyword));
        }

        return query.select(board.count())
                .from(board, member)
                .where(board.codeDetail.codeDetailIdx.eq(codeDetails)
                        .and(board.member.memIdx.eq(member.memIdx))
                        .and(board.delAt.eq("N"))
                        .and(booleanBuilder)
                        .and((JPAExpressions
                                .select(reports.count())
                                .from(reports)
                                .where(reports.board.boardIdx.eq(board.boardIdx))
                        ).lt(5L))
                )
                .fetchCount();
    }

    @Override
    public List<Comment> getComments(long boardIdx, int commentsOnView) {
        QComment comment = QComment.comment;
        QBoard qBoard = QBoard.board;
        QMember qMember = QMember.member;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.select(comment)
                .from(comment)
                .innerJoin(comment.board, qBoard)
                .fetchJoin()
                .innerJoin(comment.member, qMember)
                .fetchJoin()
                .where(comment.board.boardIdx.eq(boardIdx))
                .offset(commentsOnView)
                .limit(5)
                .orderBy(comment.answerIdx.desc())
                .fetch();
    }

    @Override
    public List<Board> findByPrjctIdxOnFive(Long projectIdx) {
        QBoard board = QBoard.board;
        QProjectBoard projectBoard = QProjectBoard.projectBoard;

        JPAQuery<Board> query = new JPAQuery<>(em);

        return query.from(projectBoard, board)
                .where(projectBoard.project.prjctIdx.eq(projectIdx))
                .orderBy(board.boardDate.desc())
                .offset(0) .limit(6)
                .fetch();
    }

    @Override
    public List<Board> getAllQnaList(String selected, String key, int articleOnView, String codeDetails) {
        QBoard board = QBoard.board;
        QComment comment = QComment.comment;

        JPAQueryFactory query = new JPAQueryFactory(em);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        switch (selected) {
            case "memNick":
                booleanBuilder.and(board.member.memNick.contains(key));
                break;
            case "boardCn":
                booleanBuilder.and(board.boardCn.contains(key));
                break;
            case "All":
                booleanBuilder.or(board.member.memNick.contains(key)).or(board.boardCn.contains(key));
                break;
        }

        return query
                .selectFrom(board).distinct()
                .leftJoin(board.commentList, comment)
                .fetchJoin()
                .where((board.delAt.eq("N")
                        .and(board.codeDetail.codeDetailIdx.eq(codeDetails))
                        .and(booleanBuilder))
                )
                .orderBy(board.boardDate.desc())
                .offset(articleOnView).limit(5)
                .fetch();
    }

}
