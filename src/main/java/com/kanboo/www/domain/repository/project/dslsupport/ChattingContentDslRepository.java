package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.ChattingContent;

import java.util.List;

public interface ChattingContentDslRepository {
	List<ChattingContent> findAllByPrjctIdxAndMemIdx(Long prjctIdx);
}
