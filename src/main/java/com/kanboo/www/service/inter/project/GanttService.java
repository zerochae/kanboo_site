package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.GanttDTO;

import java.util.List;

public interface GanttService {
    List<GanttDTO> getGantt(Long projectIdx);

    GanttDTO insertGantt(GanttDTO ganttDTO);

    GanttDTO updateGantt(GanttDTO ganttDTO);

    void deleteGantt(GanttDTO ganttDTO);
}
