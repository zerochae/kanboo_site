package com.kanboo.www.dto.member.nativedto;

import com.kanboo.www.dto.global.CodeDetailDto;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarNative {

    private Long calIdx;
    private Date calStartDate;
    private Date calEndDate;
    private String calColor;
    private String calCn;
    private String calTitle;
    private String calDelAt;
    private String calIsAllDay;
    private String calIsDeletable;
    private String calIsResizable;
}
