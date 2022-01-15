package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Demand;
import com.kanboo.www.domain.repository.project.dslsupport.DemandDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Long>, DemandDslRepository {
}
