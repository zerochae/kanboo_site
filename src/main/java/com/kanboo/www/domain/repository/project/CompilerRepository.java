package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.repository.project.dslsupport.CompilerDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilerRepository extends JpaRepository<Compiler, Long>, CompilerDslRepository {
    List<Compiler> findByProjectPrjctIdx(Long prjtcIdx);
    Compiler findByProjectPrjctIdxAndComNm(Long prjtcIdx, String comNm);
    Compiler findByComIdx(Long comIdx);
    List<Compiler> findByProjectPrjctIdxAndComSe(Long prjctIdx, String comSe);
}
