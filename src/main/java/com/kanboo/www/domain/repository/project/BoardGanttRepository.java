package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.BoardGantt;
import com.kanboo.www.domain.entity.project.idclass.BoardGanttId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGanttRepository  extends JpaRepository<BoardGantt, BoardGanttId> {
}
