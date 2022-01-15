package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.dto.project.ProjectDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsDslRepository {
    List<Project> getIssueAndCalendar(String memTag);
    ProjectDTO getDashBoard(String memTag, Long projectIdx);
    Long getMaxIndexOfProject(String selected,String key);

    List<Project> findAllProject(String selected, String keyword, int articleOnView);
}
