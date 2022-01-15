package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberDslRepository {

    List<ProjectMember> getAllList(String memTag);

    List<ProjectMember> findAllByMemIdx(Long MemIdx);

    List<ProjectMember> getAllProjectMemberList(String selected, String key,int articleOnView);
}
