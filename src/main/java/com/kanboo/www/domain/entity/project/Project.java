package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
@Builder
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prjctIdx;

    private String prjctNm;
    private LocalDate prjctStartDate;
    private LocalDate prjctEndDate;
    private int prjctProgress;
    private String prjctDelAt;
    private String prjctComplAt;
    private String prjctReadMe;
    private String prjctManager;

    @OneToMany(mappedBy = "project")
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Issue> issueList = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Calendar> calendarList = new ArrayList<>();

    public void changePrjctDelAt(String prjctDelAt){
        this.prjctDelAt = prjctDelAt;
    }

    public void changePrjctComplAt(String prjctComplAt){
        this.prjctComplAt = prjctComplAt;
    }

    public ProjectDTO entityToDto() {

        return ProjectDTO.builder()
                .prjctIdx(prjctIdx)
                .prjctNm(prjctNm)
                .prjctStartDate(prjctStartDate)
                .prjctEndDate(prjctEndDate)
                .prjctProgress(prjctProgress)
                .prjctDelAt(prjctDelAt)
                .prjctComplAt(prjctComplAt)
                .prjctReadMe(prjctReadMe)
                .prjctManager(prjctManager)
                .build();
    }

    public void updateReadMe(String prjctReadMe) {
        this.prjctReadMe = prjctReadMe;
    }

    public void changeIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public void changeCalendarList(List<Calendar> calendarList) {
        this.calendarList = calendarList;
    }
}