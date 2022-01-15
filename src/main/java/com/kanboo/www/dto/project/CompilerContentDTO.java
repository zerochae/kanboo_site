package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.CompilerFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilerContentDTO {

    private Long comFileIdx;
    private CompilerDTO compiler;
    private String comFileCn;

    public CompilerFile dtoToEntity() {
        return CompilerFile.builder()
                .comFileIdx(comFileIdx)
                .compiler(compiler.dtoToEntity())
                .comFileCn(comFileCn)
                .build();
    }
}
