package com.kanboo.www.controller.access;


import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.ChattingContentDTO;
import com.kanboo.www.dto.project.SocketDTO;
import com.kanboo.www.service.inter.project.ChattingContentService;
import com.kanboo.www.service.inter.member.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SocketController {

    private final ChattingContentService chattingContentService;
    private final ProjectMemberService projectMemberService;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/receive")
    @SendTo("/send")
    public SocketDTO socketHandler(SocketDTO socketDTO) throws Exception{
        return new SocketDTO(
                socketDTO.getId()
                , socketDTO.getText()
                , socketDTO.getDate()
                , socketDTO.getAlarm()
                , socketDTO.getTextAreaText()
        );
    }

    @MessageMapping("/alarm")
    @SendTo("/send")
    public SocketDTO alarmHandler(SocketDTO socketDTO) throws Exception{
        return new SocketDTO(socketDTO.getId()
                , socketDTO.getText()
                , socketDTO.getDate()
                , socketDTO.getAlarm()
                , socketDTO.getTextAreaText());
    }

    @MessageMapping("/textArea")
    @SendTo("/send")
    public SocketDTO textAreaHandler(SocketDTO socketDTO){
        return new SocketDTO(socketDTO.getId()
                , socketDTO.getText()
                , socketDTO.getDate()
                , socketDTO.getAlarm()
                , socketDTO.getTextAreaText());
    }

    @PostMapping("/socket/insertChatLog")
    public ChattingContentDTO chatHandler(ChattingContentDTO chattingContentDTO){
        return chattingContentService.insertChatLog(chattingContentDTO);
    }

    @GetMapping("/socket/selectAllChatLog")
    public List<ChattingContentDTO> getAllChat(ChattingContentDTO chattingContentDTO){
        return chattingContentService.getAllChat(chattingContentDTO);
    }

    @PostMapping("/socket/getAllRoom")
    public List<ProjectMemberDTO> getAllRoomFindByMemIdx(Long memIdx){
        return projectMemberService.getAllRoomFindByMemIdx(memIdx);
    }

}
