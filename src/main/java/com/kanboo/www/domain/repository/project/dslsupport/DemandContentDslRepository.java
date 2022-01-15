package com.kanboo.www.domain.repository.project.dslsupport;


import com.kanboo.www.domain.entity.project.DemandContent;

import java.util.List;

public interface DemandContentDslRepository {
    DemandContent findByDemandContentIdx(Long idx);
    List<DemandContent> findByDemandIdx(Long idx);
    void deleteByDemandIdx(Long demandIdx, Long demandCnIdx);
    void deleteDemandContentAllByDemandIdx(Long demandIdx);

}
