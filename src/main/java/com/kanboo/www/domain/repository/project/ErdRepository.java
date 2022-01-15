package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Erd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErdRepository extends JpaRepository<Erd, Long> {

    List<Erd> findAllByProject_PrjctIdx(Long prjctIdx);
}
