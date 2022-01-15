package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.entity.project.CalendarCheck;
import com.kanboo.www.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarCheckDTO {

    private Long calChkIdx;
    private CalendarDTO calendar;
    private MemberDTO member;

    public CalendarCheck dtoToEntity() {
        return CalendarCheck.builder()
                .calChkIdx(calChkIdx)
                .calendar(calendar.dtoToEntity())
                .member(member.dtoToEntity())
                .build();
    }
}
