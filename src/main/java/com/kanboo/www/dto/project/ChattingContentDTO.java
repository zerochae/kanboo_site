package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.ChattingContent;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChattingContentDTO {

    private Long chatContentIdx;
    private MemberDTO member;
    private ChatDTO chat;
    private String chatCn;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime chatCnDate;

    public ChattingContent dtoToEntity() {
        return ChattingContent.builder()
                .chatContentIdx(chatContentIdx)
                .chat(chat.dtoToEntity())
                .chatCn(chatCn)
                .chatCnDate(chatCnDate)
                .build();
    }
}
