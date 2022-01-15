package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.DemandDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "demand")
@Builder
public class Demand {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demandIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;

    @Column(name = "demand_revise_date")
    private LocalDateTime demandReviseDate;

    public DemandDTO entityToDto() {
        return DemandDTO.builder()
                .demandIdx(demandIdx)
                .project(project.entityToDto())
                .demandReviseDate(demandReviseDate)
                .build();
    }
}
