package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.entity.board.QBoard;
import com.kanboo.www.domain.entity.board.QProjectBoard;
import com.kanboo.www.domain.entity.global.QCodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.member.QBan;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.member.QProjectMember;
import com.kanboo.www.domain.entity.project.*;
import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProjectsDslRepositoryImpl implements ProjectsDslRepository {

    private final EntityManager em;

    @Override
    public List<Project> getIssueAndCalendar(String memTag) {

        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QIssue issue = QIssue.issue;
        QCalendar calendar = QCalendar.calendar;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.select(project)
                .from(project)
                .fetch();
    }

    @Override
    public ProjectDTO getDashBoard(String memTag, Long projectIdx) {

        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QMember member = QMember.member;
        QBan ban = QBan.ban;
        QGantt gantt = QGantt.gantt;
        QIssue issue = QIssue.issue;
        QGit git = QGit.git;
        QBoard board = QBoard.board;
        QCalendar calendar = QCalendar.calendar;
        QCodeDetail codeDetail = QCodeDetail.codeDetail;
        QProjectBoard projectBoard = QProjectBoard.projectBoard;

        JPAQueryFactory query = new JPAQueryFactory(em);

        List<Member> resultMemberList = query.select(member)
                .from(projectMember)
                .rightJoin(projectMember.member, member)
                .leftJoin(member.ban, ban)
                .fetchJoin()
                .where(
                        projectMember.project.prjctIdx.eq(projectIdx)
                )
                .fetch();

        List<Issue> resultIssueList = query.selectFrom(issue)
                .leftJoin(issue.project, project)
                .fetchJoin()
                .where(
                        project.prjctIdx.eq(projectIdx)
                )
                .orderBy(issue.issueDate.desc())
                .offset(0).limit(6)
                .fetch();

        List<Calendar> resultCalendarList = query.selectFrom(calendar)
                .leftJoin(calendar.project, project)
                .fetchJoin()
                .leftJoin(calendar.codeDetail, codeDetail)
                .fetchJoin()
                .where(
                        project.prjctIdx.eq(projectIdx)
                )
                .fetch();


        Git resultGit = query.selectFrom(git)
                .innerJoin(git.project, project)
                .fetchJoin()
                .where(
                        project.prjctIdx.eq(projectIdx)
                )
                .fetchOne();

        List<Board> resultBoardList = query.selectFrom(board)
                .leftJoin(board.codeDetail, codeDetail)
                .join(projectBoard)
                .on(projectBoard.board.eq(board))
                .where(
                        codeDetail.codeDetailIdx.eq("9")
                                .and(projectBoard.project.prjctIdx.eq(projectIdx))
                )
                .orderBy(board.boardDate.desc())
                .offset(0) .limit(6)
                .fetch();

        List<Gantt> resultGanttList = query.selectFrom(gantt)
                .leftJoin(gantt.project, project)
                .fetchJoin()
                .where(
                        project.prjctIdx.eq(projectIdx)
                )
                .fetch();

        ProjectDTO projectDTO = ProjectDTO.builder()
                .prjctIdx(projectIdx)
                .build();

        List<MemberDTO> memberDTOS = new ArrayList<>();
        resultMemberList.forEach(item -> {
            MemberDTO build = MemberDTO.builder()
                    .memId(item.getMemId())
                    .memTag(item.getMemTag())
                    .build();
            memberDTOS.add(build);
        });

        List<IssueDTO> issueDTOS = new ArrayList<>();
        resultIssueList.forEach(item -> {
            IssueDTO build = IssueDTO.builder()
                    .issueCn(item.getIssueCn())
                    .build();
            issueDTOS.add(build);
        });

        List<CalendarDTO> calendarDTOS = new ArrayList<>();
        resultCalendarList.forEach(item -> {
            CalendarDTO build = CalendarDTO.builder()
                    .calTitle(item.getCalTitle())
                    .calCn(item.getCalCn())
                    .calColor(item.getCalColor())
                    .calStartDate(item.getCalStartDate())
                    .calEndDate(item.getCalEndDate())
                    .calDelAt(item.getCalDelAt())
                    .calIsAllDay(item.getCalIsAllDay())
                    .build();

            calendarDTOS.add(build);
        });

        String repo = resultGit != null ? resultGit.getGitRepo() : null;
        GitDTO returnGit = GitDTO.builder().gitRepo(repo).build();

        List<BoardDTO> boardDTOS = new ArrayList<>();
        resultBoardList.forEach(item -> {
            BoardDTO build = BoardDTO.builder()
                    .boardCn(item.getBoardCn())
                    .build();
            boardDTOS.add(build);
        });

        List<GanttDTO> ganttDTOS = new ArrayList<>();
        resultGanttList.forEach(item -> {
            GanttDTO build = GanttDTO.builder()
                    .gtIdx(item.getGtIdx())
                    .gtStartDate(item.getGtStartDate())
                    .gtEndDate(item.getGtEndDate())
                    .gtTitle(item.getGtTitle())
                    .gtProgress(item.getGtProgress())
                    .gtPriority(item.getGtPriority())
                    .build();
            ganttDTOS.add(build);
        });

        projectDTO.setMembers(memberDTOS);
        projectDTO.setIssueList(issueDTOS);
        projectDTO.setCalendarList(calendarDTOS);
        projectDTO.setGit(returnGit);
        projectDTO.setBoardList(boardDTOS);
        projectDTO.setGanttList(ganttDTOS);

        return projectDTO;
    }

    @Override
    public Long getMaxIndexOfProject(String selected, String key) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QProject qProject = QProject.project;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

//        switch (selected) {
//            case "memNick":
//                booleanBuilder.and(member.memNick.contains(keyword));
//                break;
//            case "memId":
//                booleanBuilder.and(member.memId.contains(keyword));
//                break;
//            case "All":
//                booleanBuilder.and(member.memNick.contains(keyword))
//                        .or(member.memNick.contains(keyword));
//                break;
//        }

        return query
                .selectFrom(qProject)
                .where(
                        booleanBuilder
                ).
                fetchCount();

    }

    @Override
    public List<Project> findAllProject(String selected, String keyword, int articleOnView) {
        QProjectMember qProjectMember = QProjectMember.projectMember;
        QProject qProject = QProject.project;

        JPAQueryFactory query = new JPAQueryFactory(em);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        switch (selected) {
            case "prjctNm":
                booleanBuilder.and(qProject.prjctNm.contains(keyword));
                break;
//            case "memId":
//                booleanBuilder.and(qProject.projectMembers.contains(keyword));
//                break;
            case "All":
                booleanBuilder.and(qProject.prjctNm.contains(keyword));
//                        .or(member.memNick.contains(keyword));
                break;
        }

        return query
                .select(qProject).from(qProject)
                .rightJoin(qProject.projectMembers, qProjectMember)
                .fetchJoin()
                .innerJoin(qProjectMember)
                .where(
                        qProjectMember.prjctMemRole.eq("PM")
                                .and(booleanBuilder)
                )
                .fetch();
    }
}
