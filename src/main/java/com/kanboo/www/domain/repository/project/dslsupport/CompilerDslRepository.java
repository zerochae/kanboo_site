package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Compiler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilerDslRepository {
    String getPath(Long projectIdx);
    List<Compiler> getFilePath(List<String> list, Long projectIdx);
}
