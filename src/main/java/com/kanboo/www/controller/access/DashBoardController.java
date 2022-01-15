package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/getData")
    public Map<String, Object> getDashBoard(@RequestParam Long prjctIdx) {
        Map<String, Object> result = new HashMap<>();
        dashBoardService.getDashBoard(result, prjctIdx);
        return result;
    }
}
