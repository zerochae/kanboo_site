package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.project.dslsupport.ProjectsDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectsDslRepository {
	Project findByPrjctIdx(Long idx);
}
