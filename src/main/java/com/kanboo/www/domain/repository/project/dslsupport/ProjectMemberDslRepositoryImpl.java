package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.board.QBoard;
import com.kanboo.www.domain.entity.board.QProjectBoard;
import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.member.QBan;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.member.QProjectMember;
import com.kanboo.www.domain.entity.project.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProjectMemberDslRepositoryImpl implements ProjectMemberDslRepository {

    private final EntityManager em;

    @Override
    public List<ProjectMember> getAllList(String memTag) {
        QMember member = QMember.member;
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QIssue issue = QIssue.issue;
        QCalendar calendar = QCalendar.calendar;

        JPAQueryFactory query = new JPAQueryFactory(em);


        return query
                .select(projectMember).distinct()
                .from(projectMember)
                .innerJoin(projectMember.member, member)
                .innerJoin(projectMember.project, project)
                .leftJoin(project.issueList, issue)
                .leftJoin(project.calendarList, calendar)
                .where(
                        projectMember.member.memTag.eq(memTag)
                )
                .fetch();
    }

    @Override
    public List<ProjectMember> findAllByMemIdx(Long memIdx) {
        QProjectMember qProjectMember = QProjectMember.projectMember;
        QProject qproject = QProject.project;
        QMember qmember = QMember.member;
        QBan qBan = QBan.ban;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.select(qProjectMember)
                .from(qProjectMember)
                .rightJoin(qProjectMember.member, qmember)
                .fetchJoin()
                .leftJoin(qmember.ban, qBan)
                .fetchJoin()
                .innerJoin(qProjectMember.project, qproject)
                .fetchJoin()
                .where(qProjectMember.member.memIdx.eq(memIdx))
                .fetch();
    }

    @Override
    public List<ProjectMember> getAllProjectMemberList(String selected, String key, int articleOnView) {

        JPAQueryFactory query = new JPAQueryFactory(em);

        QProjectMember qProjectMember = QProjectMember.projectMember;
        QProject qProject = QProject.project;
        QMember qMember = QMember.member;
        QBan qBan = QBan.ban;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        switch (selected) {
            case "prjctNm":
                booleanBuilder.and(qProject.prjctNm.contains(key));
                break;
            case "memId":
                booleanBuilder.and(qProjectMember.member.memNick.contains(key));
                break;
            case "All":
                booleanBuilder.or(qProject.prjctNm.contains(key)).or(qProjectMember.member.memId.contains(key));
                break;
        }

        return query
                .selectFrom(qProjectMember)
                .innerJoin(qProjectMember.project, qProject)
                .fetchJoin()
                .innerJoin(qProjectMember.member, qMember)
                .fetchJoin()
                .leftJoin(qMember.ban, qBan)
                .fetchJoin()
                .where(
                        qProjectMember.prjctMemRole.eq("PM")
                                .and(booleanBuilder)
                )
                .orderBy(qProject.prjctIdx.desc())
                .offset(articleOnView)
                .limit(10)
                .fetch();
    }
}
