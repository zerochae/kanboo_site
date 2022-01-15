package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Demand;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandDslRepository {
    Demand findByDemandIdx(Long idx);
}
