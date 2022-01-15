package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.project.IssueDTO;
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
@Table(name = "issue")
@Builder
public class Issue {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    private String issueCn;
    private LocalDateTime issueDate;
    private String issueState;
    private String issueGitFile;

    public void changeIssueState(String issueState) {
        this.issueState = issueState;
    }

    public IssueDTO entityToDto() {
        return IssueDTO.builder()
                .issueIdx(issueIdx)
                .issueCn(issueCn)
                .issueDate(issueDate)
                .issueState(issueState)
                .issueGitFile(issueGitFile)
                .build();
    }

    public void addProject(Project project) {
        this.project = project;
    }

    public void addMember(Member member) {
        this.member = member;
    }
}
