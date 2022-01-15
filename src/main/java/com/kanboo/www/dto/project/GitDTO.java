package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.Git;
import com.kanboo.www.domain.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitDTO {

    private ProjectDTO project;
    private String gitRepo;

    public Git dtoToEntity() {
        return Git.builder()
                .project(project.dtoToEntity())
                .gitRepo(gitRepo)
                .build();
    }
}
