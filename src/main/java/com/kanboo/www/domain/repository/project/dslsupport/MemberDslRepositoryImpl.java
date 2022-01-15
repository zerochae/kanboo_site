package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.global.QRole;
import com.kanboo.www.domain.entity.member.Ban;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.member.QBan;
import com.kanboo.www.domain.entity.member.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberDslRepositoryImpl implements MemberDslRepository{

	private final EntityManager em;

	@Override
	public Member findByMemberIdx(Long memberIdx) {
		QMember qMember = QMember.member;
		QRole qRole = QRole.role;

		JPAQueryFactory query = new JPAQueryFactory(em);

		return (Member) query.select(qMember)
				.from(qMember)
				.innerJoin(qMember.role, qRole)
				.fetchJoin()
				.where(qMember.memIdx.eq(memberIdx))
				.fetch();
	}

	@Override
	public List<Member> findAllMemberBanInfo(String selected, String keyword, int articleOnView) {
		QMember member = QMember.member;
		QBan qBan = QBan.ban;

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		switch (selected) {
			case "memNick":
				booleanBuilder.and(member.memNick.contains(keyword));
				break;
			case "memTag":
				booleanBuilder.and(member.memTag.contains(keyword));
				break;
			case "All":
				booleanBuilder.or(member.memNick.contains(keyword))
						.or(member.memTag.contains(keyword));
				break;
		}

		JPAQueryFactory query = new JPAQueryFactory(em);

		return query.select(member)
				.from(member)
				.leftJoin(member.ban, qBan)
				.fetchJoin()
				.where(
						booleanBuilder
				)
				.offset(articleOnView)
				.limit(15)
				.fetch();

	}

	@Override
	public long getMaxIndexOfMember(String selected, String keyword) {

		JPAQueryFactory query = new JPAQueryFactory(em);
		QMember member = QMember.member;
		BooleanBuilder booleanBuilder = new BooleanBuilder();

		switch (selected) {
			case "memId":
				booleanBuilder.and(member.memId.contains(keyword));
				break;
			case "memTag":
				booleanBuilder.and(member.memTag.contains(keyword));
				break;
			case "All":
				booleanBuilder.or(member.memId.contains(keyword))
						.or(member.memTag.contains(keyword));
				break;
		}

		return query
				.select(member)
				.from(member)
				.where(
						booleanBuilder
				).
				fetchCount();
	}

}
