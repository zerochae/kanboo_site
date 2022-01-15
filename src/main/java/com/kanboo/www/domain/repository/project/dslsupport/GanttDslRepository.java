package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Gantt;

import java.util.List;

public interface GanttDslRepository {

    List<Gantt> getGantt(long projectIdx);
}
