package com.kanboo.www.service.inter.board;

import com.kanboo.www.dto.board.CommentDTO;

public interface CommentService {
    CommentDTO insertComment(CommentDTO comment);
}
