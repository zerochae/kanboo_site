package com.kanboo.www.service.inter.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    Project save(ProjectDTO project);

    void updateReadMe(ProjectDTO project);

    ProjectDTO getProject(Long projectIdx);

    void addDirOrFile(Map<String, Object> map);

    Map<String, Object> getAllList(String token);

    void saveProject(Map<String, Object> param);

    ProjectDTO getDashBoardData(String memTag, Long projectIdx);

    ProjectDTO updateProjectState(ProjectDTO projectDTO);

    Long getMaxIndexOfProject(String selected, String key);

}