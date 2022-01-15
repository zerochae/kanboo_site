package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Demand;
import com.kanboo.www.domain.entity.project.QDemand;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class DemandDslRepositoryImpl implements DemandDslRepository{

    private final EntityManager em;

    public Demand findByDemandIdx(Long idx) {
        QDemand qDemand = QDemand.demand;

        JPAQuery<Demand> query = new JPAQuery<>(em);

        query.from(qDemand)
                .where(
                        qDemand.demandIdx.eq(idx)
                                .and(qDemand.project.prjctIdx.gt(10))
                );
                return query
                .fetchOne();


    }
}
