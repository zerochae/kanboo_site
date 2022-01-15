package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.member.idclass.ProjectMemberId;
import com.kanboo.www.domain.repository.project.dslsupport.ProjectMemberDslRepository;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.member.ProjectMemberDTOInter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId>, ProjectMemberDslRepository {
    List<ProjectMember> findByProjectPrjctIdx(Long prjctIdx);
}
