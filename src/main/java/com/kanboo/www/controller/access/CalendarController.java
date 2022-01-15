package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.service.inter.project.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

	private final CalendarService calendarService;

	@GetMapping(value = "/getAllSchedules")
	public List<CalendarDTO> calendarHandler(CalendarDTO calendarDTO){
		return calendarService.calendarHandler(calendarDTO);
	}

	@PostMapping(value = "/updateSchedule")
	public CalendarDTO updateCalendar(CalendarDTO calendarDTO){
		return calendarService.updateCalendar(calendarDTO);
	}

	@PostMapping(value = "/insertSchedule")
	public CalendarDTO insertSchedule(CalendarDTO calendarDTO){
		return calendarService.insertCalendar(calendarDTO);
	}

	@PostMapping(value = "/deleteSchedule")
	public CalendarDTO deleteSchedule(CalendarDTO calendarDTO){
		return calendarService.deleteCalendar(calendarDTO);
	}
}
