package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.CalendarDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarService {
	List<CalendarDTO> calendarHandler(CalendarDTO calendarDTO);

	CalendarDTO updateCalendar(CalendarDTO calendarDTO);

	CalendarDTO insertCalendar(CalendarDTO calendarDTO);

	List<CalendarDTO> getThisWeekSchedule(Long projectIdx, LocalDateTime startDate, LocalDateTime endDate);

	CalendarDTO deleteCalendar(CalendarDTO calendarDTO);
}
