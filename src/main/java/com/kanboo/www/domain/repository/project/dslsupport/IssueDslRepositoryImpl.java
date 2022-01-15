package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Issue;
import com.kanboo.www.domain.entity.project.QIssue;
import com.kanboo.www.domain.entity.project.QProject;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class IssueDslRepositoryImpl implements IssueDslRepository {

	private final EntityManager em;

	@Override
	public List<Issue> findAllByProjectIdxDesc(Long projectIdx) {
		QIssue qIssue = QIssue.issue;
		JPAQuery<Issue> query = new JPAQuery<>(em);
		return query.from(qIssue)
				.where(qIssue.project.prjctIdx.eq(projectIdx))
				.orderBy(qIssue.issueDate.desc())
				.fetch();
	}

	@Override
	public List<Issue> getDashBoardIssue(Long prjctIdx) {

		QIssue issue = QIssue.issue;
		QProject project = QProject.project;

		JPAQueryFactory query = new JPAQueryFactory(em);
		return query.selectFrom(issue)
				.rightJoin(issue.project, project)
				.fetchJoin()
				.where(
						issue.project.prjctIdx.eq(prjctIdx)
				)
				.limit(6).offset(0)
				.fetch();
	}


}
