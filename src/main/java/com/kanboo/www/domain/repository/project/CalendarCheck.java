package com.kanboo.www.domain.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarCheck extends JpaRepository<com.kanboo.www.domain.entity.project.CalendarCheck, Long> {
}
