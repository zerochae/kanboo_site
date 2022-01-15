package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.dto.global.CodeDetailDto;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDTO {

    private Long calIdx;
    private ProjectDTO project;
    private MemberDTO member;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime calStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime calEndDate;
    private String calColor;
    private CodeDetailDto codeDetail;
    private String calCn;
    private String calTitle;
    private String calDelAt;
    private String calIsAllDay;
    private String calIsDeletable;
    private String calIsResizable;

    public Calendar dtoToEntity() {
        return Calendar.builder()
                .calIdx(calIdx)
                .project(project.dtoToEntity())
                .member(member.dtoToEntity())
                .calStartDate(calStartDate)
                .calEndDate(calEndDate)
                .calColor(calColor)
                .codeDetail(codeDetail.dtoToEntity())
                .calCn(calCn)
                .calTitle(calTitle)
                .calDelAt(calDelAt)
                .calIsAllDay(calIsAllDay)
                .calIsDeletable(calIsDeletable)
                .calIsResizable(calIsResizable)
                .build();
    }
}
