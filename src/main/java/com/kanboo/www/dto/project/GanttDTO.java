package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.Gantt;
import com.kanboo.www.domain.entity.project.Project;
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
public class GanttDTO {

    private Long gtIdx;
    private ProjectDTO project;
    private MemberDTO member;
    private String gtState;
    private String gtPriority;
    private int gtProgress;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gtStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gtEndDate;
    private  String gtExplanation;
    private String gtTitle;

    public Gantt dtoToEntity() {
        return Gantt.builder()
                .gtIdx(gtIdx)
                .project(project.dtoToEntity())
                .member(member.dtoToEntity())
                .gtState(gtState)
                .gtPriority(gtPriority)
                .gtProgress(gtProgress)
                .gtStartDate(gtStartDate)
                .gtEndDate(gtEndDate)
                .gtExplanation(gtExplanation)
                .gtTitle(gtTitle)
                .build();
    }
}
