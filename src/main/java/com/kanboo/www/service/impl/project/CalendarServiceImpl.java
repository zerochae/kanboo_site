package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.CalendarRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

	private final CalendarRepository calendarRepository;
	private final ProjectRepository projectRepository;
	private final MemberRepository memberRepository;

	@Override
	public List<CalendarDTO> calendarHandler(CalendarDTO calendarDTO) {
		Map<String, Object> map = new HashMap<>();
		map.put("member", calendarDTO.getMember().getMemIdx());
		List<Calendar> calendarList = calendarRepository
				.findByProjectIdxAndMemberIdx(calendarDTO.getProject().getPrjctIdx()
						, map);
		List<CalendarDTO> dtoList = new ArrayList<>();
		for (Calendar calendar : calendarList) {
			CalendarDTO build = CalendarDTO.builder()
					.calIdx(calendar.getCalIdx())
					.project(ProjectDTO.builder().prjctIdx(calendarDTO.getProject().getPrjctIdx()).build())
					.member(calendar.getMember().entityToDto())
					.codeDetail(calendar.getCodeDetail().entityToDto())
					.calStartDate(calendar.getCalStartDate())
					.calEndDate(calendar.getCalEndDate())
					.calColor(calendar.getCalColor())
					.calCn(calendar.getCalCn())
					.calTitle(calendar.getCalTitle())
					.calDelAt(calendar.getCalDelAt())
					.calIsAllDay(calendar.getCalIsAllDay())
					.calIsDeletable(calendar.getCalIsDeletable())
					.calIsResizable(calendar.getCalIsResizable())
					.build();
			dtoList.add( build );
		}
		return dtoList;
	}

	@Override
	@Transactional
	public CalendarDTO updateCalendar(CalendarDTO calendarDTO) {
		// dto에 있는 idx로 entity조회
		// 반환된 entity에 setter메소드(생성자x)를 통해서 값 업데이트
		Calendar calendar = calendarRepository.findByCalIdx(calendarDTO.getCalIdx());
		calendar.changeStartDate(calendarDTO.getCalStartDate());
		calendar.changeEndDate(calendarDTO.getCalEndDate());
		if(calendar.getCalTitle().equals(calendarDTO.getCalTitle())){
			calendar.changeTitle(calendarDTO.getCalTitle());
		}
		return calendar.entityToDto();
	}

	@Override
	public CalendarDTO insertCalendar(CalendarDTO calendarDTO) {
		Member m = Member.builder().memIdx(calendarDTO.getMember().getMemIdx()).build();
		Project p = Project.builder().prjctIdx(calendarDTO.getProject().getPrjctIdx()).build();
		CodeDetail cd = CodeDetail.builder().codeDetailIdx(calendarDTO.getCodeDetail().getCodeDetailIdx()).build();
		Calendar c = Calendar.builder()
				.project(p)
				.member(m)
				.calStartDate(calendarDTO.getCalStartDate())
				.calEndDate(calendarDTO.getCalEndDate())
				.calColor(calendarDTO.getCalColor())
				.codeDetail(cd)
				.calCn(calendarDTO.getCalCn())
				.calTitle(calendarDTO.getCalTitle())
				.calDelAt(calendarDTO.getCalDelAt())
				.calIsAllDay(calendarDTO.getCalIsAllDay())
				.calIsDeletable(calendarDTO.getCalIsDeletable())
				.calIsResizable(calendarDTO.getCalIsResizable())
				.build();
		Calendar calendar = calendarRepository.save(c);
		return calendar.entityToDto();
	}

	@Override
	public List<CalendarDTO> getThisWeekSchedule(Long projectIdx, LocalDateTime startDate, LocalDateTime endDate) {
		List<Calendar> calendarList = calendarRepository.findAllByThisWeek(projectIdx, startDate, endDate);
		List<CalendarDTO> list = new ArrayList<>();
		calendarList.forEach(item -> {
			list.add(item.entityToDto());
		});
		return list;
	}

	@Override
	@Transactional
	public CalendarDTO deleteCalendar(CalendarDTO calendarDTO) {
		Calendar calendar = calendarRepository.findByCalIdx(calendarDTO.getCalIdx());
		calendar.changeDelAt(calendarDTO.getCalDelAt());
		return calendarDTO;
	}

}
