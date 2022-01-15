package com.kanboo.www.dto.member;

import com.kanboo.www.dto.project.ProjectDTO;

public interface ProjectMemberDTOInter {

    MemberDTO member();
    ProjectDTO project();
    String prjctMemRole();
}
