package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.ProjectDTO;

import java.util.Map;

public interface DashBoardService {
    void getDashBoard(Map<String, Object> result, Long prjctIdx);
}
