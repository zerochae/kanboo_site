package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.board.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
}
