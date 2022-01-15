package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Gantt;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.project.GanttRepository;
import com.kanboo.www.dto.project.GanttDTO;
import com.kanboo.www.service.inter.project.GanttService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GanttServiceImpl implements GanttService {

    private final GanttRepository ganttRepository;

    @Override
    public List<GanttDTO> getGantt(Long projectIdx) {
        List<Gantt> ganttList = ganttRepository.findByProjectPrjctIdx(projectIdx);
        List<GanttDTO> gantt = new ArrayList<>();

        ganttList.forEach(item -> {
            gantt.add(item.entityToDto());
        });

        return gantt;
    }

    @Override
    @Transactional
    public GanttDTO insertGantt(GanttDTO ganttDTO) {

        Member member = Member.
                builder().
                memIdx(ganttDTO.getMember().getMemIdx()).

                build();

        Project project = Project.
                builder().
                prjctIdx(ganttDTO.getProject().getPrjctIdx()).
                build();

        Gantt gantt = Gantt.
                builder().
                gtIdx(ganttDTO.getGtIdx()).
                gtTitle(ganttDTO.getGtTitle()).
                gtEndDate(ganttDTO.getGtEndDate()).
                gtStartDate(ganttDTO.getGtStartDate()).
                gtExplanation(ganttDTO.getGtExplanation()).
                gtState(ganttDTO.getGtState()).
                gtPriority(ganttDTO.getGtPriority()).
                gtProgress(ganttDTO.getGtProgress()).
                member(member).
                project(project).
                build();

        gantt = ganttRepository.save(gantt);

        return gantt.entityToDto();
    }

    @Override
    @Transactional
    public GanttDTO updateGantt(GanttDTO ganttDTO) {
        Gantt gantt = ganttRepository.findByGtIdx(ganttDTO.getGtIdx());
        gantt.changeGtTitle(ganttDTO.getGtTitle());
        gantt.changeGtExplanation(ganttDTO.getGtExplanation());
        gantt.changeGtStartDate(ganttDTO.getGtStartDate());
        gantt.changeGtEndDate(ganttDTO.getGtEndDate());
        gantt.changeGtPriority(ganttDTO.getGtPriority());
        gantt.changeGtProgress(ganttDTO.getGtProgress());
        gantt.changeGtState(ganttDTO.getGtState());
        gantt = ganttRepository.save(gantt);
        return gantt.entityToDto();
    }

    @Override
    public void deleteGantt(GanttDTO ganttDTO) {

        Gantt gantt = ganttRepository.findByGtIdx(ganttDTO.getGtIdx());
        System.out.println(gantt);
        ganttRepository.delete(gantt);

    }
}
