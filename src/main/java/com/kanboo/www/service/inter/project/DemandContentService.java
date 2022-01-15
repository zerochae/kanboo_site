package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.DemandContentDTO;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface DemandContentService {
    List<DemandContentDTO> loadDemandContent(Long idx);
    DemandContentDTO getDemandContent(Long idx);
    void updateDemandContent(List<DemandContentDTO> demandContentDTO);

    Resource downloadExcel(Long idx);

    void checkDocument(Long idx, File file) throws FileNotFoundException;

    void deleteDemandContent(Long demandIdx, Long demandCnIdx);
}
