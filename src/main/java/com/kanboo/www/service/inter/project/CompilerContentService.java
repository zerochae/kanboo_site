package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.CompilerContentDTO;
import com.kanboo.www.dto.project.CompilerDTO;

import java.util.Map;

public interface CompilerContentService {

    CompilerContentDTO getFile(CompilerDTO compilerDTO);

    boolean updateFile(CompilerContentDTO compilerContentDTO);

    CompilerContentDTO getHtmlDetail(CompilerDTO compilerDTO);

    void updateHtml(CompilerContentDTO compilerContentDTO);

    CompilerContentDTO createHtml(Map<String, Object> map);
}
