package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.KanbanItem;

import java.util.List;

public interface KanbanDslRepository {
	List<KanbanItem> getAllItemsByPrjctIdx(Long prjctIdx);
}
