package com.kanboo.www.controller.access;

import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.dto.project.GanttDTO;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.board.BoardService;
import com.kanboo.www.service.inter.project.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pdtail")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final GanttService ganttService;
    private final BoardService boardService;
    private final IssueService issueService;
    private final CalendarService calendarService;
    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

    private final JwtSecurityService jwtSecurityService;

    @GetMapping("/allList")
    public Map<String, Object> getAllList(String token) {
        return projectService.getAllList(token);
    }

    @PostMapping("/createProject")
    public ProjectDTO createProject(@RequestParam Map<String, Object> map) {
        /** 파라미터 조회 */
        String token = map.get("token") + "";
        String tag = jwtSecurityService.getToken(token);
        if(tag == null) {
            return null;
        }

        String prjctNm = map.get("prjctNm") + "";
        LocalDate startDate = LocalDate.parse(map.get("prjctStartDate") + "", DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(map.get("prjctEndDate") + "", DateTimeFormatter.ISO_DATE);

        logger.info("createProject{} - 파라미터 조회 ::: startDate = {}, endDate = {} ", prjctNm, startDate, endDate);

        /** 검증 */

        /** 서비스 파라미터 생성 */
        Map<String,Object> param = new HashMap<>();
        ProjectDTO project = ProjectDTO.builder()
                    .prjctNm(prjctNm)
                    .prjctStartDate(startDate)
                    .prjctEndDate(endDate)
                    .prjctComplAt("n")
                    .prjctProgress(0)
                    .prjctDelAt("n")
                    .prjctReadMe("")
                    .build();
        param.put("project",project);
        param.put("tag", tag);

        /** 서비스 호출 2개 (트랜잭션 고려) */
        projectService.saveProject(param);
        logger.info("createProject - 파라미터 조회 ::: 서비스 호출 완료");

        /** Client 반환(파라미터 구성, 화면설정) */
        String successYn = (String) param.get("successYn");
        logger.info("createProject{" + prjctNm + "} - 파라미터 조회 ::: successYn = {+" + successYn +"+}");
        if("Y".equals(successYn)){
            ProjectDTO returnProject = (ProjectDTO) param.get("project");
            return returnProject;
        }

        return null;
    }

    @PostMapping("/saveReadMe")
    public boolean saveReadMe(ProjectDTO project) {
        projectService.updateReadMe(project);
        return true;
    }

    @GetMapping("/getData")
    public Map<String, Object> getDashBoardData(
            @RequestParam Long projectIdx,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        Map<String, Object> result = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        ProjectDTO project = projectService.getProject(projectIdx);
        List<GanttDTO> gantt = ganttService.getGantt(projectIdx);
        List<BoardDTO> projectLastest = boardService.getProjectLastest(projectIdx);
        List<IssueDTO> lastestIssue = issueService.getLastestIssue(projectIdx);
        List<CalendarDTO> weekSchedule = calendarService.getThisWeekSchedule(projectIdx, start, end);

        result.put("project", project);
        result.put("gantt", gantt);
        result.put("projectLastest", projectLastest);
        result.put("lastestIssue", lastestIssue);
        result.put("weekSchedule", weekSchedule);

        return result;
    }

    @PostMapping("/addDirOrFile")
    public Object addDirOrFile(@RequestParam Map<String, Object> map) {
        projectService.addDirOrFile(map);
        return map.get("result");
    }
}


