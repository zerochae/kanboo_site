package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.member.QBan;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.project.KanbanItem;
import com.kanboo.www.domain.entity.project.QKanban;
import com.kanboo.www.domain.entity.project.QKanbanItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class KanbanDslRepositoryImpl implements KanbanDslRepository{

	private final EntityManager em;

	@Override
	public List<KanbanItem> getAllItemsByPrjctIdx(Long prjctIdx) {
		QKanbanItem qKanbanItem = QKanbanItem.kanbanItem;
		QKanban qKanban = QKanban.kanban;
		QMember qMember = QMember.member;
		QBan qBan = QBan.ban;

		JPAQueryFactory query = new JPAQueryFactory(em);

		return query.selectFrom(qKanbanItem)
				.rightJoin(qKanbanItem.kanban, qKanban)
				.fetchJoin()
				.rightJoin(qKanbanItem.member, qMember)
				.fetchJoin()
				.leftJoin(qMember.ban, qBan)
				.fetchJoin()
				.where(qKanbanItem.kanban.project.prjctIdx.eq(prjctIdx))
				.fetch();
	}
}
