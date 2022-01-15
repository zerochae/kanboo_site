package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Notification;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.dto.global.CodeDetailDto;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long ntcnIdx;
    private MemberDTO member;
    private ProjectDTO project;
    private String ntcnCn;
    private String ntcnAt;
    private LocalDateTime ntcnDate;
    private CodeDetailDto codeDetail;

    public Notification dtoToEntity() {
        return Notification.builder()
                .ntcnIdx(ntcnIdx)
                .member(member.dtoToEntity())
                .project(project.dtoToEntity())
                .ntcnCn(ntcnCn)
                .ntcnAt(ntcnAt)
                .ntcnDate(ntcnDate)
                .codeDetail(codeDetail.dtoToEntity())
                .build();
    }
}
