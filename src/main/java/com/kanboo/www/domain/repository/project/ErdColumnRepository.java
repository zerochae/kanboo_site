package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.ErdColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErdColumnRepository  extends JpaRepository<ErdColumn, Long> {
    void deleteByErd_ErdIdx(Long erdIdx);
}
