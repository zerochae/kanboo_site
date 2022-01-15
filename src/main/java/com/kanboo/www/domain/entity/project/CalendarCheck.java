package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.project.CalendarCheckDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calendar_check")
@Builder
public class CalendarCheck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calChkIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cal_idx")
    private Calendar calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    public CalendarCheckDTO entityToDto() {
        return CalendarCheckDTO.builder()
                .calChkIdx(calChkIdx)
                .calendar(calendar.entityToDto())
                .member(member.entityToDto())
                .build();
    }
}
