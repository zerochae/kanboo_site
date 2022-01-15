package com.kanboo.www.dto.member;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.dto.project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberDTO {

    private MemberDTO member;
    private ProjectDTO project;
    private String prjctMemRole;

    public ProjectMember dtoToEntity() {
        return ProjectMember.builder()
                .member(member.dtoToEntity())
                .project(project.dtoToEntity())
                .prjctMemRole(prjctMemRole)
                .build();
    }
}
