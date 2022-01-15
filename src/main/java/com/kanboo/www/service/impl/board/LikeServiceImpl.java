package com.kanboo.www.service.impl.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.Likes;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.repository.board.LikeRepository;
import com.kanboo.www.dto.board.LikesDTO;
import com.kanboo.www.service.inter.board.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public LikesDTO insertLike(long boardIdx, long memIdx) {
        Board board = Board.builder().boardIdx(boardIdx).build();
        Member member = Member.builder().memIdx(memIdx).build();

        Likes likes = Likes.builder()
                .board(board)
                .member(member)
                .build();
        Likes savedLike = likeRepository.save(likes);

        return LikesDTO.builder()
                .likeIdx(savedLike.getLikeIdx())
                .build();
    }

    @Override
    @Transactional
    public int deleteLike(long boardIdx, long memIdx) {
        try {
            return likeRepository.deleteByMember_MemIdxAndAndBoard_BoardIdx(memIdx, boardIdx);
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
