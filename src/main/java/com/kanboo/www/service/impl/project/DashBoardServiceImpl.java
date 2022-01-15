package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.project.*;
import com.kanboo.www.domain.repository.board.BoardRepository;
import com.kanboo.www.domain.repository.project.*;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.*;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.member.MemberService;
import com.kanboo.www.service.inter.project.DashBoardService;
import com.kanboo.www.service.inter.project.GanttService;
import com.kanboo.www.service.inter.project.IssueService;
import com.kanboo.www.service.inter.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final BoardRepository boardRepository;
    private final CalendarRepository calendarRepository;
    private final GitRepository gitRepository;
    private final GanttRepository ganttRepository;

    @Override
    public void getDashBoard(Map<String, Object> result, Long prjctIdx) {
        Project projectEntity = projectRepository.findById(prjctIdx).get();
        ProjectDTO project = ProjectDTO.builder()
                .prjctIdx(projectEntity.getPrjctIdx())
                .prjctProgress(projectEntity.getPrjctProgress())
                .prjctReadMe(projectEntity.getPrjctReadMe())
                .prjctNm(projectEntity.getPrjctNm())
                .prjctComplAt(projectEntity.getPrjctComplAt())
                .prjctStartDate(projectEntity.getPrjctStartDate())
                .prjctEndDate(projectEntity.getPrjctEndDate())
                .prjctDelAt(projectEntity.getPrjctDelAt())
                .build();

        List<Calendar> calendarListEntity = calendarRepository.findByProjectPrjctIdx(prjctIdx);
        List<CalendarDTO> calendarList = new ArrayList<>();
        calendarListEntity.forEach(item -> {
            CalendarDTO calendar = CalendarDTO.builder()
                    .calIdx(item.getCalIdx())
                    .calStartDate(item.getCalStartDate())
                    .calEndDate(item.getCalEndDate())
                    .calColor(item.getCalColor())
                    .codeDetail(item.getCodeDetail().entityToDto())
                    .calCn(item.getCalCn())
                    .calTitle(item.getCalTitle())
                    .calDelAt(item.getCalDelAt())
                    .calIsAllDay(item.getCalIsAllDay())
                    .calIsDeletable(item.getCalIsDeletable())
                    .calIsResizable(item.getCalIsResizable())
                    .build();
            calendarList.add(calendar);
        });

        List<Issue> issueListEntity = issueRepository.getDashBoardIssue(prjctIdx);
        List<IssueDTO> issueList = new ArrayList<>();
        issueListEntity.forEach(item -> {
            IssueDTO issue = IssueDTO.builder()
                    .issueIdx(item.getIssueIdx())
                    .issueCn(item.getIssueCn())
                    .issueDate(item.getIssueDate())
                    .issueState(item.getIssueState())
                    .issueGitFile(item.getIssueGitFile())
                    .build();
            issueList.add(issue);
        });

        List<ProjectMember> projectMemberListEntity = projectMemberRepository.findByProjectPrjctIdx(prjctIdx);
        List<ProjectMemberDTO> projectMemberList = new ArrayList<>();
        projectMemberListEntity.forEach(item -> {
            MemberDTO member = MemberDTO.builder()
                    .memTag(item.getMember().getMemTag())
                    .memId(item.getMember().getMemId())
                    .build();

            ProjectMemberDTO projectMember = ProjectMemberDTO.builder()
                    .member(member)
                    .prjctMemRole(item.getPrjctMemRole())
                    .build();
            projectMemberList.add(projectMember);
        });

        Git gitEntity = gitRepository.findByProjectPrjctIdx(prjctIdx);
        GitDTO git = GitDTO.builder()
                .gitRepo(gitEntity == null ? null : gitEntity.getGitRepo())
                .build();

        List<Gantt> ganttListEntity = ganttRepository.findByProjectPrjctIdx(prjctIdx);
        List<GanttDTO> ganttList = new ArrayList<>();
        ganttListEntity.forEach(item -> {
            MemberDTO member = MemberDTO.builder()
                    .memIdx(item.getMember().getMemIdx())
                    .memNick(item.getMember().getMemNick())
                    .build();

            GanttDTO gantt = GanttDTO.builder()
                    .gtIdx(item.getGtIdx())
                    .member(member)
                    .gtState(item.getGtState())
                    .gtPriority(item.getGtPriority())
                    .gtProgress(item.getGtProgress())
                    .gtStartDate(item.getGtStartDate())
                    .gtEndDate(item.getGtEndDate())
                    .gtExplanation(item.getGtExplanation())
                    .gtTitle(item.getGtTitle())
                    .build();
            ganttList.add(gantt);
        });

        result.put("project", project);
        result.put("calendarList", calendarList);
        result.put("issueList", issueList);
        result.put("projectMemberList", projectMemberList);
        result.put("git", git);
        result.put("ganttList", ganttList);
    }
}
