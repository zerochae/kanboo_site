package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.project.CalendarDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calendar")
@Builder
public class Calendar {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    private LocalDateTime calStartDate;
    private LocalDateTime calEndDate;
    private String calColor;

    @OneToOne
    @JoinColumn(name = "code_detail_idx")
    private CodeDetail codeDetail;

    private String calCn;
    private String calTitle;
    private String calDelAt;
    private String calIsAllDay;
    private String calIsDeletable;
    private String calIsResizable;


    public void changeStartDate(LocalDateTime calStartDate) {
        this.calStartDate = calStartDate;
    }

    public void changeEndDate(LocalDateTime calEndDate) {
        this.calEndDate = calEndDate;
    }

    public void changeTitle(String calTitle) {
        this.calTitle = calTitle;
    }

    public void changeDelAt(String calDelAt) {
        this.calDelAt = calDelAt;
    }

    public CalendarDTO entityToDto() {
        return CalendarDTO.builder()
                .calIdx(calIdx)
                .project(project.entityToDto())
                .member(member.entityToDto())
                .calStartDate(calStartDate)
                .calEndDate(calEndDate)
                .calColor(calColor)
                .codeDetail(codeDetail.entityToDto())
                .calCn(calCn)
                .calTitle(calTitle)
                .calDelAt(calDelAt)
                .calIsAllDay(calIsAllDay)
                .calIsDeletable(calIsDeletable)
                .calIsResizable(calIsResizable)
                .build();
    }
}
