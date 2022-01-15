package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Chat;
import com.kanboo.www.domain.entity.project.ChattingContent;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.ChattingContentRepository;
import com.kanboo.www.domain.repository.project.ChattingRepository;
import com.kanboo.www.domain.repository.project.ProjectMemberRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.project.ChatDTO;
import com.kanboo.www.dto.project.ChattingContentDTO;
import com.kanboo.www.service.inter.project.ChattingContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingContentServiceImpl implements ChattingContentService {

    private final ChattingContentRepository chattingContentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ChattingRepository chattingRepository;

    @Override
    public ChattingContentDTO insertChatLog(ChattingContentDTO chattingContentDTO) {
        Chat chat = chattingRepository.findByMember_MemIdx(chattingContentDTO.getChat().getMember().getMemIdx());
        ChattingContent chattingContent = ChattingContent.builder()
                .chat(chat)
                .chatCn(chattingContentDTO.getChatCn())
                .chatCnDate(chattingContentDTO.getChatCnDate())
                .build();
        return chattingContentRepository.save(chattingContent).entityToDto();
    }

    @Override
    public List<ChattingContentDTO> getAllChat(ChattingContentDTO chattingContentDTO) {
        List<ChattingContent> returnList = chattingContentRepository.findAllByPrjctIdxAndMemIdx(
                chattingContentDTO.getChat().getProject().getPrjctIdx());
        List<ChattingContentDTO> dtoList = new ArrayList<>();
        for (ChattingContent chattingContent : returnList) {
            dtoList.add( chattingContent.entityToDto() );
        }
        return dtoList;
    }

}
