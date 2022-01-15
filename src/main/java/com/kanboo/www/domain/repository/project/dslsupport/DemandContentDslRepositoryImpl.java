package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.DemandContent;
import com.kanboo.www.domain.entity.project.QDemand;
import com.kanboo.www.domain.entity.project.QDemandContent;
import com.kanboo.www.domain.entity.project.QProject;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class DemandContentDslRepositoryImpl implements DemandContentDslRepository {
    private final EntityManager em;

    @Override
    public DemandContent findByDemandContentIdx(Long idx) {
        QDemandContent qDemandContent = QDemandContent.demandContent;
        QDemand qDemand = QDemand.demand;
        JPAQuery<DemandContent> query = new JPAQuery<>(em);

        query.from(qDemandContent).
                where(qDemandContent.demand.demandIdx.eq(idx));
        return query.fetchOne();
    }
    
    @Override
    public List<DemandContent> findByDemandIdx(Long idx){
        QDemandContent qDemandContent = QDemandContent.demandContent;
        QDemand qDemand = QDemand.demand;
        QProject qProject = QProject.project;
        JPAQuery<DemandContent> query = new JPAQuery<>(em);

        return query
                .select(qDemandContent)
                .from(qDemandContent)
                .where(qDemandContent.demand.demandIdx.eq(idx))
                .fetch();
    }

    @Override
    public void deleteByDemandIdx(Long demandIdx, Long demandCnIdx) {
        QDemandContent qDemandContent = QDemandContent.demandContent;
        JPAQueryFactory query = new JPAQueryFactory(em);

        query.delete(qDemandContent).where(
                qDemandContent.demand.demandIdx.eq(demandIdx).and(
                        qDemandContent.demandCnIdx.eq(demandCnIdx)
                )
        ).execute();
    }

    @Override
    public void deleteDemandContentAllByDemandIdx(Long demandIdx) {
        QDemandContent qDemandContent = QDemandContent.demandContent;
        JPAQueryFactory query = new JPAQueryFactory(em);

        query.delete(qDemandContent).where(
                qDemandContent.demand.demandIdx.eq(demandIdx)
        ).execute();
    }


}
