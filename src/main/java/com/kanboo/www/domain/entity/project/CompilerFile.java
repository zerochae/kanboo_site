package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.CompilerContentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compiler_file")
@Builder
public class CompilerFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comFileIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "com_idx")
    private Compiler compiler;

    private String comFileCn;

    public CompilerContentDTO entityToDto() {
        return CompilerContentDTO.builder()
                .comFileIdx(comFileIdx)
                .compiler(compiler.entityToDto())
                .comFileCn(comFileCn)
                .build();
    }

    public void updateContent(String comFileCn) {
        this.comFileCn = comFileCn;
    }
}
