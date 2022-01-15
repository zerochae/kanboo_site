package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.idclass.ChatId;
import com.kanboo.www.dto.project.ChatDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat")
@Builder
@IdClass(ChatId.class)
public class Chat {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjctIdx")
    private Project project;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memIdx")
    private Member member;

    public ChatDTO entityToDto() {
        return ChatDTO.builder()
                .project(project.entityToDto())
                .member(member.entityToDto())
                .build();
    }
}
