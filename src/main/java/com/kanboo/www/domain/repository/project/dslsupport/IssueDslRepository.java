package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Issue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueDslRepository {
	List<Issue> findAllByProjectIdxDesc(Long projectIdx);
	List<Issue> getDashBoardIssue(Long prjctIdx);
}
