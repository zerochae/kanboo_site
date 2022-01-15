package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Demand;
import com.kanboo.www.domain.repository.project.DemandRepository;
import com.kanboo.www.dto.project.DemandDTO;
import com.kanboo.www.service.inter.project.DemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandServiceImpl implements DemandService {
    private final DemandRepository demandRepository;

    @Override
    public DemandDTO getDemand(Long idx) {
        Demand byDemandIdx = demandRepository.findByDemandIdx(idx);
        return byDemandIdx.entityToDto();
    }
}
