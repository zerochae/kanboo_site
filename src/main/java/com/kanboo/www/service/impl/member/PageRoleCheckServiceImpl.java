package com.kanboo.www.service.impl.member;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.member.PageRoleCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageRoleCheckServiceImpl implements PageRoleCheckService {

    private final JwtSecurityService jwtSecurityService;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean checkProject(String token, Long projectIdx) {
        System.out.println(token);
        System.out.println(projectIdx);
        String memTag = jwtSecurityService.getToken(token);

        if(memTag != null) {
            memberRepository.findByMemTag(memTag);
        } else {
            return false;
        }

        Project project = projectRepository.findByPrjctIdx(projectIdx);
        return project != null;
    }
}
