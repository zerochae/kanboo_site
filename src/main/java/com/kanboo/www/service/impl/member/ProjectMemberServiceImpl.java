package com.kanboo.www.service.impl.member;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.repository.project.ProjectMemberRepository;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.member.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public List<ProjectMemberDTO> getAllRoomFindByMemIdx(Long memIdx) {
        List<ProjectMember> list = projectMemberRepository.findAllByMemIdx(memIdx);
        List<ProjectMemberDTO> dto = new ArrayList<>();
        for (ProjectMember projectMember : list) {
            dto.add(projectMember.entityToDto());
        }
        return dto;
    }

    @Override
    public List<ProjectMemberDTO> getAllProject(String selected, String key, int articleOnView) {

        List<ProjectMember> all = projectMemberRepository.getAllProjectMemberList(selected,key,articleOnView);
        List<ProjectMemberDTO> result = new ArrayList<>();
        for(ProjectMember pm : all){
            ProjectDTO project = ProjectDTO.builder()
                    .prjctIdx(pm.getProject().getPrjctIdx())
                    .prjctComplAt(pm.getProject().getPrjctComplAt())
                    .prjctDelAt(pm.getProject().getPrjctDelAt())
                    .prjctStartDate(pm.getProject().getPrjctStartDate())
                    .prjctEndDate(pm.getProject().getPrjctEndDate())
                    .build();
            MemberDTO member = MemberDTO.builder()
                    .memId(pm.getMember().getMemId())
                    .build();

            ProjectMemberDTO.builder()
                    .project(project)
                    .member(member)
                    .build();
            result.add(pm.entityToDto());
        }
        return result;
    }
}
