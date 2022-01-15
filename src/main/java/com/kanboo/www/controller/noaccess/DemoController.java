package com.kanboo.www.controller.noaccess;

import com.kanboo.www.service.inter.project.CompilerService;
import com.kanboo.www.util.CompilerUtil;
import com.kanboo.www.util.SaveCompileFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/noAccess")
@RequiredArgsConstructor
public class DemoController {

    private final CompilerService compilerService;

    @PostMapping("/demoCompile")
    public Map<String, String> demoCompile(@RequestParam String code) {
        return compilerService.runDemo(code);
    }


}

