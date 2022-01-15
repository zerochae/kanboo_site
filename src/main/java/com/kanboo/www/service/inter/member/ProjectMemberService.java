package com.kanboo.www.service.inter.member;

import com.kanboo.www.dto.member.ProjectMemberDTO;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMemberDTO> getAllRoomFindByMemIdx(Long memIdx);
    List<ProjectMemberDTO> getAllProject(String selected, String key, int articleOnView);
}
