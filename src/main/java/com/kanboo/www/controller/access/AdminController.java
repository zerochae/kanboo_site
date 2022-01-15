package com.kanboo.www.controller.access;

import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.board.CommentDTO;
import com.kanboo.www.dto.member.BanDTO;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.board.BoardService;
import com.kanboo.www.service.inter.board.CommentService;
import com.kanboo.www.service.inter.member.BanService;
import com.kanboo.www.service.inter.member.MemberService;
import com.kanboo.www.service.inter.member.ProjectMemberService;
import com.kanboo.www.service.inter.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final BanService banService;
    private final CommentService commentService;

    @GetMapping("/getAllMember")
    public List<MemberDTO> getMemList(String selected, String key, int articleOnView){
        return memberService.getAllMember(selected,key,articleOnView);
    }

    @GetMapping("/getMaxIndexOfMember")
    public Long getMaxIndexOfMember(String selected, String key){
        return memberService.getMaxIndexOfMember(selected,key);
    }

    @PostMapping("/updateBanUser")
    public BanDTO updateBanUser(BanDTO banDTO){
        return banService.updateBanUser(banDTO);
    }

    @PostMapping("/deleteBanUser")
    public boolean deleteBanUser(BanDTO banDTO){
        System.out.println("AdminController : " + banDTO);
        return banService.deleteBanUser(banDTO);
    }

    @GetMapping("/getAllProject")
    public List<ProjectMemberDTO> getProjectList(String selected, String key, int articleOnView){
        return projectMemberService.getAllProject(selected,key,articleOnView);
    }

    @GetMapping("/getMaxIndexOfProject")
    public Long getMaxIndexOfProject(String selected, String key){
        return projectService.getMaxIndexOfProject(selected,key);
    }

    @PostMapping("/updateProjectState")
    public ProjectDTO updateProjectState(ProjectDTO projectDTO){
        return projectService.updateProjectState(projectDTO);
    }

    @GetMapping("/getQnaBoardList")
    public List<BoardDTO> getQnaBoardList(String selected, String key ,int articleOnView, String codeDetail){
        return boardService.getAllQnaList(selected,key,articleOnView,codeDetail);
    }

    @PostMapping("/insertComment")
    public CommentDTO insertComment(CommentDTO commentDTO){
        return commentService.insertComment(commentDTO);
    }
}
