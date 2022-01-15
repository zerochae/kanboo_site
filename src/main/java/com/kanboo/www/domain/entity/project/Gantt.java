package com.kanboo.www.domain.entity.project;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.project.GanttDTO;
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
@Table(name = "gantt")
@Builder
public class Gantt {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gtIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    private String gtState;
    private String gtPriority;
    private int gtProgress;
    private LocalDateTime gtStartDate;
    private LocalDateTime gtEndDate;
    private  String gtExplanation;
    private String gtTitle;

    public GanttDTO entityToDto() {
        return GanttDTO.builder()
                .gtIdx(gtIdx)
                .project(project.entityToDto())
                .member(member.entityToDto())
                .gtState(gtState)
                .gtPriority(gtPriority)
                .gtProgress(gtProgress)
                .gtStartDate(gtStartDate)
                .gtEndDate(gtEndDate)
                .gtExplanation(gtExplanation)
                .gtTitle(gtTitle)
                .build();
    }

    public void changeGtState(String gtState) {
        this.gtState = gtState;
    }

    public void changeGtPriority(String gtPriority) {
        this.gtPriority = gtPriority;
    }

    public void changeGtProgress(int gtProgress) {
        this.gtProgress = gtProgress;
    }

    public void changeGtStartDate(LocalDateTime gtStartDate) {
        this.gtStartDate = gtStartDate;
    }

    public void changeGtEndDate(LocalDateTime gtEndDate) {
        this.gtEndDate = gtEndDate;
    }

    public void changeGtExplanation(String gtExplanation) {
        this.gtExplanation = gtExplanation;
    }

    public void changeGtTitle(String gtTitle) {
        this.gtTitle = gtTitle;
    }

}
