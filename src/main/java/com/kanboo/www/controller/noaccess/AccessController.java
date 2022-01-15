package com.kanboo.www.controller.noaccess;

import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/access")
public class AccessController {

    private final MemberService memberService;
    private final JwtSecurityService jwtSecurityService;
    private final Logger logger = LoggerFactory.getLogger(AccessController.class);



    @PostMapping("/login")
    public String loginHandler(MemberDTO memberDTO) {
        MemberDTO member = memberService.loginHandler(memberDTO);
        if(member == null) {
            return null;
        }
        return jwtSecurityService.createToken(member.getMemTag(), (60L * 60 * 1000));
    }


    @PostMapping("/sign")
    public String signHandler(MemberDTO memberDTO) {
        MemberDTO member = memberService.signHandler(memberDTO);
        return member.getMemToken();
    }

    @PostMapping("/idCheck")
    public boolean idDuplicateCheck(String memId) {
        return memberService.isExistId(memId) > 0;
    }

    @PostMapping("/findId")
    public String findIdHandler(MemberDTO memberDTO) {
        return memberService.findIdHandler(memberDTO).getMemId();
    }

    @PostMapping("/resetPw")
    public boolean resetPwHandler(MemberDTO memberDTO){
        String resetPass = memberService.resetPwHandler(memberDTO);
        return false;
    }

    @PostMapping("/userInfo")
    public MemberDTO getUserInfo(@RequestBody Map<String,String> map) {
        String memTag = jwtSecurityService.getToken(map.get("token"));
        if(memTag != null) {
            return memberService.getUserInfo(memTag);
        }
        return null;
    }

    @PostMapping("/userModify")
    public Boolean modifyUser(@RequestBody MemberDTO memberDTO) {
        System.out.println(memberDTO);
        if(memberDTO == null) {
            return false;
        }
        return memberService.userModify(memberDTO);
    }

    @PostMapping("/userImg")
    public Boolean modifyImg(@ModelAttribute MultipartFile file) {

        return false;
    }
}


