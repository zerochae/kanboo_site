package com.kanboo.www.controller.global;

import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.member.PageRoleCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final JwtSecurityService jwtSecurityService;
    private final PageRoleCheckService pageRoleCheckService;

    @PostMapping("/check")
    public Boolean tokenCheck(@RequestBody Map<String, Object> map) {
        String exeToken = jwtSecurityService.getToken(map.get("token") + "");
        if(exeToken != null) {
            return true;
        }
        return false;
    }

    @PostMapping("/projectCheck")
    public Boolean projectTokenCheck(@RequestBody Map<String, Object> map) {
        String token = map.get("token") + "";
        Long projectIdx = map.get("projectIdx") != null ? Long.parseLong(map.get("projectIdx") + "") : null;
        return pageRoleCheckService.checkProject(token, projectIdx);
    }
}
