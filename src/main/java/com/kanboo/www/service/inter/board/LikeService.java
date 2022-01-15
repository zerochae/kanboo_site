package com.kanboo.www.service.inter.board;

import com.kanboo.www.dto.board.LikesDTO;

public interface LikeService {
    LikesDTO insertLike(long boardIdx, long memIdx);

    int deleteLike(long boardIdx, long memIdx);
}
