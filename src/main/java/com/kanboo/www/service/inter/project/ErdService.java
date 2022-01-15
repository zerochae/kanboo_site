package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.ErdDTO;

import java.util.List;

public interface ErdService {
    List<ErdDTO> getAllErd(Long prjctIdx);

    ErdDTO createTable(ErdDTO erdDTO);

    void deleteTable(ErdDTO erdDTO);

    void updateTable(ErdDTO erdDTO);
}
