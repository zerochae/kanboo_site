package com.kanboo.www.domain.entity.project;

import com.kanboo.www.domain.entity.project.idclass.GitId;
import com.kanboo.www.dto.project.GitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "git")
@Builder
public class Git {

    @EmbeddedId
    private GitId id;

    @MapsId("project")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    private String gitRepo;

    public GitDTO entityToDto() {
        return GitDTO.builder()
                .project(project.entityToDto())
                .gitRepo(gitRepo)
                .build();
    }
}