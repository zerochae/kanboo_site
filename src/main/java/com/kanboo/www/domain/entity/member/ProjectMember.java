package com.kanboo.www.domain.entity.member;

import com.kanboo.www.domain.entity.member.idclass.ProjectMemberId;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_member")
@IdClass(ProjectMemberId.class)
@Builder
public class ProjectMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    private String prjctMemRole;

    public ProjectMemberDTO entityToDto() {
        return ProjectMemberDTO.builder()
                .member(member.entityToDto())
                .project(project.entityToDto())
                .prjctMemRole(prjctMemRole)
                .build();
    }
}
