package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.ErdDTO;
import com.kanboo.www.service.inter.project.ErdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/erd")
@RequiredArgsConstructor
public class ErdAndViewController {

    private final ErdService erdService;

    @PostMapping("/getErd")
    @ResponseBody
    public ResponseEntity<?> getAllErd(@RequestBody Map<String, Object> map) {
        Long prjctIdx = Long.parseLong(map.get("projectIdx") + "");
        return ResponseEntity.ok(erdService.getAllErd(prjctIdx));
    }

    @PostMapping("/createTable")
    public ErdDTO createTable(@RequestBody ErdDTO erdDTO) {
        return erdService.createTable(erdDTO);
    }

    @PostMapping("/deleteTable")
    public void deleteTable(@RequestBody ErdDTO erdDTO) {
        erdService.deleteTable(erdDTO);
    }

    @PostMapping("/updateTable")
    public void updateTable(@RequestBody ErdDTO erdDTO) {
        erdService.updateTable(erdDTO);
    }
}
