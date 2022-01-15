package com.kanboo.www.domain.entity.project.idclass;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatId implements Serializable {

    private Member member;
    private Project project;
}
