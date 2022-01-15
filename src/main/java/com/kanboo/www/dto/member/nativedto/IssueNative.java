package com.kanboo.www.dto.member.nativedto;

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
public class IssueNative {

    private Long issueIdx;
    private String issueCn;
    private Date issueDate;
    private String issueState;
    private String issueGitFile;
}
