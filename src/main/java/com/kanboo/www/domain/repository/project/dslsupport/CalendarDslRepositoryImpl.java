package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.global.QCodeDetail;
import com.kanboo.www.domain.entity.global.QMasterCode;
import com.kanboo.www.domain.entity.global.QRole;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.entity.project.QCalendar;
import com.kanboo.www.domain.entity.project.QProject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CalendarDslRepositoryImpl implements CalendarDslRepository{

	private final EntityManager em;

	@Override
	public List<Calendar> findByProjectIdxAndMemberIdx(Long projectIdx, Map<String, Object> filter) {
		QCalendar qCalendar = QCalendar.calendar;
		QMember qMember = QMember.member;
		QProject qProject = QProject.project;
		QCodeDetail qCodeDetail = QCodeDetail.codeDetail;

		JPAQueryFactory query = new JPAQueryFactory(em);

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		if(filter.get("member") != null) {
			booleanBuilder.and(qCalendar.member.memIdx.eq(Long.parseLong(filter.get("member") + "")));
		}

		 return query.select(qCalendar)
				.from(qCalendar)
				.innerJoin(qCalendar.member, qMember)
				.fetchJoin()
				.innerJoin(qCalendar.project, qProject)
				.fetchJoin()
				.innerJoin(qCalendar.codeDetail, qCodeDetail)
				.fetchJoin()
				.where(
						qCalendar.project.prjctIdx.eq(projectIdx)
								.and(booleanBuilder)
				)
				.fetch();
	}

	@Override
	public List<Calendar> findAllByThisWeek(Long projectIdx, LocalDateTime start, LocalDateTime end) {
		QCalendar qCalendar = QCalendar.calendar;

		JPAQuery<Calendar> query = new JPAQuery<>(em);

		return query.from(qCalendar)
				.where(
						qCalendar.project.prjctIdx.eq(projectIdx)
								.and(qCalendar.calStartDate.between(start, end))
				)
				.fetch();
	}

}
