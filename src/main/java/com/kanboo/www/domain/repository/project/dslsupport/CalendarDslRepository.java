package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Calendar;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface CalendarDslRepository {
	List<Calendar> findByProjectIdxAndMemberIdx(Long projectIdx, Map<String, Object> filter);
	List<Calendar> findAllByThisWeek(Long projectIdx, LocalDateTime start, LocalDateTime end);
}
