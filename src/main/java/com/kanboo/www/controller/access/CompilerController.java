package com.kanboo.www.controller.access;

import com.kanboo.www.domain.entity.project.CompilerFile;
import com.kanboo.www.dto.project.CompilerContentDTO;
import com.kanboo.www.dto.project.CompilerDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.CompilerContentService;
import com.kanboo.www.service.inter.project.CompilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compiler")
public class CompilerController {

    private final CompilerService compilerService;
    private final CompilerContentService compilerContentService;

    @PostMapping("/getData")
    public Map<String, Object> getCompiler(@RequestBody ProjectDTO projectDTO) {
        if(projectDTO.getPrjctIdx() == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        List<CompilerDTO> javaList = compilerService.getList(projectDTO);
        result.put("javaList", javaList);
        List<CompilerDTO> htmlList = compilerService.getHtmlList(projectDTO);
        result.put("htmlList", htmlList);

        return result;
    }

    @PostMapping("/getFile")
    public CompilerContentDTO getFileDetail(@RequestBody CompilerDTO compilerDTO) {
        return compilerContentService.getFile(compilerDTO);
    }

    @PostMapping("/updateFile")
    public boolean updateFile(@RequestBody CompilerContentDTO compilerContentDTO) {
        return compilerContentService.updateFile(compilerContentDTO);
    }

    @PostMapping("/runCompile")
    public Map<String, String> runCompile(@RequestBody ProjectDTO projectDTO) {
        return compilerService.runMemberProject(projectDTO);
    }

    @PostMapping("/getHtml")
    public CompilerContentDTO getHtml(@RequestBody CompilerDTO compilerDTO) {
        return compilerContentService.getHtmlDetail(compilerDTO);
    }

    @PostMapping("/updateHtml")
    public void updateHtml(@RequestBody CompilerContentDTO compilerContentDTO) {
        compilerContentService.updateHtml(compilerContentDTO);
    }

    @PostMapping("/createHtml")
    public CompilerContentDTO createHtml(@RequestBody Map<String, Object> map) {
        if(!(map.get("name") + "").contains(".")) {
            map.put("name", map.get("name") + ".html");
        }
        return compilerContentService.createHtml(map);
    }
}
