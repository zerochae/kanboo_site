package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.member.QBan;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.project.Gantt;
import com.kanboo.www.domain.entity.project.QGantt;
import com.kanboo.www.domain.entity.project.QProject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class GanttDslRepositoryImpl implements GanttDslRepository {

    private final EntityManager em;

    @Override
    public List<Gantt> getGantt(long projectIdx) {

        QGantt gantt = QGantt.gantt;
        QProject project = QProject.project;
        QMember member = QMember.member;
        QBan ban = QBan.ban;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .selectFrom(gantt)
                .innerJoin(gantt.project, project)
                .fetchJoin()
                .innerJoin(gantt.member,member)
                .fetchJoin()
                .leftJoin(member.ban, ban)
                .fetchJoin()
                .where(
                        project.prjctIdx.eq(projectIdx)
                )
                .fetch();

    }
}
