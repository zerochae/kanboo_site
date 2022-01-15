package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.CompilerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compiler")
@Builder
public class Compiler {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    private Long parentComIdx;
    private String comSe;
    private String comNm;

    public CompilerDTO entityToDto() {
        return CompilerDTO.builder()
                .comIdx(comIdx)
                .project(project.entityToDto())
                .parentComIdx(parentComIdx)
                .comSe(comSe)
                .comNm(comNm)
                .build();
    }
}
