package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.CompilerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilerContentRepository extends JpaRepository<CompilerFile, Long> {

    CompilerFile findByCompiler_ComIdx(Long comIdx);
    List<CompilerFile> findAllByCompiler_Project_PrjctIdx(Long prjctIdx);
}
