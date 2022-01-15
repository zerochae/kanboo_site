package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.project.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
@Builder
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ntcnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    private String ntcnCn;
    private String ntcnAt;
    private LocalDateTime ntcnDate;

    @OneToOne
    @JoinColumn(name = "code_detail_idx")
    private CodeDetail codeDetail;

    public NotificationDTO entityToDto() {
        return NotificationDTO.builder()
                .ntcnIdx(ntcnIdx)
                .member(member.entityToDto())
                .project(project.entityToDto())
                .ntcnCn(ntcnCn)
                .ntcnAt(ntcnAt)
                .ntcnDate(ntcnDate)
                .codeDetail(codeDetail.entityToDto())
                .build();
    }
}
