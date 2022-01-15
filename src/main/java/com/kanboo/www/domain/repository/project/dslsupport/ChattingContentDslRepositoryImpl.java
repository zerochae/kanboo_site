package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.member.QBan;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.project.ChattingContent;
import com.kanboo.www.domain.entity.project.QChat;
import com.kanboo.www.domain.entity.project.QChattingContent;
import com.kanboo.www.domain.entity.project.QProject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ChattingContentDslRepositoryImpl implements ChattingContentDslRepository{

	private final EntityManager em;

	// 차후에 쿼리 수정해야함 prjctIdx, memIdx => prjctIdx, prjct_memIdx로
	@Override
	public List<ChattingContent> findAllByPrjctIdxAndMemIdx(Long prjctIdx) {
		QChattingContent qChattingContent = QChattingContent.chattingContent;
		QChat qChat = QChat.chat;
		QProject qProject = QProject.project;
		QMember qMember = QMember.member;
		QBan qBan = QBan.ban;

		JPAQueryFactory query = new JPAQueryFactory(em);

		// 메인테이블이 왼쪽, 조인할 테이블이 오른쪽
		// from 에 들어가는 테이블이 메인테이블
		// 왼쪽은 쩜 ?, 위에 가지고있는?(이미 조인된 테이블)
		// 오른쪽은 조인하고싶은 테이블
		// right left 기준은 많은쪽으로
		// 1:1 은 innerJoin

		return query.selectFrom(qChattingContent)
				.rightJoin(qChattingContent.chat, qChat)
				.fetchJoin()
				.innerJoin(qChat.project, qProject)
				.fetchJoin()
				.rightJoin(qChat.member, qMember)
				.fetchJoin()
				.leftJoin(qMember.ban, qBan)
				.fetchJoin()
				.where(qChattingContent.chat.project.prjctIdx.eq(prjctIdx))
				.fetch();
	}
}
