package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.GanttDTO;
import com.kanboo.www.service.inter.member.MemberService;
import com.kanboo.www.service.inter.project.GanttService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gantt")
public class GanttController {

    private final MemberService memberService;
    private final GanttService ganttService;

    @GetMapping("/selectGantt")
    public List<GanttDTO> selectGantt(Long projectIdx) {
        return ganttService.getGantt(projectIdx);
    }

    @PostMapping("/insertGantt")
    public GanttDTO insertGantt(GanttDTO ganttDTO) {
        System.out.println(ganttDTO);
        return ganttService.insertGantt(ganttDTO);
    }

    @PostMapping("/updateGantt")
    public GanttDTO updateGantt(GanttDTO ganttDTO) {
        System.out.println(ganttDTO);
        return ganttService.updateGantt(ganttDTO);
    }

    @PostMapping("/deleteGantt")
    public void deleteGantt(GanttDTO ganttDTO) {
        ganttService.deleteGantt(ganttDTO);
    }

}
