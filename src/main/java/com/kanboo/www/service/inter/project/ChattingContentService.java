package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.ChatDTO;
import com.kanboo.www.dto.project.ChattingContentDTO;

import java.util.List;

public interface ChattingContentService {
    ChattingContentDTO insertChatLog(ChattingContentDTO chattingContentDTO);

    List<ChattingContentDTO> getAllChat(ChattingContentDTO chattingContentDTO);
}
