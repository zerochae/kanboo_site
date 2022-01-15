package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.Chat;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private ProjectDTO project;
    private MemberDTO member;

    public Chat dtoToEntity() {
        return Chat.builder()
                .project(project.dtoToEntity())
                .member(member.dtoToEntity())
                .build();
    }
}
